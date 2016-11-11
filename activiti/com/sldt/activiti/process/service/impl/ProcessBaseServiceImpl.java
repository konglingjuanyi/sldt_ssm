package com.sldt.activiti.process.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import com.sldt.activiti.process.common.DateUtil;
import com.sldt.activiti.process.common.StringUtil;
import com.sldt.activiti.process.common.SystemConstant;
import com.sldt.activiti.process.common.UserUtil;
import com.sldt.activiti.process.dao.IProcessBaseDao;
import com.sldt.activiti.process.domain.WfReProcess;
import com.sldt.activiti.process.domain.WfReTask;
import com.sldt.activiti.process.domain.WfRuProcinst;
import com.sldt.activiti.process.domain.WfRuTask;
import com.sldt.activiti.process.domain.WfRuTaskOper;
import com.sldt.activiti.process.exception.ProcessServiceException;
import com.sldt.activiti.process.service.IProcessBaseService;
import com.sldt.framework.common.PageResults;
import com.sldt.mds.dmc.sm.uitl.DateUtils;

@Service(value = "processBaseService")
public class ProcessBaseServiceImpl implements IProcessBaseService {

	@Resource(name = "processBaseDao")
	private IProcessBaseDao processBaseDao;

	@Resource(name = "taskService")
	private TaskService taskService;

	@Resource(name = "runtimeService")
	private RuntimeService runtimeService;

	@Resource(name = "historyService")
	private HistoryService historyService;

	@Override
	public PageResults findPage(DetachedCriteria detachedCriteria, int start, int pageSize) {
		return processBaseDao.findPage(detachedCriteria, start, pageSize);
	}

	@Override
	public <T> void add(T obj) {
		processBaseDao.add(obj);
	}

	@Override
	public <T> void update(T obj) {
		processBaseDao.update(obj);
	}

	@Override
	public <T> void save(T obj) {
		processBaseDao.save(obj);
	}

	@Override
	public <T> void delete(T obj) {
		processBaseDao.delete(obj);
	}
	
	public void deleteById(Class<WfReProcess> clz,Serializable id){
		processBaseDao.deleteById(clz,id);
	};

	@Override
	public <T> T get(Class<T> clz, Serializable id) {
		return processBaseDao.get(clz, id);
	}

	@Override
	public List<?> find(DetachedCriteria criteria) {
		return processBaseDao.find(criteria);
	}

	@Override
	public List<?> getTaskInstByPage(PageResults page) {
		return processBaseDao.getTaskInstByPage(page);
	}

	/**
	 * 通过流程配置ID启动流程实例
	 * 
	 * @param procId
	 * @param busiType
	 * @param busiNo
	 * @return
	 * @throws ProcessServiceException
	 */
	@Override
	public WfRuProcinst startProcessInstanceById(String procId, String businessType, String businessId)
			throws ProcessServiceException {
		return this.startProcessInstanceById(procId, businessType, businessId, null);
	}

	/**
	 * 根据任务实例ID完成对应的任务
	 */
	public void complete(String taskInstId) throws ProcessServiceException {
		complete(taskInstId, null);
	}

