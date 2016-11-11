package com.sldt.activiti.process.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.activiti.engine.ManagementService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.query.NativeQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sldt.activiti.process.common.ProcessVariableEnum;
import com.sldt.activiti.process.service.IProcessBaseService;
import com.sldt.activiti.process.service.IProcessService;
import com.sldt.framework.common.ControllerHelper;
import com.sldt.framework.common.PageResults;
import com.sldt.framework.common.PageVo;

@Controller
@RequestMapping("/activiti/processTask.do")
public class ProcessTaskController {

	private static Log log = LogFactory.getLog(ProcessTaskController.class);

	@Resource
	private TaskService taskService;
	@Resource
	private RepositoryServiceImpl repositoryService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private IProcessBaseService baseService;
	@Resource
	private ManagementService managementService; //
	@Resource
	private IProcessService processService;

	
	/**
	 * @MethodName: delete
	 * @Description: 删除待办任务
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="method=deleteProcessTask")
	@ResponseBody
	public JSONObject deleteProcessTask(HttpServletRequest request, HttpServletResponse response,String ids) {
		String[] idsArr = null; 
		
		JSONObject json = new JSONObject();
		
		if(!StringUtils.isEmpty(ids)){
			idsArr = ids.split(",");
			for (String id : idsArr) {
				try {
					taskService.deleteTask(id, true);
					json.put("success", "1");
				} catch (Exception e) {
					json.put("success", "3");
				}
			}
			
		}else{
			json.put("success", "0");
		}
		return json;
	}	
	
	/**
	 * @MethodName: findPage
	 * @Description: 分页查询
	 * @return
	 */
	@RequestMapping(params="method=getProcessTaskByPage")
	@ResponseBody
	public void getProcessTaskByPage(HttpServletRequest request, HttpServletResponse response) {
		PageResults page = ControllerHelper.buildPage(request);
		
		//伪代码
		//select res.* 任务表 res left join 任务的候选人表 i on... where 
			//res.assginee = 当前用户 or 
		    //i.user_id_ = 当前用户 or
		    //i.group_id_ in 当前用户职位列表
		//in 不能使用参数
		StringBuilder sql = new StringBuilder("select DISTINCT RES.* from "
				+ managementService.getTableName(Task.class) + " RES left join "
				+ managementService.getTableName(IdentityLinkEntity.class) + " I on I.TASK_ID_ = RES.ID_ ");
//				+ " where RES.ASSIGNEE_ = #{assignee} "
//				+ " or I.USER_ID_ = #{candidateUser} ");
//				+ " or I.GROUP_ID_ IN  ");
		//当前用户
		//User currentUser = baseService.get(User.class, SecurityHolder.getCurrentUser().getId());
		
		//得到当前用户的职位列表
//		StringBuilder candidateGroups = new StringBuilder("(");
//		for (Job j : currentUser.getJobs()) {
//			candidateGroups.append("'" + j.getJobName() + "',");
//		}
//		if (candidateGroups.length() > 1) {
//			candidateGroups.deleteCharAt(candidateGroups.length() - 1);
//		}
//		candidateGroups.append(")");
//		sql.append(candidateGroups);
		
		
		//使用nativerQuery可以直接查询标准sql语句,直接操作底层所有的activiti的表
		NativeQuery query = taskService.createNativeTaskQuery()
			.sql(sql.toString());
//			.parameter("assignee", currentUser.getUserName())
//			.parameter("candidateUser", currentUser.getUserName());
		//查询一页数据
		List<Task> tasks = query.listPage(page.getStartIndex(), page.getPageSize());
		//查询总行数
		
		/*
		  select count(*) form (
		    select res.* 任务表 res left join 任务的候选人表 i on... where 
		    res.assginee = 当前用户 or 
	        i.user_id_ = 当前用户 or
	        i.group_id_ in 当前用户职位列表
	        //查询一页数据的语句，作为子查询，查询总行数
	      )
	    */
		
		long rowCount = query.sql("select count(*) from (" + sql.toString() + ") t1").count();
		List<Map<String, Object>> map = doResult(tasks);
		//=======================临时代码=====================
		Map<String,Object> tempMap = null;
		for(Map<String,Object> mp : map){
			tempMap = new HashMap<String,Object>();
			TaskEntity t1 = (TaskEntity)mp.get("task");
			tempMap.put("id", t1.getId());
			tempMap.put("name", t1.getName());
			tempMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t1.getCreateTime()));
			mp.put("task", tempMap);
		}
		
		page.setTotalRecs((int)rowCount);
		PageVo pv = new PageVo(page.getPageSize(), page.getCurrPage(), page.getTotalRecs());
		
		pv.setRows(map);
		
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonStr;
//		try {
//			jsonStr = mapper.writeValueAsString(pv);
//			System.out.println("################" + jsonStr);
//		} catch (JsonGenerationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		JSONObject json = JSONObject.fromObject(pv);
		
