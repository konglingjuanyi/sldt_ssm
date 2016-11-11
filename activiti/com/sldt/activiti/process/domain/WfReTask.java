package com.sldt.activiti.process.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 任务节点配置信息
 * @author 刘猛
 */
public class WfReTask extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 4521963980787503908L;
	
	/**
	 * 任务节点配置ID
	 */
	private String taskId;
	/**
	 * 流程配置id，t_wf_re_process的id
	 */
	private String procId;
	
	/**
	 * 关联act_re_procdef表的主键
	 */
	private String actProcDefId;
	
	/**
	 * 任务id，报文task节点对应的id
	 */
	private String actId;
	/**
	 * 任务名称，报文task节点对应的name
	 */
	private String actName;
	/**
	 * 节点任务类型，task节点的类型，usertask，startevent…
	 */
	private String actType;
	/**
	 * 操作类型，1：竞签；2：会签
	 */
	private String operateType;
	/**
	 * 排序
	 */
	private int orderIndex;
	/**
	 * 任务审批用户信息
	 */
	private Set<WfReTaskOper> taskOpers = new HashSet<WfReTaskOper>();
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
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
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public int getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
	public Set<WfReTaskOper> getTaskOpers() {
		return taskOpers;
	}
	public void setTaskOpers(Set<WfReTaskOper> taskOpers) {
		this.taskOpers = taskOpers;
	}
	public String getActProcDefId() {
		return actProcDefId;
	}
	public void setActProcDefId(String actProcDefId) {
		this.actProcDefId = actProcDefId;
	}
}
