package com.sldt.activiti.process.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.criterion.DetachedCriteria;

import com.sldt.activiti.process.domain.WfReProcess;
import com.sldt.activiti.process.domain.WfReTask;
import com.sldt.activiti.process.domain.WfReTaskOper;
import com.sldt.activiti.process.domain.WfRuTask;
import com.sldt.framework.common.PageResults;

public interface IProcessBaseDao {
	
	/**
	 * 增加
	 * @param obj
	 * @return
	 */
	public <T> void add(T obj);
	
	/**
	 * 修改
	 * @param obj
	 * @return
	 */
	public <T> void update(T obj);	
	
	/**
	 * 增加，修改
	 * @param obj
	 * @return
	 */
	public <T> void save(T obj);
	
	/**
	 * 修改
	 * @param obj
	 * @return
	 */
	public <T> void delete(T obj);
	
	
	
	/**
	 * 加载
	 * @param obj
	 * @return
	 */
	public <T> T get(Class<T> clz, Serializable id);
	
	/**
	 * 查询所有
	 * @param criteria：离线criteria
	 * @return
	 */
	public List<?> find(DetachedCriteria criteria);
	
	/**
	 * 分页查询
	 * @param criteria：离线criteria, 在具体的Action中构件好，处理好条件，传给service，传给dao
	 * @param start：起始下标
	 * @param pageSize：每页行数
	 * @return pageResult，result,一页数据,rowCount：总页数
	 */
	public PageResults findPage(DetachedCriteria detachedCriteria, int start, int pageSize);
	
	/**
	 * 查询获取任务实例信息
	 * @param page
	 * @return
	 */
	public List<?> getTaskInstByPage(PageResults page);
	
	/**
	 *  根据流程定义id和节点id获取任务信息
	 * @param procId
	 * @param actId
	 * @return
	 */
	public WfReTask getWfReTaskBy(String procId,String actId);
	
	/**
	 * 根据任务ID获取操作人任务信息
	 * @param taskId
	 * @return
	 */
	public List<WfReTaskOper> getWfReTaskOper(String taskId);
	
	/**
	 *  根据用户ID获取待执行任务
	 * @param userId
	 * @return
	 */
	public List<WfRuTask> getWfRuTaskByUserId(String userId);
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
	/**
	 * 通过userId查询，包括用户角色包含的的待审批任务和用户被指派的待审批任务
	 * @param userId
	 * @return
	 */
	public List getWfRuTaskByUserId_(String userId);
	
	public WfReProcess checkExistsProcess(String processName);
	
	public boolean deleteTaskOpers(String taskId);
	
	public void deleteById(Class<WfReProcess> clz,Serializable id);
	
	public void deleteBySQL(String sql);
	
	public List listWfRuProcinst(String defId);
	
	public WfReProcess getWfProcessCfg(String key);
}
