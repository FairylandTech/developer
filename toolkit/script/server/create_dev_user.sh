#!/usr/bin/env bash
#
# create_dev_user.sh
# 适用于 Ubuntu / Debian（含 AWS EC2、Lightsail 等云服务器）
#
# 功能：
#   1. 创建用户（默认 dev），主组为 developer
#   2. 该用户执行 sudo 免密码
#   3. 加入 root 组，可编辑属于 root 用户/组的文件（配合免密 sudo）
#   4. 可自定义家目录，不指定则为 /home/<用户名>
#   5. 允许该用户通过 SSH 账号密码登录（只对该用户开放，不影响全局）
#
# 用法：
#   sudo bash create_dev_user.sh [-u 用户名] [-g 组名] [-d 家目录] [-p 密码]
#
# 示例：
#   sudo bash create_dev_user.sh                       # 交互式输入密码
#   sudo bash create_dev_user.sh -d /data/dev          # 自定义家目录
#   sudo bash create_dev_user.sh -p 'MyP@ssw0rd'       # 直接指定密码（会留在 shell 历史里）
#
set -euo pipefail

USERNAME="dev"
GROUPNAME="developer"
HOME_DIR=""
PASSWORD=""

SSHD_CONFIG="/etc/ssh/sshd_config"

log()  { echo -e "\033[32m[INFO]\033[0m $*"; }
warn() { echo -e "\033[33m[WARN]\033[0m $*"; }
die()  { echo -e "\033[31m[ERROR]\033[0m $*" >&2; exit 1; }

usage() {
    sed -n '2,20p' "$0" | sed 's/^# \{0,1\}//'
    exit 0
}

while getopts ":u:g:d:p:h" opt; do
    case "$opt" in
        u) USERNAME="$OPTARG" ;;
        g) GROUPNAME="$OPTARG" ;;
        d) HOME_DIR="$OPTARG" ;;
        p) PASSWORD="$OPTARG" ;;
        h) usage ;;
        :) die "选项 -$OPTARG 需要参数" ;;
        *) die "未知选项 -$OPTARG，使用 -h 查看帮助" ;;
    esac
done

