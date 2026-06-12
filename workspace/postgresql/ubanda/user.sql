create schema if not exists user_center authorization ubanda;
comment on schema user_center is '用户管理中心';

-- ==================================================================
-- Table: 用户表 user
-- ==================================================================
create table user_center.user
(
    id            serial primary key,
    username      varchar(64)  not null,
    password_hash varchar(255),
    password_salt varchar(64),
    nickname      varchar(64),
    email         varchar(128),
    phone         varchar(11),
    gender        varchar(16),
    status        character(1) not null default 'Y',
    last_login_at timestamptz,
    remark        varchar(255),
    created_at    timestamptz  not null default current_timestamp,
    updated_at    timestamptz  not null default current_timestamp,
    deleted_at    timestamptz,
    created_by    integer,
    updated_by    integer,
    deleted_by    integer,
    enabled       character(1) not null default 'Y'
);

create unique index uk_user_phone on user_center.user (phone) where enabled = 'Y' and phone is not null;
comment on table user_center.user is '用户基本信息表';
comment on column user_center.user.id is '主键ID';
comment on column user_center.user.username is '登录用户名 唯一';
comment on column user_center.user.password_hash is '密码';
comment on column user_center.user.password_salt is '密码盐值';
comment on column user_center.user.nickname is '昵称';
comment on column user_center.user.email is '邮箱';
comment on column user_center.user.phone is '手机号';
comment on column user_center.user.gender is '性别 MALE-男 FEMALE-女 UNKNOWN-未知';
comment on column user_center.user.status is '账号状态 Y-正常 N-禁用';
comment on column user_center.user.last_login_at is '最近一次登录时间';
comment on column user_center.user.remark is '备注';
comment on column user_center.user.created_at is '创建时间';
comment on column user_center.user.updated_at is '更新时间';
comment on column user_center.user.deleted_at is '删除时间';
comment on column user_center.user.created_by is '创建人ID';
comment on column user_center.user.updated_by is '更新人ID';
comment on column user_center.user.deleted_by is '删除人ID';
comment on column user_center.user.enabled is '逻辑删除标识符 Y-正常 N-逻辑删除';

-- ==================================================================
-- Table: 租户表 tenant
-- ==================================================================
create table if not exists user_center.tenant
(
    id              serial primary key,
    user_id         integer      not null,
    tenant_type     varchar(64)  not null,
    tenant_code     varchar(64)  not null,
    tenant_name     varchar(128) not null,
    tenant_metadata jsonb,
    created_at      timestamptz  not null default current_timestamp,
    updated_at      timestamptz  not null default current_timestamp,
    enabled         character(1)          default 'Y'
);
create unique index uk_tenant_user_id_tenant_code on user_center.tenant (user_id, tenant_code) where enabled = 'Y';
comment on table user_center.tenant is '租户表';
comment on column user_center.tenant.id is '租户主键';
comment on column user_center.tenant.user_id is '用户ID';
comment on column user_center.tenant.tenant_type is '租户类型 WECHAT-微信 ALIPAY-支付宝';
comment on column user_center.tenant.tenant_code is '租户编码';
comment on column user_center.tenant.tenant_name is '租户名称';
comment on column user_center.tenant.tenant_metadata is '租户元数据';
comment on column user_center.tenant.created_at is '创建时间';
comment on column user_center.tenant.updated_at is '更新时间';
comment on column user_center.tenant.enabled is '逻辑删除标识符 Y-正常 N-逻辑删除';