	// @Override
	// public void complete(String taskInstId,Map<String, Object> param,TSmUser
	// user) throws ProcessServiceException{
	// user.getName();
	// user.getUserId();
	// }
	/**
	 * 
	 * @param taskInstId
	 * @param param
	 * @throws ProcessServiceException
	 */
	@Override
	public Map<String,String> complete(String taskInstId, Map<String, Object> param) throws ProcessServiceException {
		Map<String,String> result = new HashMap<String, String>();
		if (taskInstId == null || "".equals(taskInstId)) {
			throw new ProcessServiceException("taskInstId任务实例ID为空，请核查");
		}

		WfRuTask wfRuTask = this.get(WfRuTask.class, taskInstId);
		if (wfRuTask == null) {
			throw new ProcessServiceException("taskInstId任务实例ID的值不存在对应的任务，请核查");
		}

		String userId = (String) param.get(SystemConstant.CURRENT_USER_ID);
		String userName = (String) param.get(SystemConstant.CURRENT_USER_NAME);

		taskService.complete(wfRuTask.getActTaskInstId(), param);
		// 处理流程实例 start
		WfRuProcinst wfRuProcinst = this.get(WfRuProcinst.class, wfRuTask.getProcInstId());
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(wfRuProcinst.getActProcInstId()).singleResult();
		wfRuProcinst.setEndTime(DateUtils.formatDatetime(historicProcessInstance.getEndTime()));
		wfRuProcinst.setEndUserId(userId);
		wfRuProcinst.setEndUserName(userName);
		if (historicProcessInstance.getEndTime() != null && !"".equals(historicProcessInstance.getEndTime())) {
			wfRuProcinst.setState("1");// 结束
		}
		wfRuProcinst.setUpdatedBy(userId);
		wfRuProcinst.setDateUpdated(DateUtil.now());

		this.update(wfRuProcinst);
		// 处理流程实例 end

		WfRuTaskOper wfRuTaskOper = new WfRuTaskOper();
		wfRuTaskOper.setOperRecId(StringUtil.generateUUID());
		wfRuTaskOper.setProcInstId(wfRuTask.getProcInstId());
		wfRuTaskOper.setActProcInstId(wfRuTask.getActProcInstId());
		wfRuTaskOper.setTaskInstId(wfRuTask.getTaskInstId());// 此处需要存放历史表的ID
		wfRuTaskOper.setTaskId(wfRuTask.getTaskId());
		wfRuTaskOper.setActProcDefId(wfRuTask.getActProcDefId());
		wfRuTaskOper.setProcId(wfRuTask.getProcId());

		wfRuTaskOper.setChangeTaskState("1");// TODO 暂时默认，后续需处理
		wfRuTaskOper.setUserId(userId);
		wfRuTaskOper.setUserName(userName);
		wfRuTaskOper.setOperTime(DateUtil.now());
		wfRuTaskOper.setOpinion((String)param.get(SystemConstant.REASON));//处理意见
		wfRuTaskOper.setDateCreated(DateUtil.now());
		wfRuTaskOper.setCreatedBy(userId);
		wfRuTaskOper.setUpdatedBy(userName);
		wfRuTaskOper.setDateUpdated(DateUtil.now());

		this.add(wfRuTaskOper);

		// 处理任务节点
		this.delete(wfRuTask);// 删除当前节点
		// 任务实例处理
		List<Task> listTask = taskService.createTaskQuery().processInstanceId(wfRuProcinst.getActProcInstId()).list();
		for (Task task : listTask) {
			WfReTask wfReTask = processBaseDao.getWfReTaskBy(wfRuProcinst.getProcId(), task.getTaskDefinitionKey());
			WfRuTask wfRuTaskTemp = new WfRuTask();
			wfRuTaskTemp.setTaskInstId(StringUtil.generateUUID());
			wfRuTaskTemp.setActTaskInstId(task.getId());// activiti的主键
			wfRuTaskTemp.setActProcInstId(wfRuProcinst.getActProcInstId());
			wfRuTaskTemp.setActProcDefId(wfRuProcinst.getActProcDefId());
			wfRuTaskTemp.setProcInstId(wfRuProcinst.getProcInstId());
			wfRuTaskTemp.setProcId(wfRuProcinst.getProcId());
			
			wfRuTaskTemp.setTaskId(wfReTask.getTaskId());
			wfRuTaskTemp.setActId(task.getTaskDefinitionKey());// 任务节点ID
			wfRuTaskTemp.setActName(wfReTask.getActName());//
			wfRuTaskTemp.setActType(wfReTask.getActType());//
			wfRuTaskTemp.setOperateType(wfReTask.getOperateType());
			wfRuTaskTemp.setState("0");// 0-处理中
			wfRuTaskTemp.setDescription("");// 根据需要填充
			wfRuTaskTemp.setCreatedBy(userId);
			wfRuTaskTemp.setDateCreated(DateUtil.now());
			result.put("actId", wfRuTaskTemp.getActId());
			result.put("actName", wfRuTaskTemp.getActName());
			result.put("taskInstId", wfRuTaskTemp.getTaskInstId());
			this.add(wfRuTaskTemp);
		}
		return result;
	}

