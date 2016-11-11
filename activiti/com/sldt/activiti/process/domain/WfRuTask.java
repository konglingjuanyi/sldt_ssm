package com.sldt.activiti.process.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 运行时任务节点信息
 * @author 刘猛
 */
public class WfRuTask extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 4521963980787503908L;
	
	/**
	 * 任务实例id
	 */
	private String	taskInstId;
	
	/**
	 * activiti的任务实例id
	 * act_ru_task的id_
	 */
	private String	actTaskInstId;
	
	/**
	 * act_ru_execution的proc_inst_id_或者act_hi_procinst的proc_inst_id_
	 * activiti中的流程实例的ID
	 * 
	 */
	private String actProcInstId;
	
	/**
	 * activiti中流程定义的ID
	 */
	private String actProcDefId;
	
	/**
	 * 流程实例id
	 * t_wf_ru_procinst的主键
	 */
	private String	procInstId;
	/**
	 * 流程配置ID
	 */
	private String	procId;
	/**
	 * 任务id
	 * t_wf_re_task的主键
	 */
	private String	taskId;
	/**
	 * 任务节点id	
	 * 报文task节点对应的id
	 */
	private String	actId;
	/**
	 * 任务名称
	 * 报文task节点对应的name
	 */
	private String	actName;
	/**
	 * 任务节点类型
	 * task节点的类型，usertask，startevent…
	 */
	private String	actType;
	/**
	 * 实际签收人
	 * 签收人（默认为空，只有在委托时才有值）
	 */
	private String	owner;
	/**
	 * 签收人或被委托
	 */
	private String	assignee;
	/**
	 * 委托类型
	 * 委托类型：pending，resolved
	 */
	private String	delegationstate;
	/**
	 * 操作类型：1—竞签；2—会签
	 */
	private String	operateType;
	/**
	 * 状态：0—处理中；1—已处理
	 */
	private String	state;
	/**
	 * 描述	
	 */
	private String	description;
	
	/**
	 * 任务审批用户信息
	 */
	private Set<WfRuTaskOper> taskOpers = new HashSet<WfRuTaskOper>();

	public String getTaskInstId() {
		return taskInstId;
	}

	public void setTaskInstId(String taskInstId) {
		this.taskInstId = taskInstId;
	}

	public String getActTaskInstId() {
		return actTaskInstId;
	}

	public void setActTaskInstId(String actTaskInstId) {
		this.actTaskInstId = actTaskInstId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getDelegationstate() {
		return delegationstate;
	}

	public void setDelegationstate(String delegationstate) {
		this.delegationstate = delegationstate;
	}

	public String getActProcInstId() {
		return actProcInstId;
	}

	public void setActProcInstId(String actProcInstId) {
		this.actProcInstId = actProcInstId;
	}

	public String getActProcDefId() {
		return actProcDefId;
	}

	public void setActProcDefId(String actProcDefId) {
		this.actProcDefId = actProcDefId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<WfRuTaskOper> getTaskOpers() {
		return taskOpers;
	}

	public void setTaskOpers(Set<WfRuTaskOper> taskOpers) {
		this.taskOpers = taskOpers;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}
	
}
