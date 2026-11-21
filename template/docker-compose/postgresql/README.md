# PostgreSQL Docker 部署方案

## 目录结构

```
postgres-docker/
├── docker-compose.yml     # 服务定义 + volumes 挂载块
├── .env                   # 版本、账号、端口、本地目录、资源限制
├── conf/
│   ├── postgresql.conf    # 优化后的主配置（2C/1G/SSD 基准，与 .env 对应）
│   └── pg_hba.conf        # 访问控制
├── init/01-init.sh        # 首次初始化：创建 dev 角色/库、启用扩展
├── setup.sh               # 创建本地挂载目录
└── backup.sh              # 全库 pg_dump + 全局对象备份脚本

# 数据实际落在 .env 指定的目录（默认）：
~/volumes/postgresql/
├── data/                  # → /var/lib/postgresql/data（数据）
├── logs/                  # → /var/log/postgresql（数据库日志）
└── backup/                # → /backup（备份文件）
```

## 挂载方式

`docker-compose.yml` 底部的 `volumes:` 块定义命名卷，通过 `driver_opts` 把卷绑定到 `.env` 中指定的本地绝对路径：

```yaml
volumes:
  pg_data:
    driver: local
    driver_opts:
      type: none
      o: bind
      device: ${PG_DATA_DIR}
```

这样既能用 `docker volume ls` 管理，数据又实际落在指定目录。**目录必须提前存在**，否则启动报 `no such file or directory`。

## 部署步骤

1. 修改 `.env`：`POSTGRES_PASSWORD`、`DEV_DB_PASSWORD`、`PG_*_DIR` 本地路径、资源限制；需要远程访问时把 `PG_BIND_ADDR` 改为 `0.0.0.0`。`.env` 含密码，勿提交到 git。
2. 创建目录：
   ```bash
   chmod +x setup.sh backup.sh && ./setup.sh
   ```
3. 启动：
   ```bash
   docker compose up -d
   ```
4. 查看状态与连接：
   ```bash
   docker compose ps
   docker exec -it postgresql-service psql -U postgres -d dev
   ```

> macOS（Docker Desktop）：`/Volumes/...` 外置盘需在 Settings → Resources → File sharing 中加入共享路径。

## 配置优化要点（按机器规格调整）

| 参数 | 公式 | 当前（2C/1G） | 4C/8G 示例 |
|---|---|---|---|
| shared_buffers | RAM × 25% | 256MB | 2GB |
| effective_cache_size | RAM × 75% | 768MB | 6GB |
| work_mem | (RAM − shared_buffers) / (max_connections × 3) | 4MB | 16MB |
| maintenance_work_mem | RAM × 5~10%，≤ 2GB | 64MB | 512MB |
| max_parallel_workers | CPU 核数 | 2 | 4 |
| max_parallel_workers_per_gather | CPU / 2 | 1 | 2 |
| random_page_cost | SSD 1.1 / HDD 4 | 1.1 | 1.1 |
| effective_io_concurrency | SSD 200 / HDD 2 | 200 | 200 |

修改规格时同步调整 `.env` 的 `PG_CPUS` / `PG_MEM_LIMIT` / `PG_SHM_SIZE`。

其他：
- `shm_size`（`.env` 中 `PG_SHM_SIZE`，当前 256m）：Docker 默认 `/dev/shm` 只有 64MB，并行查询会报 `could not resize shared memory segment`。
- `--data-checksums`：初始化时开启数据页校验，便于发现磁盘损坏。
- `pg_stat_statements` 已预加载，慢查询分析：
  ```sql
  SELECT query, calls, mean_exec_time, total_exec_time
  FROM pg_stat_statements ORDER BY total_exec_time DESC LIMIT 20;
  ```
- 修改 `postgresql.conf` 后：大部分参数 `SELECT pg_reload_conf();` 即可生效；`shared_buffers`、`max_connections`、`shared_preload_libraries` 等需 `docker compose restart`。

## 运维

```bash
# 备份所有库 + 角色（保留 7 天，KEEP_DAYS=14 ./backup.sh 可调整）
./backup.sh

# 恢复：先恢复角色（新实例时），再恢复库
docker exec -i -u postgres postgresql-service psql -U postgres -f /backup/globals_YYYYmmdd_HHMMSS.sql
docker exec -i -u postgres postgresql-service pg_restore -U postgres -d dev --clean --if-exists /backup/db_dev_YYYYmmdd_HHMMSS.dump

# 查看数据库日志
tail -f ~/volumes/postgresql/logs/postgresql-$(date +%F).log
```

## 注意

- `init/` 下脚本只在数据目录为空时执行一次；若初始化中途失败，必须清空数据目录后再启动，否则不会重跑。
- 升级大版本（如 17 → 18）不能直接改镜像 tag，需 `pg_dump` 导出再导入，或使用 `pg_upgrade`。
- 端口默认只绑定 `127.0.0.1`。开放远程访问时，在 `pg_hba.conf` 中按实际网段放开（不要用 `0.0.0.0/0`）。注意 Docker 端口映射会绕过 ufw/iptables。
