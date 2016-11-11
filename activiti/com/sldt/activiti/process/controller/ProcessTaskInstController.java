package com.sldt.activiti.process.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sldt.activiti.process.common.DateUtil;
import com.sldt.activiti.process.domain.WfRuTask;
import com.sldt.activiti.process.service.IProcessBaseService;
import com.sldt.activiti.process.service.ITaskInstService;
import com.sldt.framework.common.ControllerHelper;
import com.sldt.framework.common.PageResults;
import com.sldt.framework.common.PageVo;
import com.sldt.mds.dmc.sm.entity.TSmRole;
import com.sldt.mds.dmc.sm.entity.TSmUserRoleCfg;
import com.sldt.mds.dmc.sm.service.RoleService;
import com.sldt.mds.dmc.sm.service.UserService;
import com.sldt.mds.dmc.sm.uitl.UserUtil;

/**
 *流程任务实例处理controller
 */
@Controller
@RequestMapping("/activiti/processTaskInst.do")
public class ProcessTaskInstController {
	
	private static final Logger log = Logger.getLogger(ProcessTaskInstController.class);
	
	@Resource(name="taskService")
	private TaskService taskService;
	@Resource(name="repositoryService")
	private RepositoryServiceImpl repositoryService;
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	@Resource(name="processBaseService")
	private IProcessBaseService processBaseService;
	@Resource(name="managementService")
	private ManagementService managementService; //
	@Resource(name="taskInstService")
	private ITaskInstService taskInstService;
	@Resource(name="userService")
	private UserService userService;
	@Autowired
	public RoleService roleService;
	
	@RequestMapping(params = "method=getTaskInstByPage")
	@ResponseBody
	public PageVo getTaskInstByPage(HttpServletRequest request) {

		PageResults page = ControllerHelper.buildPage(request);
		List<?> list = processBaseService.getTaskInstByPage(page);
		PageVo pv = new PageVo(page.getPageSize(), page.getCurrPage(), page.getTotalRecs());
		log.info("查询方法getTaskInstByPage:获取数据的条数为："+list.size());
		pv.setRows(list);
		return pv;
	}
	
	/**
	 * 跳到任务执行页
	 * @return
	 */
	@RequestMapping(params="method=runTask")
	@ResponseBody
	public JSONObject runTask(HttpServletRequest request, HttpServletResponse response, String taskInstId)
			throws Exception {
		log.info("前台传入的任务实例ID为：" + taskInstId);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", "admin");
		map.put("userName", "管理员");
		map.put("opinion", "操作建议");
		
		//数据质量问题流程
		map.put("auditFlag", "1");//审核通过标志
		map.put("greatProFlag", "1");//重大问题标志
		map.put("technicalFlag", "1");//是否涉及技术方案标志
		map.put("dealFlag", "1");//是否解决
		map.put("planAudit", "1");//是否解决
		//标准停用
//		map.put("auditFlag", "1");//审核通过标志
		//标准修改修改流程参数
//		map.put("auditFlag", "1");//审核通过标志
		map.put("confirmFlag", "3");//满足需求
		map.put("flag", "b");//满足需求
		//标准新增
//		map.put("auditFlag", "1");//审核通过标志
//		map.put("confirmFlag", "2");//满足需求
		
		JSONObject json = new JSONObject();
		if (StringUtils.isEmpty(taskInstId)) {
			json.put("success", "0");
		} else {
			processBaseService.complete(taskInstId, map);
			json.put("success", "1");
		}
		return json;
	}
	
