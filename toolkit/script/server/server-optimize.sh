#!/usr/bin/env bash
# =============================================================================
#  Ubuntu / Debian 服务器系统配置优化脚本
#  适用：Ubuntu 26.04 / 24.04 / 22.04 LTS，Debian 13 (trixie) / 12 (bookworm)
#
#  默认执行（安全、可回滚）：
#    - 系统更新 + 常用运维工具
#    - 内核参数 (sysctl)：网络栈 / BBR / 内存 / 文件句柄
#    - 进程资源限制 (ulimit / systemd)
#    - journald 日志容量限制
#    - 磁盘 I/O 调度器
#    - 时间同步、自动安全更新
#  可选（需显式开启）：
#    --swap SIZE        创建 swap 文件（如 2G），已有 swap 则跳过
#    --timezone TZ      设置时区（默认 America/Los_Angeles）
#    --ssh-harden       SSH 加固（禁止密码登录，仅在已配置公钥时生效）
#    --firewall         启用 UFW 防火墙（自动放行当前 SSH 端口）
#    --no-update        跳过 apt 更新与软件安装
#    --dry-run          仅打印将执行的操作，不做修改
#
#  用法：sudo bash server-optimize.sh [选项]
#  所有被修改的原文件会备份到 /root/sysopt-backup-<时间戳>/
# =============================================================================
set -Eeuo pipefail

# ----------------------------- 参数 -----------------------------------------
SWAP_SIZE=""
TIMEZONE="America/Los_Angeles"   # 默认洛杉矶时区，可用 --timezone 覆盖
SSH_HARDEN=0
FIREWALL=0
DO_UPDATE=1
DRY_RUN=0

usage() { sed -n '2,23p' "$0" | sed 's/^# \{0,1\}//'; exit 0; }

while [[ $# -gt 0 ]]; do
  case "$1" in
    --swap)       SWAP_SIZE="${2:?--swap 需要参数，如 2G}"; shift 2 ;;
    --timezone)   TIMEZONE="${2:?--timezone 需要参数}"; shift 2 ;;
    --ssh-harden) SSH_HARDEN=1; shift ;;
    --firewall)   FIREWALL=1; shift ;;
    --no-update)  DO_UPDATE=0; shift ;;
    --dry-run)    DRY_RUN=1; shift ;;
    -h|--help)    usage ;;
    *) echo "未知参数: $1"; usage ;;
  esac
done

# ----------------------------- 工具函数 -------------------------------------
RED='\033[31m'; GREEN='\033[32m'; YELLOW='\033[33m'; BLUE='\033[36m'; NC='\033[0m'
info()  { echo -e "${BLUE}[INFO]${NC}  $*"; }
ok()    { echo -e "${GREEN}[ OK ]${NC}  $*"; }
warn()  { echo -e "${YELLOW}[WARN]${NC}  $*"; }
die()   { echo -e "${RED}[FAIL]${NC}  $*" >&2; exit 1; }
step()  { echo -e "\n${GREEN}==== $* ====${NC}"; }

trap 'die "第 ${LINENO} 行执行失败：${BASH_COMMAND}"' ERR

run() {  # 执行命令（dry-run 时只打印）
  if (( DRY_RUN )); then echo "  [dry-run] $*"; else "$@"; fi
}

BACKUP_DIR="/root/sysopt-backup-$(date +%Y%m%d-%H%M%S)"
backup() {  # 备份将被覆盖的文件
  local f="$1"
  [[ -e "$f" ]] || return 0
  (( DRY_RUN )) && return 0
  mkdir -p "$BACKUP_DIR$(dirname "$f")"
  cp -a "$f" "$BACKUP_DIR$f"
}

write_file() {  # write_file <路径>  （内容来自 stdin）
  local f="$1" content
  content="$(cat)"
  if (( DRY_RUN )); then
    echo "  [dry-run] 写入 $f:"; echo "$content" | sed 's/^/      /'; return
  fi
  backup "$f"
  mkdir -p "$(dirname "$f")"
  printf '%s\n' "$content" > "$f"
  ok "已写入 $f"
}

# ----------------------------- 前置检查 -------------------------------------
[[ $EUID -eq 0 ]] || die "请使用 root 或 sudo 运行"
[[ -r /etc/os-release ]] || die "无法识别操作系统"
. /etc/os-release
case "${ID:-}:${VERSION_ID:-}" in
  ubuntu:26.04|ubuntu:24.04|ubuntu:22.04|debian:13|debian:12)
    info "检测到系统：$PRETTY_NAME" ;;
  ubuntu:*|debian:*)
    warn "未在 $PRETTY_NAME 上测试，3 秒后继续，Ctrl+C 可中止"; sleep 3 ;;
  *)
    die "仅支持 Ubuntu / Debian，当前为 ${PRETTY_NAME:-unknown}" ;;
