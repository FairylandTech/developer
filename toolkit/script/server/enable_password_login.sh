#!/usr/bin/env bash
#
# enable_password_login.sh
# 适用于 Ubuntu / Debian（AWS EC2、Lightsail 及其他默认只允许密钥登录的云服务器）
#
# 功能：
#   全局开启 SSH 账号密码登录（保留 pem/密钥登录），并防止 cloud-init 重启后改回。
#
# 用法：
#   sudo bash enable_password_login.sh [-u 用户名] [-p 密码] [-r]
#
#   -u  同时为某个用户设置密码（如 ubuntu / admin / root），不指定则只改 SSH 配置
#   -p  该用户的密码；指定了 -u 但没给 -p 会交互式输入
#   -r  允许 root 使用密码登录（PermitRootLogin yes），默认不允许
#
# 示例：
#   sudo bash enable_password_login.sh                     # 只开启密码认证
#   sudo bash enable_password_login.sh -u ubuntu           # 开启并给 ubuntu 设置密码
#   sudo bash enable_password_login.sh -u root -r          # 开启并允许 root 密码登录
#
set -euo pipefail

TARGET_USER=""
PASSWORD=""
ALLOW_ROOT="no"

SSHD_CONFIG="/etc/ssh/sshd_config"
SSHD_DIR="/etc/ssh/sshd_config.d"
DROPIN="${SSHD_DIR}/00-enable-password-login.conf"
CLOUD_CFG="/etc/cloud/cloud.cfg.d/99-enable-password-login.cfg"

log()  { echo -e "\033[32m[INFO]\033[0m $*"; }
warn() { echo -e "\033[33m[WARN]\033[0m $*"; }
die()  { echo -e "\033[31m[ERROR]\033[0m $*" >&2; exit 1; }

usage() {
    sed -n '2,21p' "$0" | sed 's/^# \{0,1\}//'
    exit 0
}

while getopts ":u:p:rh" opt; do
    case "$opt" in
        u) TARGET_USER="$OPTARG" ;;
        p) PASSWORD="$OPTARG" ;;
        r) ALLOW_ROOT="yes" ;;
        h) usage ;;
        :) die "选项 -$OPTARG 需要参数" ;;
        *) die "未知选项 -$OPTARG，使用 -h 查看帮助" ;;
    esac
done

[[ $EUID -eq 0 ]] || die "请使用 root 或 sudo 运行此脚本"
[[ -f /etc/debian_version ]] || die "此脚本仅支持 Debian / Ubuntu"
[[ -f "$SSHD_CONFIG" ]] || die "未找到 $SSHD_CONFIG，请确认已安装 openssh-server"

if [[ "$TARGET_USER" == "root" && "$ALLOW_ROOT" != "yes" ]]; then
    warn "你给 root 设置了密码但没加 -r，root 仍然不能用密码登录 SSH"
fi

# 老版本 OpenSSH (<8.7) 用 ChallengeResponseAuthentication
SSH_VER="$( { sshd -V 2>&1; ssh -V 2>&1; } | grep -oE 'OpenSSH_[0-9]+\.[0-9]+' | head -1 | cut -d_ -f2 || true)"
KBD_KEY="KbdInteractiveAuthentication"
if [[ -n "$SSH_VER" ]] && awk -v v="$SSH_VER" 'BEGIN{exit !(v < 8.7)}'; then
    KBD_KEY="ChallengeResponseAuthentication"
fi

if [[ "$ALLOW_ROOT" == "yes" ]]; then
    ROOT_VALUE="yes"
else
    ROOT_VALUE="prohibit-password"
fi

# ---------- 1. 备份 ----------
TS="$(date +%Y%m%d%H%M%S)"
BACKUP_DIR="/root/ssh-backup-${TS}"
mkdir -p "$BACKUP_DIR"
cp -a "$SSHD_CONFIG" "$BACKUP_DIR/"
[[ -d "$SSHD_DIR" ]] && cp -a "$SSHD_DIR" "$BACKUP_DIR/"
log "已备份 SSH 配置到 $BACKUP_DIR"

# ---------- 2. 修改 sshd 配置 ----------
# sshd 对同一个参数"第一次出现的值生效"。
# AWS / cloud-init 会在 sshd_config.d/50-cloud-init.conf、60-cloudimg-settings.conf
# 里写 PasswordAuthentication no，所以：
#   a) 把这些 drop-in 里的冲突项注释掉
#   b) 再写一个 00- 开头的 drop-in（排序最前，最先生效）
#   c) 主配置文件里的冲突项也一并改掉（兼容没有 Include 的老系统）

