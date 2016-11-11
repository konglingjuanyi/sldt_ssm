package com.sldt.activiti.process.domain;

import java.io.Serializable;

/**
 * 运行时任务节点操作记录
 * @author 刘猛
 */
public class WfRuTaskOper extends BaseBean implements Serializable{
	private static final long serialVersionUID = -2562525483977005116L;
	/**
	 * 任务操作记录ID
	 */
	private String operRecId;
	
	/**
	 * 流程实例id
	 * t_wf_ru_procinst的主键
	 */
	private String	procInstId;
	
	/**
	 * act_ru_execution的proc_inst_id_或者act_hi_procinst的proc_inst_id_
	 * activiti中的流程实例的ID
	 * 
	 */
	private String actProcInstId;
	
	/**
	 * 任务实例id	
	 */
	private String taskInstId;
	
	/**
	 * 任务id
	 * t_wf_re_task的主键
	 */
	private String	taskId;
	
	/**
	 * activiti中流程定义的ID
	 */
	private String actProcDefId;
	
	/**
	 * 流程配置ID
	 */
	private String	procId;
	
	/**
	 * 用户ID	
	 */
	private String userId;
	/**
	 * 用户姓名	
	 */
	private String userName;
	/**
	 * 处理时间	
	 */
	private String operTime;
	/**
	 * 处理类型	
	 */
	private String confirmType;
	/**
	 * 处理意见	
	 */
	private String opinion;
	/**
	 * 是否更改流程节点	
	 */
	private String changeTaskState;
	
	public String getOperRecId() {
		return operRecId;
	}
	public void setOperRecId(String operRecId) {
		this.operRecId = operRecId;
	}
	public String getTaskInstId() {
		return taskInstId;
	}
	public void setTaskInstId(String taskInstId) {
		this.taskInstId = taskInstId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public String getConfirmType() {
		return confirmType;
	}
	public void setConfirmType(String confirmType) {
		this.confirmType = confirmType;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getChangeTaskState() {
		return changeTaskState;
	}
	public void setChangeTaskState(String changeTaskState) {
		this.changeTaskState = changeTaskState;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getActProcInstId() {
		return actProcInstId;
	}
	public void setActProcInstId(String actProcInstId) {
		this.actProcInstId = actProcInstId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getActProcDefId() {
		return actProcDefId;
	}
	public void setActProcDefId(String actProcDefId) {
		this.actProcDefId = actProcDefId;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	
}
