/*
Navicat MySQL Data Transfer

Source Server         : sunlinework
Source Server Version : 50621
Source Host           : 127.0.0.1:3306
Source Database       : sldt_activiti

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-08-05 15:07:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_wf_re_process
-- ----------------------------
DROP TABLE IF EXISTS `t_wf_re_process`;
CREATE TABLE `t_wf_re_process` (
  `proc_id` varchar(64) NOT NULL COMMENT '主键',
  `proc_name` varchar(128) DEFAULT NULL COMMENT '流程配置名称',
  `act_proc_def_id` varchar(64) DEFAULT NULL COMMENT '关联activiti的流程定义表',
  `version` varchar(32) DEFAULT NULL COMMENT '版本',
  `proc_type` varchar(10) DEFAULT NULL COMMENT '标志流程的分类，预留方便管理',
  `act_proc_def_key` varchar(255) DEFAULT NULL COMMENT '流程定义文件中的id',
  `act_proc_def_name` varchar(255) DEFAULT NULL COMMENT '流程定义文件中的name',
  `deployment_id` varchar(64) DEFAULT NULL COMMENT '部署表act_re_deployment的id',
  `description` text COMMENT '描述',
  `order_index` int(11) DEFAULT NULL COMMENT '默认为10',
  `state` varchar(2) DEFAULT NULL COMMENT '状态：1-有效，0-无效',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `date_created` varchar(30) DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '更新人',
  `date_updated` varchar(30) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`proc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程配置信息';

-- ----------------------------
-- Table structure for t_wf_re_task
-- ----------------------------
DROP TABLE IF EXISTS `t_wf_re_task`;
CREATE TABLE `t_wf_re_task` (
  `task_id` varchar(64) NOT NULL COMMENT '主键',
  `proc_id` varchar(64) DEFAULT NULL COMMENT 't_wf_re_process的id',
  `act_proc_def_id` varchar(64) DEFAULT NULL COMMENT '关联activiti的流程定义表',
  `act_id` varchar(255) DEFAULT NULL COMMENT '报文task节点对应的id',
  `act_name` varchar(255) DEFAULT NULL COMMENT '报文task节点对应的name',
  `act_type` varchar(255) DEFAULT NULL COMMENT 'task节点的类型，usertask，startevent…',
  `operate_type` varchar(2) DEFAULT NULL COMMENT '操作类型，1：竞签；2：会签',
  `order_index` int(11) DEFAULT NULL COMMENT '默认为10, 描述节点展示的顺序',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `date_created` varchar(30) DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  `date_updated` varchar(30) DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务节点配置信息';

-- ----------------------------
-- Table structure for t_wf_re_task_oper
-- ----------------------------
DROP TABLE IF EXISTS `t_wf_re_task_oper`;
CREATE TABLE `t_wf_re_task_oper` (
  `task_oper_id` varchar(64) NOT NULL COMMENT '任务节点操作id',
  `task_id` varchar(64) DEFAULT NULL COMMENT '关联t_wf_re_task表task_id',
  `oper_type` varchar(2) DEFAULT NULL COMMENT '操作人类型：1-用户；2-组别',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(128) DEFAULT NULL COMMENT '用户姓名',
  `group_id` varchar(64) DEFAULT NULL COMMENT '组别ID',
  `group_name` varchar(128) DEFAULT NULL COMMENT '组别名称',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `date_created` varchar(30) DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  `date_updated` varchar(30) DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`task_oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务节点操作配置信息';

-- ----------------------------
-- Table structure for t_wf_ru_procinst
-- ----------------------------
DROP TABLE IF EXISTS `t_wf_ru_procinst`;
CREATE TABLE `t_wf_ru_procinst` (
  `proc_inst_id` varchar(64) NOT NULL COMMENT '流程实例id',
  `act_proc_inst_id` varchar(64) DEFAULT NULL COMMENT 'act_ru_execution的proc_inst_id_或者act_hi_procinst的proc_inst_id_',
  `act_proc_def_id` varchar(64) DEFAULT NULL COMMENT '关联activiti的流程定义表',
  `proc_id` varchar(64) DEFAULT NULL COMMENT 't_wf_re_process的id',
  `business_type` varchar(32) DEFAULT NULL COMMENT '业务类型用于区分业务',
  `business_id` varchar(64) DEFAULT NULL COMMENT '业务的主键查找对应的业务信息',
  `start_time` varchar(30) DEFAULT NULL COMMENT '起始时间',
  `start_user_id` varchar(64) DEFAULT NULL COMMENT '启动流程用户',
  `start_user_name` varchar(128) DEFAULT NULL COMMENT '启动用户姓名',
  `end_time` varchar(30) DEFAULT NULL COMMENT '结束时间',
  `end_user_id` varchar(64) DEFAULT NULL COMMENT '结束流程用户',
  `end_user_name` varchar(128) DEFAULT NULL COMMENT '结束用户姓名',
  `suspension_state` varchar (2) DEFAULT NULL COMMENT '挂起状态：1—激活；2—挂起',
  `state` varchar(2) DEFAULT NULL COMMENT '状态：0—审批中；1—已通过；2—已否决；3—已撤单',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `date_created` varchar(30) DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  `date_updated` varchar(30) DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`proc_inst_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行时流程实例信息';

-- ----------------------------
-- Table structure for t_wf_ru_task
-- ----------------------------
DROP TABLE IF EXISTS `t_wf_ru_task`;
CREATE TABLE `t_wf_ru_task` (
  `task_inst_id` varchar(64) NOT NULL COMMENT '任务实例id',
  `act_task_inst_id` varchar(64) DEFAULT NULL COMMENT 'act_ru_task的id_',
  `act_proc_inst_id` varchar(64) DEFAULT NULL COMMENT 'act_ru_execution的proc_inst_id_或者act_hi_procinst的proc_inst_id_',
  `act_proc_def_id` varchar(64) DEFAULT NULL COMMENT '关联activiti的流程定义表',
  `proc_inst_id` varchar(64) DEFAULT NULL COMMENT 't_wf_ru_procinst的主键',
  `proc_id` varchar(64) DEFAULT NULL COMMENT 't_wf_re_process的id',
  `task_id` varchar(64) DEFAULT NULL COMMENT 't_wf_re_task的主键',
  `act_id` varchar(255) DEFAULT NULL COMMENT '报文task节点对应的id',
  `act_name` varchar(255) DEFAULT NULL COMMENT '报文task节点对应的name',
  `act_type` varchar(255) DEFAULT NULL COMMENT 'task节点的类型，usertask，startevent…',
  `owner` varchar(255) DEFAULT NULL COMMENT '签收人（默认为空，只有在委托时才有值）',
  `assignee` varchar(255) DEFAULT NULL COMMENT '签收人或被委托人',
  `delegationstate` varchar(64) DEFAULT NULL COMMENT '委托类型：pending，resolved，如无委托，则为空', 
  `operate_type` varchar(8) DEFAULT NULL COMMENT '操作类型：1—竞签；2—会签',
  `state` varchar(2) DEFAULT NULL COMMENT '状态：0—处理中；1—已处理',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `date_created` varchar(30) DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  `date_updated` varchar(30) DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`task_inst_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行时任务节点信息';

-- ----------------------------
-- Table structure for t_wf_ru_task_oper
-- ----------------------------
DROP TABLE IF EXISTS `t_wf_ru_task_oper`;
CREATE TABLE `t_wf_ru_task_oper` (
  `oper_rec_id` varchar(64) NOT NULL COMMENT '操作记录id',
  `proc_inst_id` varchar(64) DEFAULT NULL COMMENT 't_wf_ru_procinst的主键',
  `act_proc_inst_id` varchar(64) DEFAULT NULL COMMENT 'act_ru_execution的proc_inst_id_或者act_hi_procinst的proc_inst_id_',
  `task_inst_id` varchar(64) DEFAULT NULL COMMENT 't_wf_ru_task的id,此主键用处不大',
  `task_id` varchar(64) DEFAULT NULL COMMENT 't_wf_re_task的主键',
  `act_proc_def_id` varchar(64) DEFAULT NULL COMMENT '关联activiti的流程定义表',
  `proc_id` varchar(64) DEFAULT NULL COMMENT 't_wf_re_process的id',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(128) DEFAULT NULL COMMENT '用户姓名',
  `oper_time` varchar(30) DEFAULT NULL COMMENT '处理时间',
  `confirm_type` varchar(2) DEFAULT NULL COMMENT '处理类型：0—未处理；1—通过；2—拒绝；3—驳回',
  `opinion` varchar(1000) DEFAULT NULL COMMENT '处理意见',
  `change_task_state` varchar(8) DEFAULT NULL COMMENT '0:未更改;1:更改',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `date_created` varchar(30) DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  `date_updated` varchar(30) DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`oper_rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行时任务节点操作记录';
