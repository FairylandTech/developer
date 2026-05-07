#!/bin/sh
set -eu

# ==============================================
# 完全POSIX兼容版本，支持所有标准Shell（dash/ash/bash/ksh等）
# 工作路径固定为脚本所在目录，不会随执行路径变化
# ==============================================
# POSIX标准方式获取脚本所在绝对路径
SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
cd "${SCRIPT_DIR}" || exit 1

# ========================== 配置项 ==========================
SERVER_DOMAIN="gz.tencent.server.fairy.host"
COMPOSE_FILE="${SCRIPT_DIR}/docker-compose.yaml"
ENV_FILE="${SCRIPT_DIR}/.env"
VOLUME_DIR="${SCRIPT_DIR}/volumes"
INIT_DIR="${SCRIPT_DIR}/init"
WAIT_TIMEOUT=120
CHECK_INTERVAL=2
SERVICE_LIST="mysql-service postgresql-service redis-service nacos-service"

# 颜色输出（POSIX兼容使用printf）
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

# ========================== 工具函数 ==========================
info() { printf "${GREEN}[INFO] %s ${NC}\n" "$*"; }
warn() { printf "${YELLOW}[WARN] %s ${NC}\n" "$*"; }
error() {
  printf "${RED}[ERROR] %s ${NC}\n" "$*"
  exit 1
}

# 校验Docker权限&用户组
check_docker_perm() {
  # 检查Docker是否安装
  if ! command -v docker >/dev/null 2>&1; then
    error "未检测到Docker，请先安装Docker：https://docs.docker.com/get-docker/"
  fi

  # 检查Docker组是否存在，不存在则创建
  if ! getent group docker >/dev/null 2>&1; then
    info "docker用户组不存在，正在创建..."
    sudo groupadd docker
  fi

  # 检查docker用户是否存在，不存在则创建
  if ! id -u docker >/dev/null 2>&1; then
    info "docker用户不存在，正在创建..."
    sudo useradd -M -N -s /sbin/nologin -g docker docker >>/dev/null
    sudo usermod -aG docker docker
  fi

  # 检查当前用户是否属于docker组
  DOCKER_PREFIX=""
  if ! groups | grep -q docker; then
    warn "当前用户不在docker用户组，执行以下命令加入后重启终端即可免sudo运行："
    warn "sudo usermod -aG docker $USER"
    printf "是否临时使用sudo权限执行？[y/N] "
    read -r use_sudo
    use_sudo_lower=$(echo "$use_sudo" | tr '[:upper:]' '[:lower:]')
    case "$use_sudo_lower" in
    y | yes) ;;
    *) error "请加入docker用户组后再执行脚本" ;;
    esac
  fi

  # 兼容docker compose v1/v2
  if ${DOCKER_PREFIX} docker compose version >/dev/null 2>&1; then
    DOCKER_COMPOSE="${DOCKER_PREFIX} docker compose"
  elif command -v docker-compose >/dev/null 2>&1; then
    DOCKER_COMPOSE="${DOCKER_PREFIX} docker-compose"
  else
    error "未检测到Docker Compose插件，请先安装"
  fi
  info "Docker环境校验正常，当前工作目录：${SCRIPT_DIR}"
}