	@RequestMapping(params="method=runCurrentTask",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String runCurrentTask(HttpServletRequest request, HttpServletResponse response){
		String state = "01";
		String result = "";
		String msg = "";
		Map<String,Object> rsMap = new HashMap<String,Object>();
		
		try {
			PageResults page = ControllerHelper.buildPage(request);
			Map<String,Object> map = page.getParameters();
			if(map.get("taskInstId") ==null){
				throw new Exception("任务实例ID为null！");
			}
			String taskInstId = (String) map.get("taskInstId");
			log.info("前台传入的任务实例ID为：" + taskInstId);
			
//			map.put("userId", ModuleUtil.getCurrUserId());
//			map.put("userName", ModuleUtil.getCurrUserId());
//			map.put("opinion", "操作建议");
			
			//数据质量问题流程
//			map.put("auditFlag", "1");//审核通过标志
//			map.put("greatProFlag", "1");//重大问题标志
//			map.put("technicalFlag", "1");//是否涉及技术方案标志
//			map.put("dealFlag", "1");//是否解决
//			map.put("planAudit", "1");//是否解决
			//标准停用
//		map.put("auditFlag", "1");//审核通过标志
			//标准修改修改流程参数
//		map.put("auditFlag", "1");//审核通过标志
//			map.put("confirmFlag", "0");//满足需求
			//标准新增
//		map.put("auditFlag", "1");//审核通过标志
//		map.put("confirmFlag", "2");//满足需求
			
			if (StringUtils.isEmpty(taskInstId)) {
				state="00";
				msg = "任务id为空";
			} else {
				Map<String,String> resultMap = processBaseService.complete(taskInstId, map);
				rsMap.put("actId", resultMap.get("actId"));
				rsMap.put("actName", resultMap.get("actName"));
				rsMap.put("taskInstId", resultMap.get("taskInstId"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			state="00";
			msg = "任务执行出现异常";
		}
		rsMap.put("state", state);
		rsMap.put("result", result);
		rsMap.put("msg", msg);
		
		return JSONObject.fromObject(rsMap).toString();
	}
	
	/**
	 * 指定任务处理人
	 * @return
	 */
	@RequestMapping(params="method=delegateUser")
	@ResponseBody
	public JSONObject delegateUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskInstId = request.getParameter("taskInstId");//任务实例id
		String actTaskInstId = request.getParameter("actTaskInstId");//activiti任务实例ID
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		log.info("前台传入的任务实例ID为：" + actTaskInstId);
		//取当前用户
		String  currUser = UserUtil.getUserId();
		JSONObject json = new JSONObject();
		if (StringUtils.isEmpty(actTaskInstId)) {
			json.put("success", "0");
		} else {
			WfRuTask wftask = processBaseService.get(WfRuTask.class, taskInstId);
			wftask.setAssignee(userId);
			wftask.setUpdatedBy(currUser);//实际中是取当前session存储的用户
			wftask.setDateUpdated(DateUtil.now());
			processBaseService.update(wftask);//更新委托人
			taskService.setAssignee(actTaskInstId, userId);//设置任务处理人
			json.put("success", "1");
		}
		return json;
	}
	
	/**
	 * 根据userId查询待办任务信息(对接外围系统用)
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=getWfRuTaskByUserId",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getWfRuTaskByUserId(HttpServletRequest request, HttpServletResponse response,String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		String state = "01";
		String msg = "";
		List tasks = null;
		try {
			/**
			 * 通过userId查询，包括用户角色包含的的待审批任务和用户被指派的待审批任务
			 * 20160923
			 */
			if("admin".equals(userId)) {	
				userId = null;
			}
			tasks = processBaseService.getWfRuTaskByUserId_(userId);
			if(tasks == null) {
				tasks = new ArrayList();
			}
			/*if("admin".equals(userId)) {
				tasks.addAll(processBaseService.getWfRuTaskByUserRoleId(null));
			}else {
				List<TSmUserRoleCfg> roleCfgLi = userService.getUserRoleCfgByUserId(userId);
				for (TSmUserRoleCfg role: roleCfgLi) {
					tasks.addAll(processBaseService.getWfRuTaskByUserRoleId(role.getRoleId()));
				}
			}*/
		} catch (Exception e) {
			state = "00";
			msg = "查询异常";
		}
		map.put("state", state);
		map.put("result", tasks);
		map.put("msg", msg);
		
		return JSONObject.fromObject(map).toString();
	}
	
	/**
	 * 根据taskId查询下一任务节点信息(对接外围系统用)
	 * @param request
	 * @param response
	 * @param wfTaskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=getNextTaskGroup",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getNextTaskGroup(HttpServletRequest request, HttpServletResponse response,String wfTaskId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> rsmap = new HashMap<String, Object>();
		String state = "01";
		String msg = "";
		taskInstService.getNextTaskInfo(wfTaskId);
		List<SequenceFlow> gatewayFlows = null;
		List<SequenceFlow> userTaskFlows = null;
		
//		===============================
		WfRuTask wftask = processBaseService.get(WfRuTask.class, wfTaskId);
		//processDefinitionId 对应表ACT_RE_PROCDEF主键信息 
		String processDefinitionId=wftask.getActProcDefId();  
		//获取bpmnModel对象  
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);  
		//因为我们这里只定义了一个Process 所以获取集合中的第一个即可  
		org.activiti.bpmn.model.Process process = bpmnModel.getProcesses().get(0);  
		//获取所有的FlowElement信息  
		Collection<FlowElement> flowElements = process.getFlowElements();  
		for (FlowElement flowElement : flowElements) {  
		//如果是任务节点  
			if (flowElement instanceof UserTask) {
			UserTask userTask=(UserTask) flowElement;
			if(!StringUtils.equals(wftask.getActId(), userTask.getId())){
				continue;
			}
			//ExclusiveGateway userTask = (ExclusiveGateway) flowElement;
			//获取入线信息  
			userTaskFlows = userTask.getOutgoingFlows();//当前userTask出线  
//			for (SequenceFlow sequenceFlow : incomingFlows) {
//				System.out.println(sequenceFlow.getId() +"&&"+sequenceFlow.getConditionExpression()+"&&"  
//						+sequenceFlow.getSourceRef()+"--"+sequenceFlow.getTargetRef()+"\r\t");  
//			}
			}
		}
		String target = getGateWay(wfTaskId);
		for(FlowElement flowElement : flowElements){
			if (flowElement instanceof ExclusiveGateway) {
				ExclusiveGateway gateway=(ExclusiveGateway) flowElement;
				if(!StringUtils.equals(target, gateway.getId())){
					continue;
				}
				gatewayFlows = gateway.getOutgoingFlows();
			}
		}
//		===============================
		if(gatewayFlows==null){
			gatewayFlows = new ArrayList<SequenceFlow>();
		}
		if(userTaskFlows == null ){
			userTaskFlows = new ArrayList<SequenceFlow>();
		}
		rsmap.put("gatewayFlows", gatewayFlows);
		rsmap.put("userTaskFlows", userTaskFlows);
		
		map.put("state", state);
		map.put("result", rsmap);
		map.put("msg", msg);
		return JSONObject.fromObject(map).toString();
	}
	
	private String getGateWay(String wfTaskId){
		String nextNode = "";
		WfRuTask wftask = processBaseService.get(WfRuTask.class, wfTaskId);
		//processDefinitionId 对应表ACT_RE_PROCDEF主键信息 
		String processDefinitionId=wftask.getActProcDefId();  
		//获取bpmnModel对象  
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);  
		//因为我们这里只定义了一个Process 所以获取集合中的第一个即可  
		org.activiti.bpmn.model.Process process = bpmnModel.getProcesses().get(0);  
		//获取所有的FlowElement信息  
		Collection<FlowElement> flowElements = process.getFlowElements();  
		for (FlowElement flowElement : flowElements) {  
		//如果是任务节点  
			if (flowElement instanceof UserTask) {
			UserTask userTask=(UserTask) flowElement;
			if(!StringUtils.equals(wftask.getActId(), userTask.getId())){
				continue;
			}
			//获取出线信息  
			List<SequenceFlow> outcomingFlows = userTask.getOutgoingFlows();  
			for (SequenceFlow sequenceFlow : outcomingFlows) {
				nextNode = sequenceFlow.getTargetRef();
				break;
			}
			}
		
		
		}
		return nextNode;
	}
	
	
	
}
