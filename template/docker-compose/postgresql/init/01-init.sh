#!/usr/bin/env bash
# 仅在数据目录为空（首次初始化）时由官方 entrypoint 执行
# 密码从环境变量 DEV_DB_PASSWORD 读取，避免明文写在仓库里
set -euo pipefail

: "${DEV_DB_PASSWORD:?DEV_DB_PASSWORD 未设置}"

# 默认库启用 pg_stat_statements
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<'SQL'
CREATE EXTENSION IF NOT EXISTS pg_stat_statements;
SQL

# 创建业务角色（密码通过 psql 变量安全引用）
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" \
     -v dev_pw="$DEV_DB_PASSWORD" <<'SQL'
CREATE ROLE dev WITH LOGIN PASSWORD :'dev_pw';
SQL

# CREATE DATABASE 不能在事务块中执行，单独执行
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" \
     -c 'CREATE DATABASE dev OWNER dev;'

# dev 库同样启用 pg_stat_statements
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname dev <<'SQL'
CREATE EXTENSION IF NOT EXISTS pg_stat_statements;
SQL