esac
OS_ID="$ID"

MEM_MB=$(awk '/MemTotal/ {printf "%d", $2/1024}' /proc/meminfo)
CPU_N=$(nproc)
info "CPU: ${CPU_N} 核, 内存: ${MEM_MB} MB"
(( DRY_RUN )) && warn "dry-run 模式：不会做任何修改"
if (( MEM_MB < 2048 )) && [[ -z "$SWAP_SIZE" ]] && [[ -z "$(swapon --show --noheadings 2>/dev/null || true)" ]]; then
  warn "内存仅 ${MEM_MB} MB 且没有 swap，强烈建议加上 --swap 2G，否则 apt 升级等操作可能被 OOM 终止"
fi

export DEBIAN_FRONTEND=noninteractive

# ============================================================================
# 1. 系统更新与基础工具
# ============================================================================
if (( DO_UPDATE )); then
  step "1. 系统更新与基础工具"
  run apt-get update -y
  run apt-get -o Dpkg::Options::=--force-confold upgrade -y
  run apt-get install -y --no-install-recommends \
      curl wget vim htop iotop iftop sysstat lsof net-tools bind9-dnsutils \
      ca-certificates gnupg tree unzip jq tmux chrony \
      unattended-upgrades
  run apt-get autoremove -y
  run apt-get clean
  # 启用 sysstat 数据收集（sar 历史数据）
  if [[ -f /etc/default/sysstat ]]; then
    run sed -i 's/^ENABLED=.*/ENABLED="true"/' /etc/default/sysstat
    run systemctl enable --now sysstat
  fi
else
  step "1. 跳过系统更新（--no-update）"
fi

# ============================================================================
# 2. 时区与时间同步
# ============================================================================
step "2. 时区与时间同步"
if [[ -n "$TIMEZONE" ]]; then
  [[ -f "/usr/share/zoneinfo/$TIMEZONE" ]] || die "无效时区：$TIMEZONE"
  CUR_TZ=$(timedatectl show -p Timezone --value 2>/dev/null || true)
  if [[ "$CUR_TZ" == "$TIMEZONE" ]]; then
    ok "时区已是 $TIMEZONE，无需修改"
  else
    run timedatectl set-timezone "$TIMEZONE"
    ok "时区：${CUR_TZ:-未知} → $TIMEZONE"
  fi
fi
if systemctl list-unit-files chrony.service &>/dev/null; then
  run systemctl enable --now chrony
fi
run timedatectl set-ntp true || true
ok "时间同步已启用"

# ============================================================================
# 3. 内核参数 sysctl
# ============================================================================
step "3. 内核参数优化 (sysctl)"

# 根据内存规模调整 TCP 缓冲上限
if   (( MEM_MB >= 16384 )); then TCP_MAX=67108864   # 64MB
elif (( MEM_MB >= 4096  )); then TCP_MAX=33554432   # 32MB
else                             TCP_MAX=16777216   # 16MB
fi

# 小内存机器保持内核默认的内存超分策略 (0)，避免内存耗尽时进程被直接 OOM kill
if (( MEM_MB >= 4096 )); then OVERCOMMIT=1; else OVERCOMMIT=0; fi

# BBR 拥塞控制（内核自带模块）
CC="cubic"
if modprobe -n tcp_bbr &>/dev/null; then
  CC="bbr"
  write_file /etc/modules-load.d/99-sysopt-bbr.conf <<< "tcp_bbr"
  run modprobe tcp_bbr
fi

write_file /etc/sysctl.d/99-sysopt.conf <<EOF
# ---- 由 server-optimize.sh 生成 ----

# 文件句柄
fs.file-max = 2097152
fs.nr_open = 2097152
fs.inotify.max_user_watches = 524288
fs.inotify.max_user_instances = 1024

# 内存
vm.swappiness = 10
vm.vfs_cache_pressure = 50
vm.dirty_ratio = 15
vm.dirty_background_ratio = 5
vm.max_map_count = 262144
vm.overcommit_memory = ${OVERCOMMIT}

# 网络：队列与连接
net.core.somaxconn = 65535
net.core.netdev_max_backlog = 16384
net.ipv4.tcp_max_syn_backlog = 65535
net.ipv4.ip_local_port_range = 10240 65535
net.ipv4.tcp_fin_timeout = 15
net.ipv4.tcp_tw_reuse = 1
net.ipv4.tcp_max_tw_buckets = 262144
net.ipv4.tcp_slow_start_after_idle = 0
net.ipv4.tcp_mtu_probing = 1
net.ipv4.tcp_fastopen = 3

# 网络：Keepalive
net.ipv4.tcp_keepalive_time = 600
net.ipv4.tcp_keepalive_intvl = 30
net.ipv4.tcp_keepalive_probes = 5

