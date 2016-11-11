package com.sldt.activiti.process.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.sldt.activiti.process.domain.WfRuTask;
import com.sldt.activiti.process.domain.WfRuTaskOper;
public interface IProcessService {
	
	/**
	 * 流程跟踪（流程未结束）
	 * @param processInstanceId ：流程实例id
	 * @param businessKey：流程业务数据id
	 * @param definitionKey：流程定义id
	 * @return 流程跟踪图的流数据
	 */
	public InputStream trace(String processInstanceId, String businessKey, String definitionKey);
	
	/**
	 * 历史流程跟踪（流程结束）
	 * @param processInstanceId ：流程实例id
	 * @param businessKey：流程业务数据id
	 * @param definitionKey：流程定义id
	 * @return 流程跟踪图的流数据
	 */
	public InputStream hisTrace(String processInstanceId, String businessKey, String definitionKey);
	
	/**
	 * 查询流程实例的审批意见
	 * @param processInstanceId：流程实例ID
	 * @return 审批意见列表
	 */
	public abstract List<Map<String, String>> getComments(String processInstanceId);
	
	/**
	 * 启动流程实例
	 * @param processCfgId	流程配置ID
	 * @param bizType		业务类型
	 * @param bizId			业务主键
	 * @param startUserId	启动流程用户
	 * @return	流程实例ID
	 */
	public abstract String startProcessInstance(String processCfgId, String bizType, String bizId, String startUserId);
	
	/**
	 * 结束人员工作任务
	 * @param task	待办任务记录
	 * @param taskOper	任务操作记录
	 * @param cureentUserId	当前用户ID
	 * @return	操作结果：true-成功；false-失败
	 */
	public abstract boolean completeUserTask(WfRuTask task, WfRuTaskOper taskOper, String currentUserId);
}
