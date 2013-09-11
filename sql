
drop schema if exists jsf;
create schema jsf;
use jsf;
create table t_user (
	c_id char(40) not null primary key,
	c_name char(40) not null default '',
	c_pwd char(40) not null default '',
	c_birth date
);
insert into t_user values('test_login', 'test_user1', 'test_password1', '2012-12-20');
insert into t_user values('test_list', 'test_user2', 'test_password2', '2008-8-8');