# 网络：缓冲区
net.core.rmem_max = ${TCP_MAX}
net.core.wmem_max = ${TCP_MAX}
net.core.rmem_default = 262144
net.core.wmem_default = 262144
net.ipv4.tcp_rmem = 4096 87380 ${TCP_MAX}
net.ipv4.tcp_wmem = 4096 65536 ${TCP_MAX}
net.ipv4.udp_rmem_min = 16384
net.ipv4.udp_wmem_min = 16384

# 拥塞控制
net.core.default_qdisc = fq
net.ipv4.tcp_congestion_control = ${CC}

# 安全
net.ipv4.tcp_syncookies = 1
net.ipv4.conf.all.rp_filter = 1
net.ipv4.conf.default.rp_filter = 1
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0
net.ipv4.conf.all.accept_source_route = 0
net.ipv6.conf.all.accept_redirects = 0
net.ipv6.conf.default.accept_redirects = 0
net.ipv4.icmp_echo_ignore_broadcasts = 1
net.ipv4.icmp_ignore_bogus_error_responses = 1
kernel.kptr_restrict = 1
kernel.dmesg_restrict = 1
kernel.panic = 10
EOF

run sysctl --system >/dev/null || warn "部分 sysctl 参数不被当前内核支持，已跳过"
ok "sysctl 已生效，拥塞控制：$CC"

# ============================================================================
# 4. 进程资源限制
# ============================================================================
step "4. 资源限制 (ulimit / systemd)"
write_file /etc/security/limits.d/99-sysopt.conf <<'EOF'
*     soft  nofile  1048576
*     hard  nofile  1048576
*     soft  nproc   65535
*     hard  nproc   65535
root  soft  nofile  1048576
root  hard  nofile  1048576
EOF

write_file /etc/systemd/system.conf.d/99-sysopt.conf <<'EOF'
[Manager]
DefaultLimitNOFILE=1048576
DefaultLimitNPROC=65535
EOF
write_file /etc/systemd/user.conf.d/99-sysopt.conf <<'EOF'
[Manager]
DefaultLimitNOFILE=1048576
EOF
run systemctl daemon-reexec
ok "资源限制已配置（新会话 / 新启动的服务生效）"

# ============================================================================
# 5. journald 日志限制
# ============================================================================
step "5. journald 日志容量"
write_file /etc/systemd/journald.conf.d/99-sysopt.conf <<'EOF'
[Journal]
Storage=persistent
Compress=yes
SystemMaxUse=1G
SystemMaxFileSize=100M
MaxRetentionSec=1month
RateLimitIntervalSec=30s
RateLimitBurst=10000
EOF
run systemctl restart systemd-journald
ok "journald 最大占用 1G，保留 1 个月"

# ============================================================================
# 6. 磁盘 I/O 调度器
# ============================================================================
step "6. I/O 调度器"
write_file /etc/udev/rules.d/60-sysopt-ioscheduler.rules <<'EOF'
# NVMe：无需调度
ACTION=="add|change", KERNEL=="nvme[0-9]*n[0-9]*", ATTR{queue/scheduler}="none"
# SSD / 虚拟盘：mq-deadline
ACTION=="add|change", KERNEL=="sd[a-z]*|vd[a-z]*|xvd[a-z]*", ATTR{queue/rotational}=="0", ATTR{queue/scheduler}="mq-deadline"
# 机械盘：bfq
ACTION=="add|change", KERNEL=="sd[a-z]*", ATTR{queue/rotational}=="1", ATTR{queue/scheduler}="bfq"
EOF
run udevadm control --reload-rules
run udevadm trigger --subsystem-match=block --action=change || true
ok "I/O 调度规则已应用"

# 启用 SSD 定期 TRIM
run systemctl enable --now fstrim.timer || true

# ============================================================================
# 7. Swap（可选）
# ============================================================================
if [[ -n "$SWAP_SIZE" ]]; then
  step "7. Swap"
  if [[ -n "$(swapon --show --noheadings)" ]]; then
    warn "系统已有 swap，跳过创建"
  elif [[ -e /swapfile ]]; then
    warn "/swapfile 已存在但未启用，跳过（请手动检查）"
  else
    run fallocate -l "$SWAP_SIZE" /swapfile
    run chmod 600 /swapfile
    run mkswap /swapfile
    run swapon /swapfile
    if ! grep -q '^/swapfile' /etc/fstab; then
      backup /etc/fstab
      (( DRY_RUN )) || echo '/swapfile none swap sw 0 0' >> /etc/fstab
    fi
    ok "已创建 ${SWAP_SIZE} swap"
  fi
fi

