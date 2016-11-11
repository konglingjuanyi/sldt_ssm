package com.sldt.activiti.process.controller;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sldt.activiti.process.common.ReflectUtil;
import com.sldt.activiti.process.domain.WfRuProcinst;
import com.sldt.activiti.process.service.IProcessBaseService;
import com.sldt.activiti.process.service.IProcessService;
import com.sldt.framework.common.ControllerHelper;
import com.sldt.framework.common.PageResults;
import com.sldt.framework.common.PageVo;
import com.sldt.mds.dmc.sm.uitl.StringUtil;

@Controller
@RequestMapping("/activiti/processInstance.do")
public class ProcessInstanceController {

	private static Log log = LogFactory.getLog(ProcessInstanceController.class);

	@Resource
	private ProcessEngineImpl processEngine;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private RepositoryServiceImpl repositoryService;
	@Resource
	private HistoryService historyService;
	@Resource
	private IProcessService processService;
	@Resource
	private IProcessBaseService iBaseService;
	@Resource
	private TaskService taskService;

	/**
	 * 分页查询
	 * @return
	 */
	@RequestMapping(params = "method=getProcessInstanceByPage")
	@ResponseBody
	public PageVo getProcessInstanceByPage(HttpServletRequest request,
			HttpServletResponse response, String businessId) {
		PageResults page = ControllerHelper.buildPage(request);
		//
		// ProcessInstanceQuery query = runtimeService
		// .createProcessInstanceQuery();
		// if (null != definitionKey && !"".equals(definitionKey.trim())) {
		// query.processDefinitionKey(definitionKey);
		// }

		// long rowCount = query.count();
		// List<ProcessInstance> result = query.listPage(page.getStartIndex(),
		// page.getPageSize());
		//
		// page.setTotalRecs((int)rowCount);
		// PageVo pv = new PageVo(page.getPageSize(), page.getCurrPage(),
		// page.getTotalRecs());
		// pv.setRows(ReflectUtil.getBaseInfoList(result,ProcessInstance.class,Execution.class));
		// return pv;

		DetachedCriteria queryParams = DetachedCriteria
				.forClass(WfRuProcinst.class);

		if (!StringUtils.isEmpty(businessId)) {
			try {
				businessId = URLDecoder.decode(businessId, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			queryParams.add(Restrictions.like("businessId", businessId,
					MatchMode.ANYWHERE));
		}
		PageResults pageInst = iBaseService.findPage(queryParams,
				page.getStartIndex(), page.getPageSize());

		List<WfRuProcinst> instList = (List<WfRuProcinst>) pageInst
				.getQueryResult();

		for (WfRuProcinst bean : instList) {
			// 获取流程实例当前节点
			List<Task> tasks = taskService.createTaskQuery()
					.processInstanceId(bean.getActProcInstId()).list();
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(bean.getActProcInstId())// 使用流程实例ID查询
					.singleResult();
			if (pi == null) {
				// 流程结束
				bean.setCurActName("流程已结束");
			} else {
				// 流程尚未结束
				for (Task task : tasks) {
					ProcessDefinitionEntity def = (ProcessDefinitionEntity) repositoryService
							.getDeployedProcessDefinition(task
									.getProcessDefinitionId());
					List<ActivityImpl> activitiList = def.getActivities();

					String excId = task.getExecutionId();
					ExecutionEntity execution = (ExecutionEntity) runtimeService
							.createExecutionQuery().executionId(excId)
							.singleResult();
					String activitiId = execution.getActivityId();

					for (ActivityImpl activityImpl : activitiList) {
						String id = activityImpl.getId();
						if (activitiId.equals(id)) {
							bean.setCurActName((String) activityImpl
									.getProperty("name"));
							// List<PvmTransition> outTransitions =
							// activityImpl.getOutgoingTransitions();//获取从某个节点出来的所有线路
							// for(PvmTransition tr:outTransitions){
							// PvmActivity ac = tr.getDestination(); //获取线路的终点节点
							// System.out.println("下一步任务："+ac.getProperty("name"));
							// }
							break;
						}
					}
				}
			}
		}

		PageVo pv = new PageVo(page.getPageSize(), page.getCurrPage(),
				pageInst.getTotalRecs());

		pv.setRows(ReflectUtil.getBaseInfoList(pageInst.getQueryResult(),WfRuProcinst.class));

		return pv;
	}

	/**
	 * 查询根据实例ID查询历史信息(与外围系统对接用)
	 * @param request
	 * @param response
	 * @param procInstId
	 * @return
	 */
	@RequestMapping(params = "method=getProcessInstance",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProcessInstance(HttpServletRequest request,
			HttpServletResponse response, String procInstId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String state = "01";
		String msg = "";
		
		WfRuProcinst wfRuProcinst = null;
		try {
			 wfRuProcinst = iBaseService.get(WfRuProcinst.class,procInstId);
			// 获取流程实例当前节点
			List<Task> tasks = taskService.createTaskQuery()
					.processInstanceId(wfRuProcinst.getActProcInstId()).list();
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(wfRuProcinst.getActProcInstId())// 使用流程实例ID查询
					.singleResult();
			if (pi == null) {
				// 流程结束
				wfRuProcinst.setCurActName("流程已结束");
			} else {
				// 流程尚未结束
				for (Task task : tasks) {
					ProcessDefinitionEntity def = (ProcessDefinitionEntity) repositoryService
							.getDeployedProcessDefinition(task
									.getProcessDefinitionId());
					List<ActivityImpl> activitiList = def.getActivities();

					String excId = task.getExecutionId();
					ExecutionEntity execution = (ExecutionEntity) runtimeService
							.createExecutionQuery().executionId(excId)
							.singleResult();
					String activitiId = execution.getActivityId();

					for (ActivityImpl activityImpl : activitiList) {
						String id = activityImpl.getId();
						if (activitiId.equals(id)) {
							wfRuProcinst.setCurActName((String) activityImpl
									.getProperty("name"));
							// List<PvmTransition> outTransitions =
							// activityImpl.getOutgoingTransitions();//获取从某个节点出来的所有线路
							// for(PvmTransition tr:outTransitions){
							// PvmActivity ac = tr.getDestination(); //获取线路的终点节点
							// System.out.println("下一步任务："+ac.getProperty("name"));
							// }
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			state = "00";
			msg = "查询异常";
		}
		map.put("state", state);
		map.put("result", wfRuProcinst);
		map.put("msg", msg);
		return JSONObject.fromObject(map).toString();
	}

	/**
	 * 流程实例删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=deleteProcessInstance")
	@ResponseBody
	public JSONObject deleteProcessInstance(HttpServletRequest request,
			HttpServletResponse response, String ids) {
		String[] idsArr = null;
		JSONObject json = new JSONObject();
		if (!StringUtils.isEmpty(ids)) {
			idsArr = ids.split(",");
			for (String id : idsArr) {
				runtimeService.deleteProcessInstance(id, "删除原因测试");
			}
			json.put("success", "1");
		} else {
			json.put("success", "0");
		}
		return json;
	}

	/**
	 * 流程跟踪
	 */
	@RequestMapping(params = "method=tracePicture")
	@ResponseBody
	public void tracePicture(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processInstanceId = request.getParameter("processInstanceId");
		String businessKey = request.getParameter("businessKey");
		String definitionKey = request.getParameter("definitionKey");
		String procInstId = request.getParameter("activityId");
		if(StringUtil.isNull(processInstanceId)){
			WfRuProcinst wf = (WfRuProcinst)iBaseService.get(WfRuProcinst.class, procInstId);
			processInstanceId = wf.getActProcInstId();
		}
		InputStream in = null;
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		if (pi == null) {
			// 流程结束
			in = processService.hisTrace(processInstanceId, businessKey,
					definitionKey);
		} else {
			// 流程尚未结束
			in = processService.trace(processInstanceId, businessKey,
					definitionKey);
		}
		// 得到流程跟踪的数据流InputStream
		FileCopyUtils.copy(in, response.getOutputStream());
	}

}
