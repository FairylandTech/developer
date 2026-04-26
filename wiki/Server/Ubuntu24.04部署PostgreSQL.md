# Ubuntu 24.04 部署 PostgreSQL

> @software: Typora  
> @author: [Beau Dean](https://fairy.host)  
> @contact: [Blog](https://blog.fairy.host/) | [GitHub](https://github.com/FairylandTech) | [Telegram](https://t.me/FairylandFuture)  
> @organization: [GitHub·FairylandFuture](https://github.com/FairylandFuture)  
> @datetime: 2026-04-26 14:01:57 UTC+08:00

[![Author](https://img.shields.io/badge/Author-Beau_Dean-orange)](https://t.me/FairylandFuture) [![github](https://img.shields.io/badge/Github-FairylandFuture-green)](https://github.com/FairylandTech) [![GitBook](https://img.shields.io/badge/GitBook-Interesting-green)](https://interestingbooks.gitbook.io/) [![Editor](https://img.shields.io/badge/Editor-Typora-yellow)]() [![Language](https://img.shields.io/badge/Language-Markdown-orange)]() [![Version](https://img.shields.io/badge/Version-Release-blue)]() [![Docs](https://img.shields.io/badge/Docs-Passing-brightgreen)]() [![Type](https://img.shields.io/badge/Type-Documents-blue)]() [![wakatime](https://wakatime.com/badge/user/fa851759-c657-4b1e-8bcb-3ec3a693a2cd.svg)](https://wakatime.com/@fa851759-c657-4b1e-8bcb-3ec3a693a2cd) [![Sign](https://img.shields.io/badge/%E7%AD%89%E6%88%91%E4%BB%A3%E7%A0%81%E7%BC%96%E6%88%90-%E5%A8%B6%E4%BD%A0%E4%B8%BA%E5%A6%BB%E5%8F%AF%E5%A5%BD-red)](https://github.com/FairylandTech)

---

## PostgreSQL Apt 仓库

1.   自动化仓库配置

     ```bash
     sudo apt install -y postgresql-common
     sudo /usr/share/postgresql-common/pgdg/apt.postgresql.org.sh
     ```

2.   手动配置
     官方下载慢的情况可以考虑使用镜像源站的 PostgreSQL 源
     阿里云：https://mirrors.aliyun.com/postgresql

     腾讯源：https://mirrors.cloud.tencent.com/postgresql

     ```bash
     # 导入仓库签名秘钥
     sudo apt install -y curl gpg gnupg2 software-properties-common apt-transport-https lsb-release ca-certificates
     curl -fsSL https://mirrors.cloud.tencent.com/postgresql/repos/apt/ACCC4CF8.asc | sudo gpg --dearmor -o /etc/apt/trusted.gpg.d/postgresql.gpg
     # 创建存储库配置文件
     echo "deb https://mirrors.cloud.tencent.com/postgresql/repos/apt `lsb_release -cs`-pgdg main" |sudo tee  /etc/apt/sources.list.d/pgdg.list
     # 更新软件包列表
     sudo apt update
     ```

     官方源：https://ftp.postgresql.org/pub

     ```bash
     # 导入仓库签名秘钥
     sudo apt install -y curl gpg gnupg2 software-properties-common apt-transport-https lsb-release ca-certificates
     sudo install -d /usr/share/postgresql-common/pgdg
     sudo curl -o /usr/share/postgresql-common/pgdg/apt.postgresql.org.asc --fail https://postgresql.ac.cn/media/keys/ACCC4CF8.asc
     # 创建存储库配置文件
     . /etc/os-release
     sudo sh -c "echo 'deb [signed-by=/usr/share/postgresql-common/pgdg/apt.postgresql.org.asc] https://apt.postgresql.org/pub/repos/apt $VERSION_CODENAME-pgdg main' > /etc/apt/sources.list.d/pgdg.list"
     # 更新软件包列表
     sudo apt update
     ```

## 安装 PostgreSQL

```bash
# 替换为安装的版本，如果是18版本的话就执行 `sudo apt install postgresql-18`，这是使用的是16版本
# *postgresql-16             核心服务包
# *postgresql-client-16      客户端库和客户端二进制文件
# *postgresql-doc-16         文档
# *libpq-dev                 用于 C 语言前端开发的库和头文件
# *postgresql-server-dev-16  用于 C 语言后端开发的库和头文件
# *postgresql-contrib-16     包含额外工具（如 pg_stat_statements）
sudo apt install -y postgresql-16 postgresql-client-16 postgresql-doc-16 postgresql-contrib-16
# 启动并设置开机启动
sudo systemctl enable --now postgresql
```

## 验证安装结果

```bash
# 查看服务状态；输出应显示 active (running)。
sudo systemctl status postgresql
# 查看监听端口（默认端口 5432）；正常情况下会显示 LISTEN 状态。
sudo netstat -tulpn | grep 5432
# 检验版本；输出应该包含 PostgreSQL 版本信息，如：PostgreSQL 16.13 (Ubuntu 16.13-1.pgdg24.04+1) on x86_64-pc-linux-gnu, compiled by gcc (Ubuntu 13.3.0-6ubuntu2~24.04.1) 13.3.0, 64-bit
sudo -u postgres psql -c "SELECT version();"
```

## PostgreSQL 基础配置

>   用户加入到 postgres 用户组：`sudo usermod -aG postgres $USER`

### 核心配置文件详解

PostgreSQL 的配置文件位于数据库集群目录（通常为 `/var/lib/postgresql/16/main/` 或 `/var/lib/pgsql/16/data/`）但是启动服务时默认会指定 config 的目录为`/etc/postgresql/16/main`，关键文件包括：

| 文件              | 作用                                                     |
| ----------------- | -------------------------------------------------------- |
| `postgresql.conf` | 主配置文件，控制全局参数（如监听地址、内存分配、日志等） |
| `pg_hba.conf`     | 客户端认证配置，控制哪些用户/IP 可访问数据库及认证方式   |
| `pg_ident.conf`   | 身份映射配置，将 OS 用户映射为数据库用户（较少使用）     |
| `postmaster.opts` | 记录服务启动参数                                         |

**配置文件路径查询**：若不确定数据目录位置，可通过以下命令获取：

```bash
sudo -u postgres psql -c "SHOW data_directory;"
```

### 配置参数优化建议

编辑 `postgresql.conf`，根据服务器硬件调整以下关键参数（生产环境建议）：

```conf
# 内存配置（根据物理内存调整，生产环境推荐）
shared_buffers = 1GB           # 数据库缓存区，建议物理内存的 1/4（如 4GB 内存设为 1GB）
work_mem = 64MB                # 每个连接的排序/哈希操作内存，视并发数调整（总内存 = work_mem * 并发数）
maintenance_work_mem = 256MB   # 维护操作（如 VACUUM、CREATE INDEX）的内存，建议物理内存的 1/16
 
# 连接配置
max_connections = 100          # 最大并发连接数（默认 100，根据业务需求调整，过高会消耗内存）
listen_addresses = 'localhost' # 监听地址（默认仅本地，远程访问需改为 '*'）
 
# 日志配置（建议开启详细日志便于排障）
log_destination = 'stderr'
logging_collector = on
log_directory = 'log'
log_filename = 'postgresql-%Y-%m-%d.log'
log_min_error_statement = 'warning'  # 记录警告及以上级别错误
log_min_duration_statement = 1000    # 记录执行时间 >1s 的 SQL（慢查询日志）
 
# 性能优化
effective_cache_size = 3GB    # 建议物理内存的 3/4（帮助优化器估算可用内存）
random_page_cost = 1.1        # SSD 存储设为 1.1，机械硬盘设为 4.0
```

**修改配置后重启服务生效**

## 用户与数据库管理

```sql
# 创建角色
create role dev with login password '强密码' createdb nosuperuser;
# 创建数据库
create database dev with owner = dev encoding = 'UTF8' lc_collate = 'en_US.UTF-8' lc_ctype = 'en_US.UTF-8';
# 权限
grant all privileges on database dev to dev;
```

