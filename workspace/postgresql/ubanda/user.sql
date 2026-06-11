create schema if not exists user_center authorization ubanda;
comment on schema user_center is '用户管理中心';

-- ==================================================================
-- Table: 租户表
-- ==================================================================
create table if not exists user_center.tenant
(
    id              serial primary key,
    tenant_code     varchar(64)  not null,
    tenant_name     varchar(128) not null,
    tenant_metadata jsonb,
    created_at      timestamptz  not null default now(),
    updated_at      timestamptz  not null default now(),
    deleted_at      timestamptz,
    enabled         character(1)          default 'Y',
    constraint tenant_code_unique unique (tenant_code)
);
comment on table user_center.tenant is '租户表';
comment on column user_center.tenant.id is '租户主键';
comment on column user_center.tenant.tenant_code is '租户编码';
comment on column user_center.tenant.tenant_name is '租户名称';
comment on column user_center.tenant.tenant_metadata is '租户元数据';
comment on column user_center.tenant.created_at is '记录创建时间';
comment on column user_center.tenant.updated_at is '记录更新时间';
comment on column user_center.tenant.deleted_at is '记录删除时间，软删除时使用';
comment on column user_center.tenant.enabled is '数据是否启用，Y表示启用，N表示禁用（软删除）';
