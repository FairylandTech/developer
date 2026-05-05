/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-26 16:16:46 UTC+08:00
 *****************************************************/

create role dev with login password '强密码' createdb nosuperuser;
create database dev with owner = dev encoding = 'UTF8' lc_collate = 'en_US.UTF-8' lc_ctype = 'en_US.UTF-8';
grant all privileges on database dev to dev;

create extension vector;
SELECT * FROM pg_extension where extname = 'vector';

create schema if not exists t_dev authorization dev;

set search_path to t_dev;
create table if not exists t_dev.t_user(
    id serial primary key,
    username varchar(15) not null,
    password varchar(255) not null,
    phone varchar(11) not null,
    info text,
    status ENUM('Y', 'N') default 'Y',
    balance decimal(10, 2) default 0.00,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    enabled ENUM('Y', 'N') default 'Y'
)
