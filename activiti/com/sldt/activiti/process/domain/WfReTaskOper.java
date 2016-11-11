package com.sldt.activiti.process.domain;

import java.io.Serializable;

/**
 * 任务节点操作配置信息
 * @author 刘猛
 */
public class WfReTaskOper extends BaseBean implements Serializable{
	private static final long serialVersionUID = -2562525483977005116L;
	/**
	 * 任务节点操作id
	 */
	private String taskOperId;
	/**
	 * 任务配置id，关联t_wf_re_task表task_id
	 */
	private String taskId;
	/**
	 * 操作人类型：1-用户；2-组别
	 */
	private String operType;
	
	/**
	 * 用户ID	
	 */
	private String userId;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 组别ID
	 */
	private String groupId;
	
	/**
	 * 组别名称
	 */
	private String groupName;

	public String getTaskOperId() {
		return taskOperId;
	}

	public void setTaskOperId(String taskOperId) {
		this.taskOperId = taskOperId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
