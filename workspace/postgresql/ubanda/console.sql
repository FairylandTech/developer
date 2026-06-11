create role ubanda with login password 'V7!qLz#9pW@3mX' createdb nosuperuser;
create database ubanda with owner = ubanda encoding = 'UTF8' lc_collate = 'en_US.utf8' lc_ctype = 'en_US.utf8';
grant all privileges on database ubanda to ubanda;
comment on database ubanda is '友伴搭数据库';
