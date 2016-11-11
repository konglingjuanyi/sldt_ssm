drop index SM_AT_A_D_I on T_SM_AUDIT_TRAIL;

drop index SM_AT_U_D_I on T_SM_AUDIT_TRAIL;

drop index SM_AT_C_D_I on T_SM_AUDIT_TRAIL;

drop index SM_AT_D_I on T_SM_AUDIT_TRAIL;

drop table if exists T_SM_AUDIT_TRAIL;

/*==============================================================*/
/* Table: T_SM_AUDIT_TRAIL                                      */
/*==============================================================*/
create table T_SM_AUDIT_TRAIL
(
   AUD_USER             varchar(100) not null,
   AUD_RESOURCE         varchar(100) not null,
   AUD_ACTION           varchar(100) not null,
   APPLIC_CD            varchar(5) not null,
   AUD_DATE             timestamp not null,
   AUD_CLIENT_IP        varchar(15) not null,
   AUD_SERVER_IP        varchar(15) not null,
   ARC_STS              int default 0 comment '0：表示未归档
            9：表示正在归档
            1：表示已归档',
   primary key (AUD_USER, AUD_CLIENT_IP, AUD_SERVER_IP, AUD_RESOURCE, AUD_ACTION, APPLIC_CD, AUD_DATE)
);

/*==============================================================*/
/* Index: SM_AT_D_I                                             */
/*==============================================================*/
create index SM_AT_D_I on T_SM_AUDIT_TRAIL
(
   AUD_DATE
);

/*==============================================================*/
/* Index: SM_AT_C_D_I                                           */
/*==============================================================*/
create index SM_AT_C_D_I on T_SM_AUDIT_TRAIL
(
   AUD_CLIENT_IP,
   AUD_DATE
);

/*==============================================================*/
/* Index: SM_AT_U_D_I                                           */
/*==============================================================*/
create index SM_AT_U_D_I on T_SM_AUDIT_TRAIL
(
   AUD_USER,
   AUD_DATE
);

/*==============================================================*/
/* Index: SM_AT_A_D_I                                           */
/*==============================================================*/
create index SM_AT_A_D_I on T_SM_AUDIT_TRAIL
(
   AUD_ACTION,
   AUD_DATE
);



drop index SM_AT_A_D_I_H on T_SM_AUDIT_TRAIL_HIS;

drop index SM_AT_U_D_I_H on T_SM_AUDIT_TRAIL_HIS;

drop index SM_AT_C_D_I_H on T_SM_AUDIT_TRAIL_HIS;

drop index SM_AT_D_I_H on T_SM_AUDIT_TRAIL_HIS;

drop table if exists T_SM_AUDIT_TRAIL_HIS;

/*==============================================================*/
/* Table: T_SM_AUDIT_TRAIL_HIS                                  */
/*==============================================================*/
create table T_SM_AUDIT_TRAIL_HIS
(
   AUD_USER             varchar(100) not null,
   AUD_RESOURCE         varchar(100) not null,
   AUD_ACTION           varchar(100) not null,
   APPLIC_CD            varchar(5) not null,
   AUD_DATE             timestamp not null,
   AUD_CLIENT_IP        varchar(15) not null,
   AUD_SERVER_IP        varchar(15) not null,
   ARC_STS              int comment '0：表示未归档
            9：表示正在归档
            1：表示已归档',
   ARC_DATE              timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   primary key (AUD_USER, AUD_CLIENT_IP, AUD_SERVER_IP, AUD_RESOURCE, AUD_ACTION, APPLIC_CD, AUD_DATE)
);

/*==============================================================*/
/* Index: SM_AT_D_I_H                                           */
/*==============================================================*/
create index SM_AT_D_I_H on T_SM_AUDIT_TRAIL_HIS
(
   AUD_DATE
);

/*==============================================================*/
/* Index: SM_AT_C_D_I_H                                         */
/*==============================================================*/
create index SM_AT_C_D_I_H on T_SM_AUDIT_TRAIL_HIS
(
   AUD_CLIENT_IP,
   AUD_DATE
);

/*==============================================================*/
/* Index: SM_AT_U_D_I_H                                         */
/*==============================================================*/
create index SM_AT_U_D_I_H on T_SM_AUDIT_TRAIL_HIS
(
   AUD_USER,
   AUD_DATE
);

/*==============================================================*/
/* Index: SM_AT_A_D_I_H                                         */
/*==============================================================*/
create index SM_AT_A_D_I_H on T_SM_AUDIT_TRAIL_HIS
(
   AUD_ACTION,
   AUD_DATE
);