# 自动创建所有缺失目录&初始化文件
init_resources() {
  info "初始化目录结构..."
  mkdir -p \
    "${VOLUME_DIR}/mysql/conf" \
    "${VOLUME_DIR}/mysql/data" \
    "${VOLUME_DIR}/mysql/logs" \
    "${VOLUME_DIR}/postgresql/conf" \
    "${VOLUME_DIR}/postgresql/data" \
    "${VOLUME_DIR}/postgresql/logs" \
    "${VOLUME_DIR}/redis/conf" \
    "${VOLUME_DIR}/redis/data" \
    "${VOLUME_DIR}/redis/logs" \
    "${VOLUME_DIR}/nacos/data" \
    "${VOLUME_DIR}/nacos/logs" \
    "${HOME}"

  info "配置.env文件..."
  if [ -f "${ENV_FILE}" ]; then
    CONTAINER_USER="$(id -u)"
    CONTAINER_GROUP="$(id -g)"
    MYSQL_ROOT_PASSWORD="$(openssl rand -hex 16)"
    POSTGRES_PASSWORD="$(openssl rand -hex 16)"
    REDIS_PASSWORD="$(openssl rand -hex 16)"
    NACOS_AUTH_TOKEN="$(openssl rand -base64 32)"

    if grep -Eq '^DOCKER_USER=.+$' "$ENV_FILE"; then
      warn "DOCKER_USER 已有值，跳过"
    elif grep -Eq '^DOCKER_USER=$' "$ENV_FILE"; then
      sed -i "s|^DOCKER_USER=$|DOCKER_USER=${CONTAINER_USER}|" "$ENV_FILE"
    else
      info "DOCKER_USER=${CONTAINER_USER}" >>"$ENV_FILE"
    fi

    if grep -Eq '^DOCKER_USERGROUP=.+$' "$ENV_FILE"; then
      warn "DOCKER_USERGROUP 已有值，跳过"
    elif grep -Eq '^DOCKER_USERGROUP=$' "$ENV_FILE"; then
      sed -i "s|^DOCKER_USERGROUP=$|DOCKER_USERGROUP=${CONTAINER_GROUP}|" "$ENV_FILE"
    else
      info "DOCKER_USERGROUP=${CONTAINER_GROUP}" >>"$ENV_FILE"
    fi

    if grep -Eq '^MYSQL_ROOT_PASSWORD=.+$' "$ENV_FILE"; then
      warn "MYSQL_ROOT_PASSWORD 已有值，跳过"
    elif grep -Eq '^MYSQL_ROOT_PASSWORD=$' "$ENV_FILE"; then
      sed -i "s|^MYSQL_ROOT_PASSWORD=$|MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}|" "$ENV_FILE"
    else
      info "MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}" >>"$ENV_FILE"
    fi

    if grep -Eq '^POSTGRES_PASSWORD=.+$' "$ENV_FILE"; then
      warn "POSTGRES_PASSWORD 已有值，跳过"
    elif grep -Eq '^POSTGRES_PASSWORD=$' "$ENV_FILE"; then
      sed -i "s|^POSTGRES_PASSWORD=$|POSTGRES_PASSWORD=${POSTGRES_PASSWORD}|" "$ENV_FILE"
    else
      info "POSTGRES_PASSWORD=${POSTGRES_PASSWORD}" >>"$ENV_FILE"
    fi

    if grep -Eq '^REDIS_PASSWORD=.+$' "$ENV_FILE"; then
      warn "REDIS_PASSWORD 已有值，跳过"
    elif grep -Eq '^REDIS_PASSWORD=$' "$ENV_FILE"; then
      sed -i "s|^REDIS_PASSWORD=$|REDIS_PASSWORD=${REDIS_PASSWORD}|" "$ENV_FILE"
    else
      info "REDIS_PASSWORD=${REDIS_PASSWORD}" >>"$ENV_FILE"
    fi

    if grep -Eq '^NACOS_AUTH_TOKEN=.+$' "$ENV_FILE"; then
      warn "NACOS_AUTH_TOKEN 已有值，跳过"
    elif grep -Eq '^NACOS_AUTH_TOKEN=$' "$ENV_FILE"; then
      sed -i "s|^NACOS_AUTH_TOKEN=$|NACOS_AUTH_TOKEN=${NACOS_AUTH_TOKEN}|" "$ENV_FILE"
    else
      info "NACOS_AUTH_TOKEN=${NACOS_AUTH_TOKEN}" >>"$ENV_FILE"
    fi

    info ".env文件已更新，已生成随机密码并设置容器用户权限"
  else
    error ".env文件不存在，请确保${SCRIPT_DIR}目录下有正确的.env文件模板"
  fi
}