[[ $EUID -eq 0 ]] || die "请使用 root 或 sudo 运行此脚本"
[[ -f /etc/debian_version ]] || die "此脚本仅支持 Debian / Ubuntu"
[[ -z "$HOME_DIR" ]] && HOME_DIR="/home/${USERNAME}"
[[ "$HOME_DIR" == /* ]] || die "家目录必须是绝对路径：$HOME_DIR"

# ---------- 0. 确保 sudo 已安装（Debian 最小化镜像可能没有） ----------
if ! command -v sudo >/dev/null 2>&1; then
    log "未检测到 sudo，正在安装..."
    apt-get update -y && DEBIAN_FRONTEND=noninteractive apt-get install -y sudo
fi

# ---------- 1. 创建组和用户 ----------
if getent group "$GROUPNAME" >/dev/null; then
    log "用户组 $GROUPNAME 已存在"
else
    groupadd "$GROUPNAME"
    log "已创建用户组 $GROUPNAME"
fi

if id "$USERNAME" >/dev/null 2>&1; then
    warn "用户 $USERNAME 已存在，将更新其主组和附加组"
    usermod -g "$GROUPNAME" "$USERNAME"
    CURRENT_HOME="$(getent passwd "$USERNAME" | cut -d: -f6)"
    if [[ "$CURRENT_HOME" != "$HOME_DIR" ]]; then
        log "迁移家目录：$CURRENT_HOME -> $HOME_DIR"
        mkdir -p "$(dirname "$HOME_DIR")"
        usermod -d "$HOME_DIR" -m "$USERNAME"
    fi
else
    mkdir -p "$(dirname "$HOME_DIR")"
    useradd -m -d "$HOME_DIR" -g "$GROUPNAME" -s /bin/bash "$USERNAME"
    log "已创建用户 $USERNAME，家目录 $HOME_DIR"
fi

# 加入 sudo 组 + root 组（root 组用于直接编辑 group=root 且组可写的文件）
usermod -aG sudo,root "$USERNAME"
log "已将 $USERNAME 加入 sudo、root 附加组"

# ---------- 2. 设置密码 ----------
if [[ -z "$PASSWORD" ]]; then
    while true; do
        read -rsp "请输入 $USERNAME 的登录密码: " PASSWORD; echo
        read -rsp "请再次输入密码: " PASSWORD2; echo
        [[ -n "$PASSWORD" ]] || { warn "密码不能为空"; continue; }
        [[ "$PASSWORD" == "$PASSWORD2" ]] && break
        warn "两次输入不一致，请重试"
    done
fi
echo "${USERNAME}:${PASSWORD}" | chpasswd
unset PASSWORD PASSWORD2
log "已设置 $USERNAME 的密码"

# ---------- 3. sudo 免密码 ----------
SUDOERS_FILE="/etc/sudoers.d/90-${USERNAME}-nopasswd"
cat > "$SUDOERS_FILE" <<EOF
# Managed by create_dev_user.sh
${USERNAME} ALL=(ALL:ALL) NOPASSWD: ALL
EOF
chmod 0440 "$SUDOERS_FILE"
if visudo -cf "$SUDOERS_FILE" >/dev/null; then
    log "已配置 sudo 免密码：$SUDOERS_FILE"
else
    rm -f "$SUDOERS_FILE"
    die "sudoers 语法校验失败，已回滚"
fi

# ---------- 4. 允许该用户 SSH 密码登录 ----------
# 说明：Match 块必须放在 sshd_config 末尾，否则会"吞掉"后续的全局配置。
#       Match 块内的设置会覆盖 AWS / cloud-init 在 sshd_config.d 中设置的
#       PasswordAuthentication no，并且只对该用户生效。
MARK_BEGIN="# >>> create_dev_user.sh: ${USERNAME} >>>"
MARK_END="# <<< create_dev_user.sh: ${USERNAME} <<<"

cp -a "$SSHD_CONFIG" "${SSHD_CONFIG}.bak.$(date +%Y%m%d%H%M%S)"

# 删除旧的同名块（保证可重复执行）
sed -i "\|^${MARK_BEGIN}\$|,\|^${MARK_END}\$|d" "$SSHD_CONFIG"

# 确保文件以换行结尾
[[ -n "$(tail -c1 "$SSHD_CONFIG")" ]] && echo >> "$SSHD_CONFIG"

cat >> "$SSHD_CONFIG" <<EOF
${MARK_BEGIN}
Match User ${USERNAME}
    PasswordAuthentication yes
    KbdInteractiveAuthentication yes
    PubkeyAuthentication yes
${MARK_END}
EOF

# 老版本 OpenSSH (<8.7) 不认识 KbdInteractiveAuthentication，降级为 ChallengeResponseAuthentication
if ! sshd -t 2>/dev/null; then
    sed -i "\|^${MARK_BEGIN}\$|,\|^${MARK_END}\$|s/KbdInteractiveAuthentication/ChallengeResponseAuthentication/" "$SSHD_CONFIG"
fi

# 确保 PAM 开启（Ubuntu/Debian 默认就是 yes）
if grep -qiE '^\s*UsePAM\s+no' "$SSHD_CONFIG"; then
    sed -i -E 's/^\s*UsePAM\s+no/UsePAM yes/I' "$SSHD_CONFIG"
fi

mkdir -p /run/sshd
if ! sshd -t; then
    sed -i "\|^${MARK_BEGIN}\$|,\|^${MARK_END}\$|d" "$SSHD_CONFIG"
    die "sshd 配置校验失败，已移除新增配置，请检查 $SSHD_CONFIG"
fi

if systemctl list-unit-files 2>/dev/null | grep -q '^ssh\.service'; then
    systemctl restart ssh
elif systemctl list-unit-files 2>/dev/null | grep -q '^sshd\.service'; then
    systemctl restart sshd
else
    service ssh restart
fi
log "SSH 服务已重启"

# ---------- 5. 汇总 ----------
echo
log "完成！"
echo "  用户     : $USERNAME"
echo "  主组     : $GROUPNAME"
echo "  附加组   : $(id -nG "$USERNAME")"
echo "  家目录   : $HOME_DIR"
echo "  sudo     : 免密码"
echo "  SSH      : 允许密码登录（仅 $USERNAME）"
echo
echo "  测试登录 : ssh ${USERNAME}@<服务器公网IP>"
warn "请先另开一个终端测试登录成功后，再关闭当前会话。"
warn "如果连不上，请检查 AWS 安全组 / Lightsail 防火墙是否放行 22 端口。"
