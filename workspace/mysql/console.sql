/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 00:15:42 UTC+08:00
 *****************************************************/

create database if not exists dev character set utf8mb4 collate utf8mb4_general_ci;
use dev;
drop table if exists t_user;
create table if not exists t_user
(
    id         bigint auto_increment primary key comment 'id',
    username   varchar(15)  not null comment '用户名',
    password   varchar(255) not null comment '密码',
    phone      varchar(11)  not null comment '手机号',
    info       text comment '用户信息',
    status     enum ('Y', 'N') default 'Y' comment '状态',
    balance    decimal(10, 2)  default 0.00 comment '余额',
    created_at timestamp       default current_timestamp comment '创建时间',
    updated_at timestamp       default current_timestamp on
        update current_timestamp comment '更新时间',
    enabled    enum ('Y', 'N') default 'Y' comment '是否启用'
) comment '用户表';
