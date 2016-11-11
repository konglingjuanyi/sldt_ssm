package com.sldt.activiti.process.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sldt.activiti.process.dao.IProcessBaseDao;
import com.sldt.activiti.process.domain.WfReProcess;
import com.sldt.activiti.process.domain.WfReTask;
import com.sldt.activiti.process.domain.WfReTaskOper;
import com.sldt.activiti.process.domain.WfRuProcinst;
import com.sldt.activiti.process.domain.WfRuTask;
import com.sldt.framework.common.PageResults;
import com.sldt.framework.orm.hibernate.dao.BaseDao;

@Component("processBaseDao")
public class ProcessBaseDaoImpl implements IProcessBaseDao{
	
	private static final Logger log = Logger.getLogger(ProcessBaseDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;
	
	@Autowired
	private BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public PageResults findPage(DetachedCriteria detachedCriteria , int start, int pageSize) {
		PageResults page = new PageResults();
		//通过DetachCriteria得到可执行的criteria
		//currentSession必须要有事务
		Criteria criteria = detachedCriteria.getExecutableCriteria(sessionFactory.getCurrentSession());
		//查询总行数
		//设置投影，查询语句select count(*) from Dept where ... 如果有条件		
		criteria.setProjection(Projections.rowCount());
		Long rowCount = (Long) criteria.uniqueResult();
		page.setTotalRecs(Integer.valueOf(rowCount+""));
		//查询一页数据
		//清楚投影, 相当于from Dept where ... 如果有条件
		criteria.setProjection(null);
		
		//设置返回结果类型，默认是ROOT_ENTITY=创建criteria 的类
		//当存在关联查询时值，回事数组，代码设置返回值为ROOT_ENTITY
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		
		criteria.setFirstResult(start);
		criteria.setMaxResults(pageSize);
		if(detachedCriteria.toString().indexOf("domain.WfRuProcinst")==-1){
			System.out.println(detachedCriteria.getClass());
			//按照id倒序查询
			criteria.addOrder(Order.asc("procName"));
		}
		//查询一页数据
		List<?> list = criteria.list();
		page.setQueryResult(list);
		
		return page;
	}

	@Override
	public <T> void add(T obj) {
		sessionFactory.getCurrentSession().save(obj);
	}

	@Override
	public <T> void update(T obj) {
		sessionFactory.getCurrentSession().update(obj);
	}

	@Override
	public <T> void save(T obj) {
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
	}

	@Override
	public <T> void delete(T obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> clz, Serializable id) {
		return (T) sessionFactory.getCurrentSession().get(clz, id);
	}

	@Override
	public List<?> find(DetachedCriteria criteria) {
		Criteria c = criteria.getExecutableCriteria(sessionFactory.getCurrentSession());
		return c.list();
	}
	
	@Override
	public List<?> getTaskInstByPage(PageResults page) {
		
		String sql = baseDao.getSQL("getTaskInstByPage", page.getParameters());
		log.info("sql:"+sql);
	    Map<String, Object> paramMap = page.getParameters();
		Object[] paramObj = baseDao.getParam("getTaskInstByPage", paramMap);
		return baseDao.findListByPage(sql,paramObj,page);
	}
	
	/**
	 * 通过ID获取
	 * @param clas
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T loadEntityById(Class<?> clas,String id){
		return (T) baseDao.loadEntityInfo(clas, id);
	}
	
	/***
	 * 删除数据byId
	 * @param c
	 * @param id
	 */
	public void deleteById(Object c,String id){
		baseDao.deleteById(c.getClass(), id);
	}
	
	@Override
	public WfReTask getWfReTaskBy(String procId, String actId) {

		if (procId == null || "".equals(procId) || actId == null || "".equals(actId)) {
			return null;
		}
		Query query = baseDao.getSession()
				.createQuery("from WfReTask where procId = '" + procId + "' and " + " actId = '" + actId + "' ");
		return (WfReTask) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WfReTaskOper> getWfReTaskOper(String taskId) {
		if (taskId == null || "".equals(taskId)) {
			return null;
		}
		Query query = baseDao.getSession()
				.createQuery("from WfReTaskOper where taskId = '" + taskId + "' ");
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WfRuTask> getWfRuTaskByUserId(String userId) {
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		
		String sql = baseDao.getSQL("getWfRuTaskByUserId", paramMap);
		log.info("sql:"+sql);

		Object[] paramObj = baseDao.getParam("getWfRuTaskByUserId", paramMap);
	    return baseDao.queryListForMap(sql, paramObj);
	}	
	@Override
	public int statisticsWfRuTaskByUserId(String userId) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		/**
		 * modify by chenbo 2016-09-21
		 * admin 用户为超级用户，不做用户的过滤控制
		 */
		if(!"admin".equals(userId))
		paramMap.put("userId", userId);
		String sql = baseDao.getSQL("statisticsWfRuTaskByUserId", paramMap);
		log.info("sql:"+sql);
		Object[] paramObj = baseDao.getParam("statisticsWfRuTaskByUserId", paramMap);
		Long l = this.baseDao.queryForLong(sql, paramObj);
		return l.intValue();
	}
	@Override
	public List<?> getWfRuTaskByUserRoleId(String userRoleId) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("groupId", userRoleId);
		
		String sql = baseDao.getSQL("getWfRuTaskByUserId", paramMap);
		log.info("sql:"+sql);

		Object[] paramObj = baseDao.getParam("getWfRuTaskByUserId", paramMap);
	    return baseDao.queryListForMap(sql, paramObj);
	}
	
	@Override
	public int statisticsWfRuTaskByUserRoleId(String userRoleId) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("groupId", userRoleId);
		String sql = baseDao.getSQL("statisticsWfRuTaskByUserId", paramMap);
		log.info("sql:"+sql);
		Object[] paramObj = baseDao.getParam("statisticsWfRuTaskByUserId", paramMap);
		Long l = this.baseDao.queryForLong(sql, paramObj);
		return l.intValue();
	}
	@Override
	public List getWfRuTaskByUserId_(String userId) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		String sql = baseDao.getSQL("getWfRuTaskByUserId_Ext", paramMap);
		log.info("sql:"+sql);
		Object[] paramObj = baseDao.getParam("getWfRuTaskByUserId_Ext", paramMap);
	    return baseDao.queryListForMap(sql, paramObj);
	}
	
	@Override
	public WfReProcess checkExistsProcess(String processName) {
		String prcId = "";
		WfReProcess process = null;
		String hqlString = "from WfReProcess where procName = '"+processName+"'";
		List<WfReProcess> list = baseDao.getListByHQL(hqlString, new Object[]{});
		if(list.size()>0){
			process = list.get(0);
		}
		return process;
	}
	
	@Override
	public boolean deleteTaskOpers(String taskId) {
		boolean suc = true;
		try {
			Query query = baseDao.createSQLQuery("delete from WfReTaskOper where taskId = "+taskId);
			query.executeUpdate();
		} catch (HibernateException e) {
			suc = false;
		}
		return suc;
	}
	
	@Override
	public void deleteById(Class<WfReProcess> clz, Serializable id) {
		baseDao.deleteById(clz, id);
	}
	
	@Override
	public void deleteBySQL(String sql) {
		baseDao.executeSql(sql, new Object[]{});
	}
	
	@Override
	public List listWfRuProcinst(String defId) {
		String hqlString = "from WfRuProcinst where actProcDefId = '"+defId+"'";
		List<WfRuProcinst> list = baseDao.getListByHQL(hqlString, new Object[]{});
		return list;
	}
	
	@Override
	public WfReProcess getWfProcessCfg(String actKey) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("actKey", actKey);
		String sql = baseDao.getSQL("getWfProcessCfg", paramMap);
		log.info("sql:"+sql);
		Object[] paramObj = baseDao.getParam("getWfProcessCfg", paramMap);
	    return (WfReProcess)baseDao.getEntityByHQL(sql, paramObj);
	}

	
	
	

	
}