# ============================================================================
# 8. 自动安全更新
# ============================================================================
step "8. 自动安全更新"
write_file /etc/apt/apt.conf.d/20auto-upgrades <<'EOF'
APT::Periodic::Update-Package-Lists "1";
APT::Periodic::Unattended-Upgrade "1";
APT::Periodic::AutocleanInterval "7";
EOF
if [[ "$OS_ID" == "debian" ]]; then
  ok "已启用 unattended-upgrades（Debian 默认：安全更新 + 稳定版修订更新，不自动重启）"
else
  ok "已启用 unattended-upgrades（仅安全更新，不自动重启）"
fi

# ============================================================================
# 9. SSH 加固（可选）
# ============================================================================
# 检测 SSH 端口。Ubuntu 24.04+ 默认 socket 激活，sshd 未常驻时 /run/sshd 不存在，
# 会导致 sshd -T 失败，所以这里每一步都容错，最后回退到配置文件 / 默认 22。
get_ssh_port() {
  local p=""
  (( DRY_RUN )) || mkdir -p /run/sshd 2>/dev/null || true
  if command -v sshd &>/dev/null; then
    p=$( { sshd -T 2>/dev/null || true; } | awk '/^port /{v=$2} END{print v}' )
  fi
  if [[ -z "$p" ]]; then
    p=$( { cat /etc/ssh/sshd_config /etc/ssh/sshd_config.d/*.conf 2>/dev/null || true; } \
         | awk 'tolower($1)=="port"{v=$2} END{print v}' )
  fi
  echo "${p:-22}"
}
SSH_PORT=$(get_ssh_port)
info "检测到 SSH 端口：${SSH_PORT}"

if (( SSH_HARDEN )); then
  step "9. SSH 加固"
  HAS_KEY=0
  for f in /root/.ssh/authorized_keys /home/*/.ssh/authorized_keys; do
    [[ -s "$f" ]] && HAS_KEY=1 && break
  done

  if (( HAS_KEY == 0 )); then
    warn "未发现任何 authorized_keys，为防止锁死，跳过禁用密码登录"
    PASS_AUTH="yes"
  else
    PASS_AUTH="no"
  fi

  write_file /etc/ssh/sshd_config.d/99-sysopt.conf <<EOF
PermitRootLogin prohibit-password
PasswordAuthentication ${PASS_AUTH}
KbdInteractiveAuthentication no
PubkeyAuthentication yes
PermitEmptyPasswords no
MaxAuthTries 4
LoginGraceTime 30
ClientAliveInterval 300
ClientAliveCountMax 2
X11Forwarding no
UseDNS no
EOF
  if (( ! DRY_RUN )); then
    if sshd -t; then
      # socket 激活时 ssh.service 可能未运行，新连接会自动读取新配置
      systemctl try-reload-or-restart ssh || true
      ok "SSH 已加固（密码登录：${PASS_AUTH}）——请先新开终端验证能登录，再关闭当前会话！"
    else
      rm -f /etc/ssh/sshd_config.d/99-sysopt.conf
      die "sshd 配置校验失败，已撤销 SSH 修改"
    fi
  fi
fi

# ============================================================================
# 10. 防火墙（可选）
# ============================================================================
if (( FIREWALL )); then
  step "10. UFW 防火墙"
  command -v ufw &>/dev/null || run apt-get install -y ufw
  run ufw default deny incoming
  run ufw default allow outgoing
  run ufw allow "${SSH_PORT}/tcp" comment 'SSH'
  run ufw --force enable
  ok "UFW 已启用，已放行 SSH 端口 ${SSH_PORT}。其他端口请用：ufw allow <端口>/tcp"
fi

# ============================================================================
# 完成
# ============================================================================
step "完成"
if (( ! DRY_RUN )); then
  echo "  拥塞控制    : $(sysctl -n net.ipv4.tcp_congestion_control)"
  echo "  队列算法    : $(sysctl -n net.core.default_qdisc)"
  echo "  somaxconn   : $(sysctl -n net.core.somaxconn)"
  echo "  file-max    : $(sysctl -n fs.file-max)"
  echo "  swappiness  : $(sysctl -n vm.swappiness)"
  echo "  Swap        : $(swapon --show --noheadings | awk '{print $1" "$3}' | paste -sd, - || true)"
  [[ -d "$BACKUP_DIR" ]] && echo "  原文件备份  : $BACKUP_DIR"
fi
echo
warn "建议重启服务器以确保所有配置（ulimit、systemd 限制等）完全生效：sudo reboot"
echo
echo "回滚方法：删除以下文件后重启"
echo "  /etc/sysctl.d/99-sysopt.conf  /etc/security/limits.d/99-sysopt.conf"
echo "  /etc/systemd/{system,user,journald}.conf.d/99-sysopt.conf"
echo "  /etc/udev/rules.d/60-sysopt-ioscheduler.rules  /etc/modules-load.d/99-sysopt-bbr.conf"
echo "  /etc/ssh/sshd_config.d/99-sysopt.conf（如启用）"
