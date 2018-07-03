-- 系统用户表
create table if not exists t_sys_user (
  id bigint(20) not null auto_increment,
  username varchar(50) not null  comment '账户',
  password varchar(100) not null comment '密码',
  name varchar(32) not null default '' comment '姓名',
  email varchar(100) not null default '' comment '邮箱',
  sex int(11) not null default 1 comment '性别(0,女;1,男)',
  mobile varchar(20) not null default '' comment '手机号',
  create_time timestamp not null default current_timestamp comment '创建时间',
  admin_flag int(11) not null default 0 comment '是否管理员(0,否;1,是)',
  primary key (id),
  unique key uniq_username (username)
) engine=innodb default charset=utf8 comment='系统用户表';

-- 角色表
create table if not exists t_sys_role (
  id bigint(20) not null auto_increment,
  role_name varchar(100) not null default '' comment '角色名称',
  remark varchar(255) not null default '' comment '备注',
  is_admin int(11) not null default0 comment '是否为管理员(0,否;1,是)' ,
  create_time timestamp not null default current_timestamp comment '创建时间',
  primary key (id)
) engine=innodb default charset=utf8 comment='角色表';

-- 用户角色关系表
create table if not exists t_sys_user_role (
  id bigint(20) not null auto_increment,
  user_id bigint(20) not null  comment '用户id',
  role_id bigint(20) not null  comment '角色id',
  create_time timestamp not null default current_timestamp comment '创建时间',
  primary key (id),
  key idx_user_id (user_id),
  key idx_role_id (role_id)
) engine=innodb default charset=utf8 comment='用户角色表';

-- 菜单表
create table if not exists t_sys_menu (
  id bigint(20) not null auto_increment,
  parent_id bigint(20) not null default 0 comment '父菜单id，一级菜单为0',
  name varchar(50) not null comment '菜单名称',
  path varchar(200) not null default '' comment '菜单url',
  perms varchar(100) not null default '' comment '授权如:sys:user:view',
  type int(11) not null  comment '类型 (1,目录;2,菜单;3,按钮)',
  iconcls varchar(50) not null default '' comment '菜单图标',
  order_num int(11) not null default 0 comment '排序',
  create_time timestamp not null default current_timestamp comment '创建时间',
  primary key (id)
) engine=innodb default charset=utf8 comment='菜单表';

-- 角色菜单表
create table if not exists t_sys_role_menu (
  id bigint(20) not null auto_increment,
  role_id bigint(20) not null  comment '角色id',
  menu_id bigint(20) not null  comment '菜单id',
  create_time timestamp not null default current_timestamp comment '创建时间',
  primary key (id),
  key idx_role_id (role_id),
  key idx_menu_id (menu_id)
) engine=innodb default charset=utf8 comment='角色菜单表';





























