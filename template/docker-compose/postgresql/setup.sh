#!/usr/bin/env bash
# 创建本地挂载目录并设置权限（首次部署前执行一次）
set -euo pipefail
cd "$(dirname "$0")"

set -a; source .env; set +a

for d in "$PG_DATA_DIR" "$PG_LOG_DIR" "$PG_BACKUP_DIR"; do
  mkdir -p "$d"
  echo "created: $d"
done

if [[ "$(uname -s)" == "Linux" ]]; then
  sudo chown -R 999:999 "$PG_DATA_DIR" "$PG_LOG_DIR" "$PG_BACKUP_DIR"
  sudo chmod 700 "$PG_DATA_DIR"
fi

echo "done. 下一步: docker compose up -d"
