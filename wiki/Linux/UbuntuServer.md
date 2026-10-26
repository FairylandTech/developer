# 一、服务器

## 1. 初始化

```shell
sudo apt update && sudo apt full-upgrade -y && sudo apt autoremove --purge -y
```

## 2. 用户和用户组

```shell
sudo adduser dev
sudo usermod -aG sudo dev
```

## 3. 配置 SSH Key

本地配置

```shell
# Mac/Linux 
ssh-kengen -t ed25519 -C "fairylandfuture@outlook.com" -f ~/.ssh/id_ed25519_server -N ""
# Windows CMD
ssh-keygen -t ed25519 -C "fairylandfuture@outlook.com" -f %USERPROFILE%\.ssh\id_ed25519_server -N ""
# Windwos PowerShell
ssh-keygen -t ed25519 -C "fairylandfuture@outlook.com" -f $env:USERPROFILE\.ssh\id_ed25519_server -N '""'
```

复制公钥到服务器或者手动加入服务器`~/.ssh/authorized_keys`

```shell
# Mac/Linux
ssh-copy-id <用户名>@<服务器地址>
# Windwos PowerShell
type $env:USERPROFILE\.ssh\id_ed25519_server.pub | ssh <用户名>@<服务器地址> "mkdir -p ~/.ssh && chmod 700 ~/.ssh && cat >> ~/.ssh/authorized_keys && chmod 600 ~/.ssh/authorized_keys"
# Windows CMD
type %USERPROFILE%\.ssh\id_ed25519_server.pub | ssh <用户名>@<服务器地址> "mkdir -p ~/.ssh && chmod 700 ~/.ssh && cat >> ~/.ssh/authorized_keys && chmod 600 ~/.ssh/authorized_keys"
```

**确认密钥登录成功以后，关闭密码登录**

```shell
# 不建议直接改`/etc/ssh/sshd_config`，而是建立`99-hardening.conf`
sudo vim /etc/ssh/sshd_config.d/99-hardening.conf
```

写入内容：

```text
PermitRootLogin no
PasswordAuthentication no
KbdInteractiveAuthentication no
PubkeyAuthentication yes

MaxAuthTries 3
X11Forwarding no
```

测试没问题后重新载入ssh服务

```shell
sudo sshd -t
sudo systemctl reload ssh
```

**要立刻退出当前 SSH。新开一个终端测试登录**

## 4. 安装常用管理工具

```shell
sudo apt install -y curl wget git vim nano htop tmux jq unzip zip rsync ca-certificates gnupg lsof net-tools dnsutils traceroute
```

# 二、安装 Docker

```shell
sudo apt remove $(dpkg --get-selections docker.io docker-compose docker-compose-v2 docker-doc docker-buildx podman-docker containerd runc | cut -f1)

sudo install -m 0755 -d /etc/apt/keyrings && sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc && sudo chmod a+r /etc/apt/keyrings/docker.asc

sudo tee /etc/apt/sources.list.d/docker.sources <<EOF
Types: deb
URIs: https://download.docker.com/linux/ubuntu
Suites: $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}")
Components: stable
Architectures: $(dpkg --print-architecture)
Signed-By: /etc/apt/keyrings/docker.asc
EOF

sudo apt update && sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

sudo systemctl enable --now docker && sudo usermod -aG docker dev
```

# 三、安装 Java 环境

```shell
# 安装 JDK
sudo apt update && sudo apt install -y openjdk-17-jdk openjdk-21-jdk maven
```

JAVA_HOME 配置
```shell
cat >> ~/.bashrc <<'EOF'

# ==========
# Java
# ==========
export JAVA_HOME="$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")"
EOF

source ~/.bashrc
# 切换 java 和 javac 版本
sudo update-alternatives --config java
sudo update-alternatives --config javac
```

# 四、安装Python 环境

```shell
# 安装 uv
curl -LsSf https://astral.sh/uv/install.sh | sh

# 后续升级
uv self update
```

配置

```shell
# 普通用户创建，如果是其他目录确保当前用户有读写的权限
# sudo mkdir -p /opt/uv/python
# sudo chown -R "$USER":"$USER" /opt/uv
mkdir -p ~/runtime/uv/python

cat >> ~/.bashrc <<'EOF'

# ==========
# uv / Python
# ==========
. "${HOME}/.local/bin/env"
# The Python downloaded and managed by uv is uniformly installed in this directory.
export UV_PYTHON_INSTALL_DIR="${HOME}/runtime/uv/python"
# Disable uv cache
export UV_NO_CACHE=1
# Disable pip cache
export PIP_NO_CACHE_DIR=1
# Python runtime prohibits the generation of .pyc / __pycache__
export PYTHONDONTWRITEBYTECODE=1
EOF

source ~/.bashrc
```

# 五、安装 GO 环境

下载`1.25.14`版本的包

```shell

mkdir -p "${HOME}/tmp" && cd "${HOME}/tmp"
curl -LO "https://go.dev/dl/go1.25.14.linux-amd64.tar.gz" && cd - > /dev/nullnvm -v
```

安装

```shell
mkdir -p "${HOME}/runtime/go/go1.25.14"
tar -C "${HOME}/runtime/go/go1.25.14" -xzf "${HOME}/tmp/go1.25.14.linux-amd64.tar.gz"
```

GOROOT，GOPATH 配置

```shell
cat >> ~/.bashrc <<'EOF'

# ==========
# Go
# ==========
export GOROOT=${HOME}/runtime/go/go1.25.14/go
export GOPATH=$HOME/go
export PATH=$PATH:$GOROOT/bin:$GOPATH/bin
EOF

source ~/.bashrc
```

# 六、安装 Node.js 环境

使用 NVM 可以对 Node 进行多版本管理

```shell
cd && curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.8/install.sh | bash
```

安装

```shell
# 安装 Node.js 24
nvm install 24
# 设为默认版本
nvm alias default 24
# 使用
nvm use 24
```
