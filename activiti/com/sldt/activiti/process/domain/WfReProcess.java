package com.sldt.activiti.process.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * 流程配置信息
 * @author 刘猛
 */
public class WfReProcess extends BaseBean implements Serializable {
	
	private static final long serialVersionUID = -5501336616473266148L;
	
	/**
	 * 流程配置id
	 */
	private String procId;
	/**
	 * 流程配置名称
	 */
	private String procName;
	
	/**
	 * 关联act_re_procdef表的主键
	 */
	private String actProcDefId;
	/**
	 * 关联act_re_procdef表的version_
	 */
	private String version;
	/**
	 * 流程类型,标志流程的分类
	 */
	private String procType;
	
	/**
	 * 定义的key，流程定义文件中的id
	 */
	private String actProcDefKey;
	/**
	 * 流程名称，流程定义文件中的name
	 */
	private String actProcDefName;
	/**
	 * 部署的id，部署表act_re_deployment的id
	 */
	private String deploymentId;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 排序
	 */
	private int orderIndex;
	/**
	 * 状态：1-有效，0-无效
	 */
	private String state;
	
	/**
	 * 流程下的任务集合
	 */
	private Set<WfReTask> tasks = new HashSet<WfReTask>();

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}



	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProcType() {
		return procType;
	}

	public void setProcType(String procType) {
		this.procType = procType;
	}
	
	public String getActProcDefId() {
		return actProcDefId;
	}

	public void setActProcDefId(String actProcDefId) {
		this.actProcDefId = actProcDefId;
	}

	public String getActProcDefKey() {
		return actProcDefKey;
	}

	public void setActProcDefKey(String actProcDefKey) {
		this.actProcDefKey = actProcDefKey;
	}

	public String getActProcDefName() {
		return actProcDefName;
	}

	public void setActProcDefName(String actProcDefName) {
		this.actProcDefName = actProcDefName;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<WfReTask> getTasks() {
		return tasks;
	}

	public void setTasks(Set<WfReTask> tasks) {
		this.tasks = tasks;
	}
}