	@Override
	public WfRuProcinst startProcessInstanceById(String procId, String businessType, String businessId,
			Map<String, Object> param) throws ProcessServiceException {

		// 实际运行时将此处的注释去掉
		if (procId == null || "".equals(procId) || businessType == null || "".equals(businessType) || businessId == null
				|| "".equals(businessId)) {
			throw new ProcessServiceException("procId流程ID、busiType业务类型、busiNo业务号不能为空,请检查！");
		}

		WfReProcess wfReProcess = processBaseDao.get(WfReProcess.class, procId);
		if (wfReProcess == null) {
			throw new ProcessServiceException("给定procId为查询到对应的流程配置信息,请核查！");
		}

		String userId = (String) param.get(SystemConstant.CURRENT_USER_ID);
		String userName = (String) param.get(SystemConstant.CURRENT_USER_NAME);
		String remark = (String) param.get(SystemConstant.RUPROCINST_REMARK);
		// 流程实例处理
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(wfReProcess.getActProcDefId(), param);
		String actProcessInstId = processInstance.getProcessInstanceId();

		WfRuProcinst wfRuProcinst = new WfRuProcinst();
		String procInstId = StringUtil.generateUUID();
		wfRuProcinst.setProcInstId(procInstId);
		wfRuProcinst.setActProcInstId(processInstance.getId());
		wfRuProcinst.setActProcDefId(wfReProcess.getActProcDefId());
		wfRuProcinst.setProcId(procId);
		wfRuProcinst.setBusinessType(businessType);
		wfRuProcinst.setBusinessId(businessId);
		wfRuProcinst.setStartTime(DateUtils.now());
		wfRuProcinst.setStartUserId(userId);
		wfRuProcinst.setStartUserName(userName);
		// wfRuProcinst.setEndTime(endTime);
		// wfRuProcinst.setEndUserId(endUserId);
		// wfRuProcinst.setEndUserName(endUserName);
		wfRuProcinst.setSuspensionState("1");// 挂起状态：1-激活；2-挂起
		wfRuProcinst.setCreatedBy(UserUtil.getUserId());
		wfRuProcinst.setRemark(remark);
		wfRuProcinst.setState("0");// 运行中

		// 任务实例处理
		List<Task> listTask = taskService.createTaskQuery().processInstanceId(actProcessInstId).list();
		if (listTask == null || listTask.size() == 0) {
			throw new ProcessServiceException("Activiti未参生对应的Task,请核查！");
		}
		for (Task task : listTask) {
			WfReTask wfReTask = processBaseDao.getWfReTaskBy(procId, task.getTaskDefinitionKey());
			WfRuTask wfRuTask = new WfRuTask();
			wfRuTask.setTaskInstId(StringUtil.generateUUID());
			wfRuTask.setActTaskInstId(task.getId());// activiti的主键
			wfRuTask.setActProcInstId(processInstance.getId());
			wfRuTask.setActProcDefId(wfReProcess.getActProcDefId());
			wfRuTask.setProcInstId(procInstId);
			wfRuTask.setProcId(procId);

			wfRuTask.setTaskId(wfReTask.getTaskId());
			wfRuTask.setActId(task.getTaskDefinitionKey());// 任务节点ID
			wfRuTask.setActName(wfReTask.getActName());//
			wfRuTask.setActType(wfReTask.getActType());//
			// wfRuTask.setOwner("");//
			// wfRuTask.setAssignee("");//不需要存储
			// wfRuTask.setDelegationstate("");
			// wfRuTask.setEndTime(endTime);
			wfRuTask.setOperateType(wfReTask.getOperateType());
			wfRuTask.setState("0");// 0-处理中
			wfRuTask.setDescription("");// 根据需要填充
			wfRuTask.setCreatedBy(userId);
			wfRuTask.setDateCreated(DateUtil.now());
			this.add(wfRuTask);
			wfRuProcinst.setWfRuTask(wfRuTask);
		}

		this.add(wfRuProcinst);
		return wfRuProcinst;
	}

	@Override
	public List<WfRuTask> getWfRuTaskByUserId(String userId) {
		List<WfRuTask> list = processBaseDao.getWfRuTaskByUserId(userId);
		System.out.println(list.size());
		return list;
	}
	@Override
	public int statisticsWfRuTaskByUserId(String userId) {
		return this.processBaseDao.statisticsWfRuTaskByUserId(userId);
	}

	@Override
	public List<?> getWfRuTaskByUserRoleId(String userRoleId) {
		// TODO Auto-generated method stub
		return processBaseDao.getWfRuTaskByUserRoleId(userRoleId);
	}

	@Override
	public int statisticsWfRuTaskByUserRoleId(String userRoleId) {
		// TODO Auto-generated method stub
		return processBaseDao.statisticsWfRuTaskByUserRoleId(userRoleId);
	}

	@Override
	public List getWfRuTaskByUserId_(String userId) {
		return processBaseDao.getWfRuTaskByUserId_(userId);
	}

	@Override
	public WfReProcess checkExistsProcess(String processName) {
		return processBaseDao.checkExistsProcess(processName);
	}

	@Override
	public boolean deleteTaskOpers(String taskId) {
		return processBaseDao.deleteTaskOpers(taskId);
	}

	@Override
	public void deleteBySQL(String sql) {
		processBaseDao.deleteBySQL(sql);
	}

	@Override
	public List listWfRuProcinst(String defId) {
		return processBaseDao.listWfRuProcinst(defId);
	}

	@Override
	public WfReProcess getWfProcessCfg(String key) {
		return processBaseDao.getWfProcessCfg(key);
	}
	
	
	
	
	
}
