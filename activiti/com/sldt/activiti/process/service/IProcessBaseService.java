package com.sldt.activiti.process.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.criterion.DetachedCriteria;

import com.sldt.activiti.process.domain.WfReProcess;
import com.sldt.activiti.process.domain.WfRuProcinst;
import com.sldt.activiti.process.exception.ProcessServiceException;
import com.sldt.framework.common.PageResults;

public interface IProcessBaseService {

	/**
	 * 增加
	 * 
	 * @param obj
	 * @return
	 */
	public <T> void add(T obj);

	/**
	 * 修改
	 * 
	 * @param obj
	 * @return
	 */
	public <T> void update(T obj);

	/**
	 * 增加，修改
	 * 
	 * @param obj
	 * @return
	 */
	public <T> void save(T obj);

	/**
	 * 修改
	 * 
	 * @param obj
	 * @return
	 */
	public <T> void delete(T obj);

	/**
	 * 加载
	 * 
	 * @param obj
	 * @return
	 */
	public <T> T get(Class<T> clz, Serializable id);

	/**
	 * 查询所有
	 * 
	 * @param criteria：离线criteria
	 * @return
	 */
	public List<?> find(DetachedCriteria criteria);

	/**
	 * 分页查询
	 * 
	 * @param criteria：离线criteria,
	 *            在具体的Action中构件好，处理好条件，传给service，传给dao
	 * @param start：起始下标
	 * @param pageSize：每页行数
	 * @return pageResult，result,一页数据,rowCount：总页数
	 */
	public PageResults findPage(DetachedCriteria detachedCriteria, int start, int pageSize);

	/**
	 * 分页查询获取任务实例的信息
	 * 
	 * @param page
	 * @return
	 */
	public List<?> getTaskInstByPage(PageResults page);

	/**
	 * 启动流程
	 * 
	 * @param procId
	 * @param businessType
	 * @param businessId
	 * @return
	 */
	public WfRuProcinst startProcessInstanceById(String procId, String businessType, String businessId)
			throws ProcessServiceException;

	/**
	 * 启动流程
	 * 
	 * @param procId
	 * @param businessType
	 * @param businessId
	 * @param param
	 * @return
	 * @throws ProcessServiceException
	 */
	public WfRuProcinst startProcessInstanceById(String procId, String businessType, String businessId,
			Map<String, Object> param) throws ProcessServiceException;

	/***
	 * 根据任务实例的ID执行任务实例
	 * 
	 * @param taskInstId
	 * @throws ProcessServiceException
	 */
	public void complete(String taskInstId) throws ProcessServiceException;

	/**
	 * 根据任务实例和参数完成任务
	 * 
	 * @param taskInstId
	 * @param param
	 * @throws ProcessServiceException
	 */
	public Map<String,String> complete(String taskInstId, Map<String, Object> param) throws ProcessServiceException;
	
	/**
	 * 根据用户的ID获取待执行的任务
	 * @param userId
	 * @return
	 */
	public List<?> getWfRuTaskByUserId(String userId);
	
	/**
	 * 根据用户的角色ID获取待执行的任务
	 * @param userId
	 * @return
	 */
	public List<?> getWfRuTaskByUserRoleId(String userRoleId);
	
	/**
	 * 统计待执行任务
	 * @param userId
	 */
	public int statisticsWfRuTaskByUserId(String userId);
	
	/**
	 * 根据角色ID统计待执行任务
	 * @param userId
	 */
	public int statisticsWfRuTaskByUserRoleId(String userRoleId);

	public List getWfRuTaskByUserId_(String userId);
	
	public WfReProcess checkExistsProcess(String processName);
	
	public boolean deleteTaskOpers(String taskId);
	
	public void deleteById(Class<WfReProcess> class1,Serializable id);
	
	public void deleteBySQL(String sql);
	
	public List listWfRuProcinst(String defId);
	
	public WfReProcess getWfProcessCfg(String key);
	
	
}
