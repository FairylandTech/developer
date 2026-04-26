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
