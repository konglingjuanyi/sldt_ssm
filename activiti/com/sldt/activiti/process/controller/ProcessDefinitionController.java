package com.sldt.activiti.process.controller;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.cas.dmp.ModuleUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sldt.activiti.process.common.ReflectUtil;
import com.sldt.activiti.process.domain.WfReProcess;
import com.sldt.activiti.process.domain.WfReTask;
import com.sldt.activiti.process.domain.WfReTaskOper;
import com.sldt.activiti.process.domain.WfRuProcinst;
import com.sldt.activiti.process.exception.ProcessServiceException;
import com.sldt.activiti.process.service.IProcessBaseService;
import com.sldt.dmc.sm.service.IUserRoleService;
import com.sldt.framework.common.ControllerHelper;
import com.sldt.framework.common.PageResults;
import com.sldt.framework.common.PageVo;
import com.sldt.mds.dmc.sm.entity.TSmRole;
import com.sldt.mds.dmc.sm.entity.TSmUser;
import com.sldt.mds.dmc.sm.service.UserService;
import com.sldt.mds.dmc.sm.uitl.DateUtils;
import com.sldt.mds.dmc.sm.vo.TreeNode;

@Controller
@RequestMapping("/activiti/processDefinition.do")
public class ProcessDefinitionController {

	private static Log log = LogFactory.getLog(ProcessDefinitionController.class);

	@Resource
	private RepositoryService repositoryService;
	@Resource
	private IProcessBaseService processBaseService;
	@Resource
	private UserService userService;
	