//		return pv;
		
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			log.error("查询待办任务列表出错！", e);
		}
	}
	
	
	
	/**
	 * 跳到任务执行页
	 * 
	 * @return
	 */
	@RequestMapping(params="method=toTask")
	@ResponseBody
	public void toTask(HttpServletRequest request, HttpServletResponse response,String taskId,ModelMap mm) throws Exception {
		//得到任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//得到业务对象(请假单,报销单)
//		Object model = taskService.getVariable(taskId,
//				ProcessVariableEnum.model.toString());
//		//得到申请人
//		Object requestUser = taskService.getVariable(taskId,
//				ProcessVariableEnum.requestUser.toString());
		//把业务对象存入对象栈，页面使用struts2回显
//		mm.getValueStack().push(model);
//		mm.put("model", model);
//		//在OgnlContext的Map中存申请人
//		mm.put("requestUser", requestUser);
//		//在OgnlContext的Map中存任务名称
//		mm.put("taskName", task.getName());

//		getButtonsForTransition(task,mm);

		// 查询审批意见
//		List<Map<String, String>> list = processService.getComments(task
//				.getProcessInstanceId());
		//在map中存审批意见
//		mm.put("commentList", list);
		
		//在流程中保存变量transition=出去的线的名字
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("flag", "1");
//		//领取任务
//		taskService.claim(task.getId(),"admin");
//		//完成任务
		taskService.complete(task.getId(), variables);
		
		//跳转到任务执行页面

		request.getRequestDispatcher("../page/activiti/process/task/taskManage.jsp").forward(request,//task.getDescription()
				response);

	}
	
	/**
	 * 得到当前任务，出去的线,outgoing transition得到执行按钮列表,存到OgnlContext的Map中
	 * @param task
	 */
	private void getButtonsForTransition(Task task,ModelMap mm) {
		ProcessDefinitionEntity pde = (ProcessDefinitionEntity) repositoryService
				.getDeployedProcessDefinition(task.getProcessDefinitionId());
		ExecutionEntity exe = (ExecutionEntity) runtimeService
				.createExecutionQuery().executionId(task.getExecutionId())
				.singleResult();
		ActivityImpl activity = pde.findActivity(exe.getActivityId());
		List<PvmTransition> transitions = activity.getOutgoingTransitions();
		List<String> buttons = new ArrayList<String>();
		for (PvmTransition pvmTransition : transitions) {
			buttons.add((String)pvmTransition.getProperty("name"));
		}
		mm.put("buttonList", buttons);
	}	
	

	/**
	 * 处理代办任务的返回数据，因为单独返回task数据，页面不知道是请假，还是报销
	 *   任务名称=task.getName
	 *   开始时间=task.getStartTime
	 *   申请人= 任务变量requestUser.userName
	 *   标题= 任务变量(业务数据,比如请假单).tile
	 *   流程名称=查询任务的流程定义.name
	 * @param list
	 * @return
	 */
	public List<Map<String, Object>> doResult(List<Task> list) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Task task : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			//业务数据,在启动流程时，存放到流程引擎中的变量
			Object model = taskService.getVariable(task.getId(),
					ProcessVariableEnum.model.toString());
			//申请人,在启动流程时，存放到流程引擎中的变量
			Object requestUser = taskService.getVariable(task.getId(),
					ProcessVariableEnum.requestUser.toString());
			
			
			//=========临时数据=======
			Object title = taskService.getVariable(task.getId(),
					"title");
			Object userName = taskService.getVariable(task.getId(),
					"userName");
//			map.put("model", model);
//			map.put("requestUser", requestUser);
			map.put("task", task);
			map.put("title", title);
			map.put("userName", userName);
			//查询任务所有的流程定义,得到流程名称(请假流程，报销流程)
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionId(task.getProcessDefinitionId())
					.singleResult();
			//流程名称
			map.put("processName", processDefinition.getName());
			result.add(map);
		}
		return result;
	}
}
