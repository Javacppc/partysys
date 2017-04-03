/**
*记录数据库变更的文件
*Author: 朱可凡
*Date: 2017_4_2
*Desc: 在DELL笔记本电脑上创建系统管理模块的表用于测试系统
* 包括：支部表，角色表，权限表，信息表
*/
drop table if exists branch;

drop table if exists info;

drop table if exists partymember;

drop table if exists privilege;

drop table if exists role;

drop table if exists rolepartymember;

drop table if exists roleprivilege;

/*==============================================================*/
/* Table: branch                                                */
/*==============================================================*/
create table branch
(
   branch_id            varchar(32) not null,
   brach_name           varchar(40) not null,
   branch_admin         varchar(100),
   branch_number        int,
   primary key (branch_id)
);

/*==============================================================*/
/* Table: info                                                  */
/*==============================================================*/
create table info
(
   source               varchar(1024),
   content              text,
   remark               varchar(1024),
   creator              varchar(1024),
   createTime           timestamp,
   info_id              varchar(32) not null,
   title                varchar(1024) not null,
   primary key (info_id)
);

/*==============================================================*/
/* Table: partymember                                           */
/*==============================================================*/
create table partymember
(
   id                   varchar(20) not null,
   branch_id            varchar(32),
   name                 varchar(30),
   gender               bool,
   number               varchar(30),
   nation               varchar(30),
   identity             varchar(40),
   province             varchar(30),
   telenumber           varchar(20),
   grade                varchar(20),
   class                varchar(20),
   cultivate            varchar(20),
   classification       varchar(30),
   branchId             varchar(20),
   textnumber           varchar(30),
   jionTime             date,
   roleId               varchar(20),
   remake               varchar(50),
   primary key (id)
);

/*==============================================================*/
/* Table: privilege                                             */
/*==============================================================*/
create table privilege
(
   privilege_id         varchar(32) not null,
   privilege_name       varchar(40) not null,
   state                varchar(10),
   primary key (privilege_id)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   role_id              varchar(32) not null,
   role_name            varchar(40) not null,
   state                varchar(10),
   primary key (role_id)
);

/*==============================================================*/
/* Table: rolepartymember                                       */
/*==============================================================*/
create table rolepartymember
(
   id                   varchar(20) not null,
   role_id              varchar(32) not null,
   primary key (id, role_id)
);

/*==============================================================*/
/* Table: roleprivilege                                         */
/*==============================================================*/
create table roleprivilege
(
   role_id              varchar(32) not null,
   privilege_id         varchar(32) not null,
   primary key (role_id, privilege_id)
);

alter table partymember add constraint FK_partybrach foreign key (branch_id)
      references branch (branch_id) on delete restrict on update restrict;

alter table rolepartymember add constraint FK_rolepartymember foreign key (id)
      references partymember (id) on delete restrict on update restrict;

alter table rolepartymember add constraint FK_rolepartymember2 foreign key (role_id)
      references role (role_id) on delete restrict on update restrict;

alter table roleprivilege add constraint FK_roleprivilege foreign key (role_id)
      references role (role_id) on delete restrict on update restrict;

alter table roleprivilege add constraint FK_roleprivilege2 foreign key (privilege_id)
      references privilege (privilege_id) on delete restrict on update restrict;