	@Resource(name = "userRoleService")
	private IUserRoleService userRoleService;
	/**
	 * 分页查询
	 * @return
	 */
	@RequestMapping(params="method=getProcessDefinitionByPage")
	@ResponseBody
	public PageVo getProcessDefinitionByPage(HttpServletRequest request, HttpServletResponse response,String pocDefKey,String procDefName) {
		PageResults page = ControllerHelper.buildPage(request);
		
//		ProcessDefinitionQuery query = repositoryService
//				.createProcessDefinitionQuery();
		// 查询条件
//		if (null != definitionName && !"".equals(definitionName.trim())) {
//			query.processDefinitionNameLike("%" + definitionName + "%");
//		}
//		if (null != definitionKey && !"".equals(definitionKey.trim())) {
//			query.processDefinitionKey(definitionKey);
//		}

//		long rowCount = query.count();
//		List<ProcessDefinition> result = query.listPage(page.getStartIndex(), page.getPageSize());
		
		//PageVo pv = new PageVo(page.getPageSize(), page.getCurrPage(), page.getTotalRecs());
		
		DetachedCriteria queryParams = DetachedCriteria.forClass(WfReProcess.class);
		
		if(!StringUtils.isEmpty(procDefName)){
			try {
				procDefName = URLDecoder.decode(procDefName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
			queryParams.add(Restrictions.like("procName", procDefName,MatchMode.ANYWHERE));
		}
		
		if(!StringUtils.isEmpty(pocDefKey)){
			queryParams.add(Restrictions.like("actProcDefKey", pocDefKey,MatchMode.ANYWHERE));
		}
		
		page = processBaseService.findPage(queryParams, page.getStartIndex(), page.getPageSize());
		
		PageVo pv = new PageVo(page.getPageSize(), page.getCurrPage(), page.getTotalRecs());
		
		pv.setRows(ReflectUtil.getBaseInfoList( page.getQueryResult(),WfReProcess.class));
		
		return pv;
	}
	
	/**
	 * 查询所有配置的流程定义(对接外围系统用)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="method=getProcessDefinitionAll",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProcessDefinitionAll(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String state = "01";
		String msg = "";
		List<WfReProcess> list = null;
		try {
			DetachedCriteria queryParams = DetachedCriteria.forClass(WfReProcess.class);
			list = (List<WfReProcess>)processBaseService.find(queryParams);
		} catch (Exception e) {
			state = "00";
			msg = "查询异常";
		}
		map.put("state", state);
		map.put("result", list);
		map.put("msg", msg);
		return JSONArray.fromObject(map).toString();
	}
	
	/**
	 * 查询流程定义
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="method=queryDefinition")
	@ResponseBody
	public Object queryDefinition(HttpServletRequest request, HttpServletResponse response) {
		String procId = request.getParameter("procId");
		WfReProcess process = null;
		try {
			process = processBaseService.get(WfReProcess.class, procId);
		} catch (Exception e) {
			
		}
		return process;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(params="method=deleteProcessDefinition")
	@ResponseBody
	public JSONObject deleteProcessDefinition(HttpServletRequest request, HttpServletResponse response,String ids) {
		String[] idsArr = null; 
		JSONObject json = new JSONObject();
		if(!StringUtils.isEmpty(ids)){
			idsArr = ids.split(",");//proc_id
			for (String id : idsArr) {
				//step 1 : query WfReProcess by proc_id
				WfReProcess wfproc = processBaseService.get(WfReProcess.class, id);
				for(WfReTask task : wfproc.getTasks()){
					Set<WfReTaskOper> wfReTaskOpers = task.getTaskOpers();
					for(WfReTaskOper taskOper :  wfReTaskOpers){
						//step 2 : delete WfReTaskOper
						processBaseService.delete(taskOper);
					}
					//step 3 : delete WfReTask
					processBaseService.delete(task);
				}
				//step 4 : delete WfReProcess
				processBaseService.delete(wfproc);
				
				//repositoryService.deleteDeployment(id, true);
			}
			json.put("success", "1");
		}else{
			json.put("success", "0");
		}
		return json;
	}
	
	/**
	 * 启动流程
	 * @return
	 * @throws ProcessServiceException 
	 */
	@RequestMapping(params="method=startInstance")
	@ResponseBody
	public JSONObject startInstance(HttpServletRequest request, HttpServletResponse response,String definitionId) throws ProcessServiceException {
		JSONObject json = new JSONObject();
		if(StringUtils.isEmpty(definitionId)){
			json.put("success", "0");
		}else{
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("firsthecker", "admin");
			variables.put("title", "我的任务。。。。");
			variables.put("userName", "admin");
			processBaseService.startProcessInstanceById(definitionId, "01", "0111110100011",variables);
			//完成第一个任务
//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
			//taskService.complete(task.getId(),variables);
			json.put("success", "1");
		}
		return json;
	}
	
	/**
	 * 启动流程返回实例id（与外围系统对接用）
	 * @return
	 * @throws ProcessServiceException 
	 */
	@RequestMapping(params="method=startNewInstance",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String startNewInstance(HttpServletRequest request, HttpServletResponse response,String definitionId){
		Map<String,Object> map = new HashMap<String, Object>();
		String state = "01";//成功标志
		String result = "";
		String msg = "";
		if(StringUtils.isEmpty(definitionId)){
			state = "00";//失败
			msg = "传入的流程定义id为空";
			throw new RuntimeException("传入的流程定义id不能为空！");
		}else{
			Map<String, Object> variables = new HashMap<String, Object>();
			WfRuProcinst wfRuProcinst = null;
			try {
				variables.put("firsthecker",ModuleUtil.getCurrUserId());
				variables.put("title", "流程启动");
				variables.put("userName", ModuleUtil.getCurrUserId());
				wfRuProcinst =	processBaseService.startProcessInstanceById(definitionId, "01", "0111110100011",variables);
				result = wfRuProcinst.getProcInstId();
				map.put("wfTaskInstId", wfRuProcinst.getWfRuTask().getTaskInstId());
				map.put("actId", wfRuProcinst.getWfRuTask().getActId());
				map.put("actName", wfRuProcinst.getWfRuTask().getActName());
			} catch (Exception e) {
				e.printStackTrace();
				state = "00";//失败
				msg = "启动流程异常";
			}
		}
		map.put("state", state);
		map.put("result", result);
		map.put("msg", msg);
		return JSONObject.fromObject(map).toString();
	}
	
	/**
	 * 查询已经部署的流程
	 * @return
	 */
	@RequestMapping(params="method=queryDefinitionList")
	@ResponseBody
	public JSONArray queryDefinitionList() {
		JSONArray jsnarr = new JSONArray();
		JSONObject json = null;
		/*查询已经部署的流程*/
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
		for(ProcessDefinition bean : list){
			json = new JSONObject();
			json.put("id", bean.getId());
			json.put("name", bean.getName());
			jsnarr.add(json);
		}
		return jsnarr;
	}
	
	
	/**
	 * 初始化流程定义任务节点
	 * @param definitionId
	 * @return
	 */
	@RequestMapping(params="method=initTaskNodes")
	@ResponseBody
	public Object initTaskNodes(String definitionId) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		BpmnModel model = repositoryService.getBpmnModel(definitionId);
		if(model != null) {  
		    Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();  
		    for(FlowElement e : flowElements) {
		    	if(e instanceof UserTask){
		    		map = new HashMap<String,Object>();
			    	map.put("actId", e.getId());
			    	map.put("actName", e.getName());
			    	list.add(map);
		    	}
		    }  
		}
		return list;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(params="method=getDefinitionById")
	@ResponseBody
	public Object getDefinitionById(String definitionId) {
		/*根据部署的id查询流程定义*/
		ProcessDefinition procssDef  = repositoryService.createProcessDefinitionQuery().processDefinitionId(definitionId).singleResult();
		if(procssDef != null)
			return ReflectUtil.getBaseInfoForObj(procssDef, ProcessDefinition.class);
		else
			return null;
	}

	/**
	 * 流程定义转换模型：从源码拷贝，不需要，太明白
	 */
	@RequestMapping(params="method=convert2Model")
	@ResponseBody
	public void convert2Model(HttpServletRequest request, HttpServletResponse response,String definitionId) {
		try {
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionId(definitionId).singleResult();

			InputStream bpmnStream = repositoryService.getResourceAsStream(
					processDefinition.getDeploymentId(),
					processDefinition.getResourceName());
			XMLInputFactory xif = XMLInputFactory.newInstance();
			InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
			XMLStreamReader xtr = xif.createXMLStreamReader(in);
			BpmnModel bpmnModel = new BpmnXMLConverter()
					.convertToBpmnModel(xtr);

			if (bpmnModel.getMainProcess() == null
					|| bpmnModel.getMainProcess().getId() == null) {
				response.getWriter().print("转换失败...");
			} else {

				if (bpmnModel.getLocationMap().size() == 0) {
					response.getWriter().print("转换失败...");
				} else {

					BpmnJsonConverter converter = new BpmnJsonConverter();
					ObjectNode modelNode = converter.convertToJson(bpmnModel);
					Model modelData = repositoryService.newModel();

					ObjectNode modelObjectNode = new ObjectMapper()
							.createObjectNode();
					modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME,
							processDefinition.getName());
					modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION,
							1);
					modelObjectNode.put(
							ModelDataJsonConstants.MODEL_DESCRIPTION,
							processDefinition.getDescription());
					modelData.setMetaInfo(modelObjectNode.toString());
					modelData.setName(processDefinition.getName());
					
					repositoryService.saveModel(modelData);

					repositoryService.addModelEditorSource(modelData.getId(),
							modelNode.toString().getBytes("utf-8"));
					response.sendRedirect(request.getContextPath()
							+ "/service/editor?id="
							+ modelData.getId());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 用户模糊搜索下拉框初始化数据
	 * @param userName
	 * @return
	 */
	@RequestMapping(params="method=fuzzyQueryUser")
	@ResponseBody
	public Object fuzzyQueryUser(String userName) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", userName);
		PageResults page = new PageResults();
		page.setParameters(params);
		List<TSmUser> users = userService.listUser(page);
		return users;
	}
	
	/**
	 * 用户模糊搜索下拉框初始化数据
	 * @param userName
	 * @return
	 */
	@RequestMapping(params="method=fuzzyQueryUserRole")
	@ResponseBody
	public Object fuzzyQueryUserRole(String serVal) {
		List<TSmRole> list = new ArrayList<TSmRole>();
		try {
			serVal = URLDecoder.decode(serVal, "UTF-8");
			list= userRoleService.fuzzyQueryUserRole(serVal);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 存储流程配置相关信息
	 * @param userName
	 * @return
	 */
	@RequestMapping(params="method=saveDefinitionConfigs")
	@ResponseBody
	public Object saveDefinitionConfigs(HttpServletRequest request, HttpServletResponse response) {
		String deifinitionConfigStr = request.getParameter("deifinitionConfigStr");
		String tasksNodesConfigStr = request.getParameter("tasksNodesConfigStr");
		JSONObject defConfig = JSONObject.fromObject(deifinitionConfigStr);
		JSONArray tasksNodesConfig = JSONArray.fromObject(tasksNodesConfigStr);
		//组装bean 
		//{"definitionId":"dmc:4:3306","deploymentId":"3301","definitionName":"dmc","definitionKey":"dmc","version":"4","procName":"太热特瑞特人"}
		//[{"actId":"usertask3","actName":"变更申请单复审<架构办>竞签admingreen","operType":"0","taskUsers":[{"userId":"admin","userName":"admin"},{"userId":"green","userName":"green"}]}]
		WfReProcess wfReProcess = new WfReProcess();
		wfReProcess.setProcId(generateUUID());
		wfReProcess.setProcName(defConfig.getString("procName"));
		wfReProcess.setActProcDefId(defConfig.getString("definitionId"));
		wfReProcess.setDeploymentId(defConfig.getString("deploymentId"));
		wfReProcess.setVersion(defConfig.getString("version"));
		wfReProcess.setActProcDefKey(defConfig.getString("definitionKey"));
		wfReProcess.setActProcDefName(defConfig.getString("definitionName"));
		wfReProcess.setOrderIndex(10);
		wfReProcess.setState("1");
		wfReProcess.setDateCreated(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
		WfReProcess procTemp = checkExists(wfReProcess.getProcName());
		if(procTemp != null){
			wfReProcess.setProcId(procTemp.getProcId());
		}
		
		Set<WfReTask> wfReTasks = new HashSet<WfReTask>();
		WfReTask wfReTask = null;
		
		Set<WfReTaskOper> wfReTaskOpers = new HashSet<WfReTaskOper>();
		for(Object obj : tasksNodesConfig){
			JSONObject json = (JSONObject)obj;
			wfReTask = new WfReTask();
			wfReTask.setTaskId(generateUUID());
			wfReTask.setActId(json.getString("actId"));
			wfReTask.setActName(json.getString("actName"));
			wfReTask.setActType("usertask");
			wfReTask.setOperateType(json.getString("operType"));
			wfReTask.setOrderIndex(10);
			wfReTask.setProcId(wfReProcess.getProcId());
			wfReTask.setDateCreated(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			
			JSONArray userRole = (JSONArray)json.get("taskUsersRole");
			WfReTaskOper wfReTaskOper = null;
			for(Object objusr : userRole){
				JSONObject jsonusr = (JSONObject)objusr;
				wfReTaskOper = new WfReTaskOper();
				wfReTaskOper.setTaskOperId(generateUUID());
				wfReTaskOper.setTaskId(wfReTask.getTaskId());
				wfReTaskOper.setOperType("1");
				wfReTaskOper.setGroupId(jsonusr.getString("userRoleId"));
				wfReTaskOper.setGroupName(jsonusr.getString("userRoleName"));
				wfReTaskOper.setDateCreated(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				wfReTaskOpers.add(wfReTaskOper);
			}
			wfReTask.setTaskOpers(wfReTaskOpers);
			wfReTasks.add(wfReTask);
			processBaseService.deleteTaskOpers(wfReTask.getTaskId());
		}
		boolean success = true;
		try {
			wfReProcess.setTasks(wfReTasks);
			
			for(WfReTaskOper bean : wfReTaskOpers){
				processBaseService.add(bean);
			}
			if(procTemp!=null){
			//删除之前的配置
			processBaseService.deleteBySQL("delete from t_wf_re_task where proc_id = '"+procTemp.getProcId()+"'");
			}
			for(WfReTask bean : wfReTasks){
				processBaseService.add(bean);
			}
			
			/**
			 *存在则更新，不存在就添加
			 */
			if(procTemp == null){
				processBaseService.add(wfReProcess);
			}else{
				processBaseService.delete(procTemp);
				wfReProcess.setProcId(procTemp.getProcId());
				processBaseService.add(wfReProcess);
			}
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", success);
		return map;
	}
	
	public WfReProcess checkExists(String processName){
		return processBaseService.checkExistsProcess(processName);
	}
	
	/**
	 * 文件部署模型
	 * @param bpmnPath
	 * @return
	 */
	@RequestMapping(params="method=deployActivitiByBpmn",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deployActivitiByBpmn(String bpmnPath) {
			String state = "01";
			String msg = "";
			Map<String,Object> rsMap = new HashMap<String, Object>();
	        try {  
	        	Deployment deployment = null;
	            InputStream fileInputStream = FileUtils.openInputStream(new File(bpmnPath));  
	            String extension = FilenameUtils.getExtension(bpmnPath);  
	            if (extension.equals("zip") || extension.equals("bar")) {  
	                ZipInputStream zip = new ZipInputStream(fileInputStream);  
	                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();  
	            } else {  
	                deployment = repositoryService.createDeployment().addInputStream(bpmnPath, fileInputStream).deploy();  
	            }  
	        } catch (Exception e) {  
	        	state = "01";
	        	msg = "模型部署失败";
	            log.error("error on deploy process, because of file input stream", e);  
	        }  
	        rsMap.put("state", state);
	        rsMap.put("msg", msg);
	        rsMap.put("result", "");
	        return JSONObject.fromObject(rsMap).toString();  
	}
	
	/**
	 * 
	 * @param bpmnPath
	 * @return
	 */
	@RequestMapping(params="method=loadUserRoleTree")
	@ResponseBody
	public Object loadUserRoleTree(String roleId) {
		List<TreeNode> nodes = null;
		if(roleId != null && !"".equals(roleId.trim())){
			nodes = userRoleService.userRoleTree(roleId);
		}
		if(nodes == null){ //避免数据为空时，返回前端的对象为空引起js错误
			nodes = new ArrayList<TreeNode>();
		}
		return nodes;
	}
	
	
	public String generateUUID(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
}



