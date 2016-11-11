package com.sldt.activiti.process.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * 运行时流程实例信息
 * @author 刘猛
 */
public class WfRuProcinst extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = -550133661645432548L;
	
	/**
	 * 流程实例id	
	 */
	private String 	procInstId;
	/**
	 * activiti的流程实例id
	 * act_ru_execution的proc_inst_id_
	 * 或者act_hi_procinst的proc_inst_id_
	 */
	private String 	actProcInstId;
	
	/**
	 * 关联act_re_procdef表的主键
	 */
	private String actProcDefId;
	
	/**
	 * 流程定义id	
	 * t_wf_re_process的流程id
	 */
	private String 	procId;
	/**
	 * 业务类型
	 * 业务类型用于区分业务
	 */
	private String 	businessType;
	/**
	 * 业务id
	 * 业务的主键查找对应的业务信息
	 */
	private String 	businessId;
	/**
	 * 起始时间	
	 */
	private String startTime;
	/**
	 * 启动流程用户	
	 */
	private String 	startUserId;
	/**
	 * 启动用户姓名
	 */
	private String startUserName;
	/**
	 * 结束时间	
	 */
	private String 	endTime;
	/**
	 * 结束用户	
	 */
	private String 	endUserId;
	/**
	 * 结束用户姓名
	 */
	private String endUserName;
	/**
	 * 挂起状态：1-激活；2-挂起
	 */
	private String 	suspensionState;
	/**
	 * 备注	
	 */
	private String 	remark;
	/**
	 * 状态：0—审批中；1—已通过；2—已否决；3—已撤单	
	 */
	private String 	state;

	/**
	 * 流程下的任务集合
	 */
	private Set<WfRuTask> tasks = new HashSet<WfRuTask>();
	
	/**
	 * 流程下审批过的任务集合
	 */
	private Set<WfRuTaskOper> tasksOpers = new HashSet<WfRuTaskOper>();
	
	/**
	 * xks 新增，用于启动实例后获取第一个任务实例
	 */
	private WfRuTask wfRuTask;
	
	/**
	 * 当前任务节点
	 */
	private String curActName;

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public WfRuTask getWfRuTask() {
		return wfRuTask;
	}

	public void setWfRuTask(WfRuTask wfRuTask) {
		this.wfRuTask = wfRuTask;
	}

	public String getActProcInstId() {
		return actProcInstId;
	}

	public void setActProcInstId(String actProcInstId) {
		this.actProcInstId = actProcInstId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}
	
	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getEndUserId() {
		return endUserId;
	}

	public void setEndUserId(String endUserId) {
		this.endUserId = endUserId;
	}

	public String getEndUserName() {
		return endUserName;
	}

	public void setEndUserName(String endUserName) {
		this.endUserName = endUserName;
	}

	public String getSuspensionState() {
		return suspensionState;
	}

	public void setSuspensionState(String suspensionState) {
		this.suspensionState = suspensionState;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<WfRuTask> getTasks() {
		return tasks;
	}

	public void setTasks(Set<WfRuTask> tasks) {
		this.tasks = tasks;
	}

	public String getCurActName() {
		return curActName;
	}

	public void setCurActName(String curActName) {
		this.curActName = curActName;
	}

	public String getActProcDefId() {
		return actProcDefId;
	}

	public void setActProcDefId(String actProcDefId) {
		this.actProcDefId = actProcDefId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Set<WfRuTaskOper> getTasksOpers() {
		return tasksOpers;
	}

	public void setTasksOpers(Set<WfRuTaskOper> tasksOpers) {
		this.tasksOpers = tasksOpers;
	}
	
	
	
}