# 只处理第一个 Match 之前的全局区域，Match 块（如 create_dev_user.sh 写的）保持不动：
#   - 第一处出现（含被注释的默认行）替换为 "key value"
#   - 之后再出现的未注释同名项注释掉
#   - 没找到则插在第一个 Match 之前 / 文件末尾
set_main_option() {
    local key="$1" value="$2" tmp
    tmp="$(mktemp)"
    awk -v key="$key" -v value="$value" '
        BEGIN { lk = tolower(key); done = 0; in_match = 0 }
        {
            line = $0; l = tolower(line)
            if (!in_match && l ~ /^[ \t]*match[ \t]/) {
                in_match = 1
                if (!done) { print key " " value; done = 1 }
            }
            if (!in_match && l ~ ("^[ \t]*#?[ \t]*" lk "[ \t]")) {
                if (!done) { print key " " value; done = 1; next }
                if (l !~ /^[ \t]*#/) { print "# " line "  # disabled by enable_password_login.sh"; next }
            }
            print line
        }
        END { if (!done) print key " " value }
    ' "$SSHD_CONFIG" > "$tmp"
    cat "$tmp" > "$SSHD_CONFIG"   # 用 cat 覆盖，保留原文件权限
    rm -f "$tmp"
}

OPTIONS=(
    "PasswordAuthentication yes"
    "${KBD_KEY} yes"
    "PubkeyAuthentication yes"
    "UsePAM yes"
    "PermitRootLogin ${ROOT_VALUE}"
)

for item in "${OPTIONS[@]}"; do
    set_main_option "${item%% *}" "${item#* }"
done
log "已修改 $SSHD_CONFIG"

if [[ -d "$SSHD_DIR" ]] && grep -qiE '^\s*Include\s+/etc/ssh/sshd_config\.d' "$SSHD_CONFIG"; then
    # 注释掉其他 drop-in 中的冲突项
    for f in "$SSHD_DIR"/*.conf; do
        [[ -e "$f" && "$f" != "$DROPIN" ]] || continue
        for item in "${OPTIONS[@]}"; do
            key="${item%% *}"
            sed -i -E "s/^(\s*${key}\s+.*)/# \1  # disabled by enable_password_login.sh/I" "$f"
        done
    done

    {
        echo "# Managed by enable_password_login.sh"
        for item in "${OPTIONS[@]}"; do echo "$item"; done
    } > "$DROPIN"
    chmod 0644 "$DROPIN"
    log "已写入 $DROPIN"
fi

# ---------- 3. 防止 cloud-init 重启后改回 ----------
if [[ -d /etc/cloud ]]; then
    mkdir -p "$(dirname "$CLOUD_CFG")"
    {
        echo "# Managed by enable_password_login.sh"
        echo "ssh_pwauth: true"
        if [[ "$ALLOW_ROOT" == "yes" ]]; then echo "disable_root: false"; fi
    } > "$CLOUD_CFG"
    log "已写入 cloud-init 配置 $CLOUD_CFG"
fi

# AWS 默认在 /root/.ssh/authorized_keys 前加了 command="echo 'Please login as...'"
# 允许 root 时顺手去掉，这样 root 用 pem 也能登录
if [[ "$ALLOW_ROOT" == "yes" && -f /root/.ssh/authorized_keys ]]; then
    cp -a /root/.ssh/authorized_keys "$BACKUP_DIR/root_authorized_keys"
    sed -i -E 's/^[^#]*Please login as.*"[[:space:]]+((ssh|ecdsa|sk)-)/\1/' /root/.ssh/authorized_keys
fi

# ---------- 4. 设置用户密码（可选） ----------
if [[ -n "$TARGET_USER" ]]; then
    id "$TARGET_USER" >/dev/null 2>&1 || die "用户 $TARGET_USER 不存在"
    if [[ -z "$PASSWORD" ]]; then
        while true; do
            read -rsp "请输入 $TARGET_USER 的新密码: " PASSWORD; echo
            read -rsp "请再次输入密码: " PASSWORD2; echo
            [[ -n "$PASSWORD" ]] || { warn "密码不能为空"; continue; }
            [[ "$PASSWORD" == "$PASSWORD2" ]] && break
            warn "两次输入不一致，请重试"
        done
    fi
    echo "${TARGET_USER}:${PASSWORD}" | chpasswd
    passwd -u "$TARGET_USER" >/dev/null 2>&1 || true   # 解锁账户（云镜像的 ubuntu/admin 默认被锁定）
    unset PASSWORD PASSWORD2
    log "已设置 $TARGET_USER 的密码"
fi

# ---------- 5. 校验并重启 ----------
mkdir -p /run/sshd
if ! sshd -t; then
    warn "sshd 配置校验失败，正在回滚..."
    cp -a "$BACKUP_DIR/sshd_config" "$SSHD_CONFIG"
    if [[ -d "$BACKUP_DIR/sshd_config.d" ]]; then
        rm -rf "$SSHD_DIR"
        cp -a "$BACKUP_DIR/sshd_config.d" "$SSHD_DIR"
    fi
    die "已回滚到原始配置，请手动检查"
fi

if systemctl list-unit-files 2>/dev/null | grep -q '^ssh\.service'; then
    systemctl restart ssh
elif systemctl list-unit-files 2>/dev/null | grep -q '^sshd\.service'; then
    systemctl restart sshd
else
    service ssh restart
fi
log "SSH 服务已重启"

# ---------- 6. 结果 ----------
echo
log "当前生效配置："
sshd -T 2>/dev/null | grep -iE '^(passwordauthentication|kbdinteractiveauthentication|challengeresponseauthentication|pubkeyauthentication|permitrootlogin|usepam) ' | sed 's/^/    /'
echo
log "完成！现在可以用 账号+密码 或 pem 密钥 登录。"
warn "请先另开一个终端测试登录成功后，再关闭当前会话。"
warn "如连不上，请检查 AWS 安全组 / Lightsail 防火墙是否放行 22 端口。"
warn "开启密码登录后建议使用强密码，并考虑安装 fail2ban 防暴力破解。"
echo "  回滚方法: 备份在 $BACKUP_DIR"