# 等待服务健康
wait_healthy() {
  info "等待所有服务启动完成..."
  START_TS=$(date +%s)
  for SVC in $SERVICE_LIST; do
    info "正在检查服务 ${SVC} 的健康状态..."
    while true; do
      # 优先用docker原生inspect读取健康状态，不依赖compose版本，兼容性更强
      HEALTH_STATUS=$(docker inspect --format='{{.State.Health.Status}}' "${SVC}" 2>/dev/null || echo "unhealthy")
      # POSIX标准比较语法：单等号、单中括号、变量全加引号防止空值报错
      if [ "${HEALTH_STATUS}" = "healthy" ]; then
        info "服务 ${SVC} 健康检查通过"
        break
      fi
      # POSIX标准算术运算
      NOW_TS=$(date +%s)
      if [ $((NOW_TS - START_TS)) -ge "${WAIT_TIMEOUT}" ]; then
        warn "等待服务 ${SVC} 超时，当前状态：${HEALTH_STATUS}"
      fi
      sleep "${CHECK_INTERVAL}"
    done
  done
  info "所有服务启动成功！"
  printf "==================== 访问地址 ====================\n"
  printf "工作目录：%s\n" "${SCRIPT_DIR}"
  printf "Nacos控制台：http://${SERVER_DOMAIN}:%s  默认用户名：nacos  初始化需要设置密码\n" "$(grep 'NACOS_HTTP_PORT' "${ENV_FILE}" | cut -d '=' -f2)"
  printf "MySQL连接：${SERVER_DOMAIN}:%s  root/%s\n" "$(grep 'MYSQL_PORT' "${ENV_FILE}" | cut -d '=' -f2)" "$(grep 'MYSQL_ROOT_PASSWORD' "${ENV_FILE}" | cut -d '=' -f2)"
  printf "PostgreSQL连接：${SERVER_DOMAIN}:%s  %s/%s\n" "$(grep 'POSTGRESQL_PORT' "${ENV_FILE}" | cut -d '=' -f2)" "$(grep 'POSTGRES_USER' "${ENV_FILE}" | cut -d '=' -f2)" "$(grep 'POSTGRES_PASSWORD' "${ENV_FILE}" | cut -d '=' -f2)"
  printf "Redis连接：${SERVER_DOMAIN}:%s  %s\n" "$(grep 'REDIS_PORT' "${ENV_FILE}" | cut -d '=' -f2)" "$(grep 'REDIS_PASSWORD' "${ENV_FILE}" | cut -d '=' -f2)"
  printf "=================================================\n"
}

# ========================== 命令入口 ==========================
CMD="${1:-start}"
case "${CMD}" in
start)
  check_docker_perm
  init_resources
  info "启动服务（使用docker用户组权限）..."
  ${DOCKER_COMPOSE} -f "${COMPOSE_FILE}" up -d --remove-orphans
  wait_healthy
  ;;
stop)
  check_docker_perm
  info "停止服务..."
  ${DOCKER_COMPOSE} -f "${COMPOSE_FILE}" stop
  ;;
restart)
  check_docker_perm
  info "重启服务..."
  ${DOCKER_COMPOSE} -f "${COMPOSE_FILE}" restart
  wait_healthy
  ;;
logs)
  check_docker_perm
  info "查看服务日志，按Ctrl+C退出"
  ${DOCKER_COMPOSE} -f "${COMPOSE_FILE}" logs -f ${2:-}
  ;;
ps)
  check_docker_perm
  ${DOCKER_COMPOSE} -f "${COMPOSE_FILE}" ps
  ;;
clean)
  check_docker_perm
  warn "即将停止所有服务并删除${SCRIPT_DIR}下所有持久化数据，该操作不可恢复！"
  printf "确认执行？输入yes继续："
  read -r confirm
  if [ "${confirm}" = "yes" ]; then
    info "清理环境..."
    ${DOCKER_COMPOSE} -f "${COMPOSE_FILE}" down -v
    sudo rm -rf "${VOLUME_DIR}" "${INIT_DIR}" "${ENV_FILE}"
    info "清理完成"
  else
    info "已取消清理操作"
  fi
  ;;
*)
  printf "使用方法：\n"
  printf "  ./docker-service.run.sh start      # 启动服务（docker用户组权限）\n"
  printf "  ./docker-service.run.sh stop       # 停止服务\n"
  printf "  ./docker-service.run.sh restart    # 重启服务\n"
  printf "  ./docker-service.run.sh logs [服务名] # 查看日志，例：./docker-service.run.sh logs nacos-service\n"
  printf "  ./docker-service.run.sh ps         # 查看服务运行状态\n"
  printf "  ./docker-service.run.sh clean      # 清理环境（删除所有数据）\n"
  ;;
esac
