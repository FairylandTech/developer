/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

create database if not exists `nacos` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `nacos`;
/******************************************/
/*   表名称 = config_info                  */
/******************************************/
create table `config_info`
(
    `id`                 bigint(20)    not null auto_increment comment 'id',
    `data_id`            varchar(255)  not null comment 'data_id',
    `group_id`           varchar(128)           default null comment 'group_id',
    `content`            longtext      not null comment 'content',
    `md5`                varchar(32)            default null comment 'md5',
    `gmt_create`         datetime      not null default current_timestamp comment '创建时间',
    `gmt_modified`       datetime      not null default current_timestamp comment '修改时间',
    `src_user`           text comment 'source user',
    `src_ip`             varchar(50)            default null comment 'source ip',
    `app_name`           varchar(128)           default null comment 'app_name',
    `tenant_id`          varchar(128)           default '' comment '租户字段',
    `c_desc`             varchar(256)           default null comment 'configuration description',
    `c_use`              varchar(64)            default null comment 'configuration usage',
    `effect`             varchar(64)            default null comment '配置生效的描述',
    `type`               varchar(64)            default null comment '配置的类型',
    `c_schema`           text comment '配置的模式',
    `encrypted_data_key` varchar(1024) not null default '' comment '密钥',
    primary key (`id`),
    unique key `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci comment ='config_info';

/******************************************/
/*   表名称 = config_info  since 2.5.0                */
/******************************************/
create table `config_info_gray`
(
    `id`                 bigint unsigned not null auto_increment comment 'id',
    `data_id`            varchar(255)    not null comment 'data_id',
    `group_id`           varchar(128)    not null comment 'group_id',
    `content`            longtext        not null comment 'content',
    `md5`                varchar(32)              default null comment 'md5',
    `src_user`           text comment 'src_user',
    `src_ip`             varchar(100)             default null comment 'src_ip',
    `gmt_create`         datetime(3)     not null default current_timestamp(3) comment 'gmt_create',
    `gmt_modified`       datetime(3)     not null default current_timestamp(3) comment 'gmt_modified',
    `app_name`           varchar(128)             default null comment 'app_name',
    `tenant_id`          varchar(128)             default '' comment 'tenant_id',
    `gray_name`          varchar(128)    not null comment 'gray_name',
    `gray_rule`          text            not null comment 'gray_rule',
    `encrypted_data_key` varchar(256)    not null default '' comment 'encrypted_data_key',
    primary key (`id`),
    unique key `uk_configinfogray_datagrouptenantgray` (`data_id`, `group_id`, `tenant_id`, `gray_name`),
    key `idx_dataid_gmt_modified` (`data_id`, `gmt_modified`),
    key `idx_gmt_modified` (`gmt_modified`)
) engine = InnoDB
  auto_increment = 1
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci comment ='config_info_gray';

/******************************************/
/*   表名称 = config_tags_relation         */
/******************************************/
create table `config_tags_relation`
(
    `id`        bigint(20)   not null comment 'id',
    `tag_name`  varchar(128) not null comment 'tag_name',
    `tag_type`  varchar(64)  default null comment 'tag_type',
    `data_id`   varchar(255) not null comment 'data_id',
    `group_id`  varchar(128) not null comment 'group_id',
    `tenant_id` varchar(128) default '' comment 'tenant_id',
    `nid`       bigint(20)   not null auto_increment comment 'nid, 自增长标识',
    primary key (`nid`),
    unique key `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`),
    key `idx_tenant_id` (`tenant_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci comment ='config_tag_relation';

/******************************************/
/*   表名称 = group_capacity               */
/******************************************/
create table `group_capacity`
(
    `id`                bigint(20) unsigned not null auto_increment comment '主键ID',
    `group_id`          varchar(128)        not null default '' comment 'Group ID，空字符表示整个集群',
    `quota`             int(10) unsigned    not null default '0' comment '配额，0表示使用默认值',
    `usage`             int(10) unsigned    not null default '0' comment '使用量',
    `max_size`          int(10) unsigned    not null default '0' comment '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned    not null default '0' comment '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     int(10) unsigned    not null default '0' comment '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned    not null default '0' comment '最大变更历史数量',
    `gmt_create`        datetime            not null default current_timestamp comment '创建时间',
    `gmt_modified`      datetime            not null default current_timestamp comment '修改时间',
    primary key (`id`),
    unique key `uk_group_id` (`group_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci comment ='集群、各Group容量信息表';

/******************************************/
/*   表名称 = his_config_info              */
/******************************************/
create table `his_config_info`
(
    `id`                 bigint(20) unsigned not null comment 'id',
    `nid`                bigint(20) unsigned not null auto_increment comment 'nid, 自增标识',
    `data_id`            varchar(255)        not null comment 'data_id',
    `group_id`           varchar(128)        not null comment 'group_id',
    `app_name`           varchar(128)                 default null comment 'app_name',
    `content`            longtext            not null comment 'content',
    `md5`                varchar(32)                  default null comment 'md5',
    `gmt_create`         datetime            not null default current_timestamp comment '创建时间',
    `gmt_modified`       datetime            not null default current_timestamp comment '修改时间',
    `src_user`           text comment 'source user',
    `src_ip`             varchar(50)                  default null comment 'source ip',
    `op_type`            char(10)                     default null comment 'operation type',
    `tenant_id`          varchar(128)                 default '' comment '租户字段',
    `encrypted_data_key` varchar(1024)       not null default '' comment '密钥',
    `publish_type`       varchar(50)                  default 'formal' comment 'publish type gray or formal',
    `gray_name`          varchar(50)                  default null comment 'gray name',
    `ext_info`           longtext                     default null comment 'ext info',
    primary key (`nid`),
    key `idx_gmt_create` (`gmt_create`),
    key `idx_gmt_modified` (`gmt_modified`),
    key `idx_did` (`data_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci comment ='多租户改造';


/******************************************/
/*   表名称 = tenant_capacity              */
/******************************************/
create table `tenant_capacity`
(
    `id`                bigint(20) unsigned not null auto_increment comment '主键ID',
    `tenant_id`         varchar(128)        not null default '' comment 'Tenant ID',
    `quota`             int(10) unsigned    not null default '0' comment '配额，0表示使用默认值',
    `usage`             int(10) unsigned    not null default '0' comment '使用量',
    `max_size`          int(10) unsigned    not null default '0' comment '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned    not null default '0' comment '聚合子配置最大个数',
    `max_aggr_size`     int(10) unsigned    not null default '0' comment '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned    not null default '0' comment '最大变更历史数量',
    `gmt_create`        datetime            not null default current_timestamp comment '创建时间',
    `gmt_modified`      datetime            not null default current_timestamp comment '修改时间',
    primary key (`id`),
    unique key `uk_tenant_id` (`tenant_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci comment ='租户容量信息表';


create table `tenant_info`
(
    `id`            bigint(20)   not null auto_increment comment 'id',
    `kp`            varchar(128) not null comment 'kp',
    `tenant_id`     varchar(128) default '' comment 'tenant_id',
    `tenant_name`   varchar(128) default '' comment 'tenant_name',
    `tenant_desc`   varchar(256) default null comment 'tenant_desc',
    `create_source` varchar(32)  default null comment 'create_source',
    `gmt_create`    bigint(20)   not null comment '创建时间',
    `gmt_modified`  bigint(20)   not null comment '修改时间',
    primary key (`id`),
    unique key `uk_tenant_info_kptenantid` (`kp`, `tenant_id`),
    key `idx_tenant_id` (`tenant_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci comment ='tenant_info';

create table `users`
(
    `username` varchar(50)  not null primary key comment 'username',
    `password` varchar(500) not null comment 'password',
    `enabled`  boolean      not null comment 'enabled'
);

create table `roles`
(
    `username` varchar(50) not null comment 'username',
    `role`     varchar(50) not null comment 'role',
    unique index `idx_user_role` (`username` asc, `role` asc) using btree
);

create table `permissions`
(
    `role`     varchar(50)  not null comment 'role',
    `resource` varchar(128) not null comment 'resource',
    `action`   varchar(8)   not null comment 'action',
    unique index `uk_role_permission` (`role`, `resource`, `action`) using btree
);


/******************************************/
/*   表名称 = pipeline_execution           */
/******************************************/
create table `pipeline_execution`
(
    `execution_id`  varchar(64)  not null comment '执行ID',
    `resource_type` varchar(32)  not null comment '资源类型',
    `resource_name` varchar(256) not null comment '资源名称',
    `namespace_id`  varchar(128) default null comment '命名空间ID',
    `version`       varchar(64)  default null comment '版本',
    `status`        varchar(32)  not null comment '执行状态',
    `pipeline`      longtext     not null comment 'pipeline节点结果JSON',
    `create_time`   bigint(20)   not null comment '创建时间',
    `update_time`   bigint(20)   not null comment '修改时间',
    primary key (`execution_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci comment ='AI资源发布审核Pipeline执行记录';

/******************************************/
/*   表名称 = ai_resource                 */
/******************************************/
create table `ai_resource`
(
    `id`             bigint(20)   not null auto_increment comment 'id',
    `gmt_create`     datetime     not null default current_timestamp comment '创建时间',
    `gmt_modified`   datetime     not null default current_timestamp comment '修改时间',
    `name`           varchar(256) not null comment '资源名称',
    `type`           varchar(32)  not null comment '资源类型',
    `c_desc`         varchar(2048)         default null comment '资源描述',
    `status`         varchar(32)           default null comment '资源状态',
    `namespace_id`   varchar(128) not null default '' comment '命名空间ID',
    `biz_tags`       varchar(1024)         default null comment '业务标签',
    `ext`            longtext              default null comment '扩展信息(JSON)',
    `c_from`         varchar(256) not null default 'local' comment '来源标识(导入/同步来源)',
    `version_info`   longtext              default null comment '版本信息(JSON)',
    `meta_version`   bigint(20)   not null default 1 comment '元数据版本(乐观锁)',
    `scope`          varchar(16)  not null default 'PRIVATE' comment '可见性: PUBLIC/PRIVATE',
    `owner`          varchar(128) not null default '' comment '创建者用户名',
    `download_count` bigint(20)   not null default 0 comment '下载次数',
    primary key (`id`),
    unique key `uk_ai_resource_ns_name_type` (`namespace_id`, `name`, `type`, `c_from`),
    key `idx_ai_resource_name` (`name`),
    key `idx_ai_resource_type` (`type`),
    key `idx_ai_resource_gmt_modified` (`gmt_modified`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci comment ='AI资源元数据表';

/******************************************/
/*   表名称 = ai_resource_version         */
/******************************************/
create table `ai_resource_version`
(
    `id`                    bigint(20)   not null auto_increment comment 'id',
    `gmt_create`            datetime     not null default current_timestamp comment '创建时间',
    `gmt_modified`          datetime     not null default current_timestamp comment '修改时间',
    `type`                  varchar(32)  not null comment '资源类型',
    `author`                varchar(128)          default null comment '作者',
    `name`                  varchar(256) not null comment '资源名称',
    `c_desc`                varchar(2048)         default null comment '版本描述',
    `status`                varchar(32)  not null comment '版本状态',
    `version`               varchar(64)  not null comment '版本号',
    `namespace_id`          varchar(128) not null default '' comment '命名空间ID',
    `storage`               longtext              default null comment '存储信息(JSON)',
    `publish_pipeline_info` longtext              default null comment '发布流水线信息(JSON)',
    `download_count`        bigint(20)   not null default 0 comment '下载次数',
    primary key (`id`),
    unique key `uk_ai_resource_ver_ns_name_type_ver` (`namespace_id`, `name`, `type`, `version`),
    key `idx_ai_resource_ver_name` (`name`),
    key `idx_ai_resource_ver_status` (`status`),
    key `idx_ai_resource_ver_gmt_modified` (`gmt_modified`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_unicode_ci comment ='AI资源版本表';
