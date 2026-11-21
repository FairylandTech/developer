#!/usr/bin/env bash
# 逻辑备份：每个业务库 pg_dump 自定义格式 + 全局对象（角色等），保留最近 N 天
# crontab 示例（日志写到备份目录下）：
#   0 3 * * * /path/to/postgres-docker/backup.sh >> $HOME/volumes/postgresql/backup/backup.log 2>&1
set -euo pipefail
# cron 环境 PATH 很精简，补上 docker 常见安装路径（Homebrew / Docker Desktop）
export PATH="/usr/local/bin:/opt/homebrew/bin:/Applications/Docker.app/Contents/Resources/bin:$PATH"
cd "$(dirname "$0")"
set -a; source .env; set +a

KEEP_DAYS=${KEEP_DAYS:-7}
TS=$(date +%Y%m%d_%H%M%S)
# 以容器内 postgres 用户执行，生成的文件属主为 999，宿主机清理时不会遇到 root 文件
DEXEC=(docker exec -u postgres "$PG_CONTAINER_NAME")

# 全局对象：角色、表空间（pg_dump 不包含这些）
"${DEXEC[@]}" pg_dumpall -U "$POSTGRES_USER" --globals-only -f "/backup/globals_${TS}.sql"
echo "$(date '+%F %T') backup ok: ${PG_BACKUP_DIR}/globals_${TS}.sql"

# 所有非模板库
DBS=$("${DEXEC[@]}" psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -Atc \
  "SELECT datname FROM pg_database WHERE NOT datistemplate AND datallowconn ORDER BY 1")

for db in $DBS; do
  file="/backup/db_${db}_${TS}.dump"
  "${DEXEC[@]}" pg_dump -U "$POSTGRES_USER" -d "$db" -Fc -Z 6 -f "$file"
  echo "$(date '+%F %T') backup ok: ${PG_BACKUP_DIR}/$(basename "$file")"
done

# 清理过期备份（-mtime +N 表示超过 N*24 小时）
find "$PG_BACKUP_DIR" -maxdepth 1 -type f \( -name 'db_*.dump' -o -name 'globals_*.sql' \) \
  -mtime +"$KEEP_DAYS" -print -delete
