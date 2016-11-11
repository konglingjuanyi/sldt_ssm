package com.sldt.activiti.process.controller;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sldt.activiti.process.domain.WfReProcess;
import com.sldt.activiti.process.domain.WfReTask;
import com.sldt.activiti.process.domain.WfReTaskOper;
import com.sldt.activiti.process.domain.WfRuProcinst;
import com.sldt.activiti.process.service.IProcessBaseService;
import com.sldt.framework.common.ControllerHelper;
import com.sldt.framework.common.PageResults;
import com.sldt.framework.common.PageVo;

@Controller
@RequestMapping("/activiti/processModel.do")
public class ProcessModelController {
	private static Log log = LogFactory.getLog(ProcessModelController.class);
	
	/**
	 * 业务对象
	 */
	@Resource
	private RepositoryService repositoryService;
	
	@Resource
	private IProcessBaseService processBaseService;
	
	/**
	 * 分页查询
	 * @return
	 */
	@RequestMapping(params="method=getProcessModelByPage")
	@ResponseBody
	public PageVo getProcessModelByPage(HttpServletRequest request, HttpServletResponse respons,String name) {
		PageResults page = ControllerHelper.buildPage(request);
		//创建query
		ModelQuery query = repositoryService.createModelQuery().orderByCreateTime().desc();
		
		if (null != name && !"".equals(name)) {
			query.modelNameLike("%" + name + "%");
		}
		
		//使用querey查询总行数
		long rowCount = query.count();
		//查询一页数据
		List<Model> result = query.listPage(page.getStartIndex(), page.getPageSize());
		page.setTotalRecs(Integer.valueOf(rowCount+""));
		PageVo pv = new PageVo(page.getPageSize(), page.getCurrPage(), page.getTotalRecs());
		pv.setRows(result);
		return pv;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(params="method=deleteProcessModel")
	@ResponseBody
	public JSONObject deleteProcessModel(HttpServletRequest request, HttpServletResponse response,String ids) {
		String[] idsArr = null; 
		
		JSONObject json = new JSONObject();
		
		if(!StringUtils.isEmpty(ids)){
			idsArr = ids.split(",");
			for (String id : idsArr) {
				repositoryService.deleteModel(id);
			}
			json.put("success", "1");
		}else{
			json.put("success", "0");
		}
		return json;
	}
	
	/**
	 * 跳到添加页面
	 * @return
	 */
	public String toAdd() {
		return "add";
	}	

	/**
	 * 创建模型：从源码拷贝，不需要，太明白
	 */
	@RequestMapping(params="method=addProcessModel")
	@ResponseBody
	public void addProcessModel(HttpServletRequest request, HttpServletResponse response,String name,String description,String key) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace",
					"http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);
			Model modelData = repositoryService.newModel();

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			description = StringUtils.defaultString(description);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
					description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(name);
			modelData.setKey(StringUtils.defaultString(key));
			
			//保存模型
			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(),
					editorNode.toString().getBytes("UTF-8"));
			Map<String,Object> outParam = new HashMap<String,Object>();
			//跳转到activiti modeler渲染模型到页面
			response.sendRedirect(request.getContextPath()
					+ "/service/editor?id=" + modelData.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据Model部署流程：从源码拷贝。
	 */
	@RequestMapping(params="method=deploy")
	@ResponseBody
	public Object deploy(HttpServletRequest request, HttpServletResponse response,String modelId,String isDeployed) {
		JSONObject json = new JSONObject();
		try {
			//如果已经部署则干掉
			if(Boolean.valueOf(isDeployed)){
				Model modelData = repositoryService.getModel(modelId);
				List<Deployment> list =  repositoryService.createDeploymentQuery().deploymentName(modelData.getName()).list();
				for(Deployment dvp : list){
					repositoryService.deleteDeployment(dvp.getId(),true);
				}
			}
			Model modelData = repositoryService.getModel(modelId);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelData
							.getId()));
			byte[] bpmnBytes = null;

			BpmnModel model = new BpmnJsonConverter()
					.convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model, "GBK");
			String processName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment()
					.name(modelData.getName())
					.addString(processName, new String(bpmnBytes)).deploy();
			json.put("success", "1");
		} catch (Exception e) {
		e.printStackTrace();
			json.put("success", "0");
		}
		return json;

	}
	
	/**
	 * 验证模型是否已经是部署状态
	 * <p>Description:</p>
	 * @param request
	 * @param response
	 * @param modelId
	 * @return
	 */
	@RequestMapping(params="method=isDeployed")
	@ResponseBody
	public Object isDeployed(HttpServletRequest request, HttpServletResponse response,String modelId) {
		JSONObject json = new JSONObject();
		try {
			Model modelData = repositoryService.getModel(modelId);
			List<?> list =  repositoryService.createDeploymentQuery().deploymentName(modelData.getName()).list();
			if(list != null && list.size()>0){
				json.put("success", "1");
			}
		} catch (Exception e) {
		e.printStackTrace();
			json.put("success", "0");
		}
		return json;

	}
	
	/**
	 * 导出model的xml文件：从源码拷贝，不需要，太明白
	 */
	@RequestMapping(params="method=exportBpmnFile")
	@ResponseBody
	public ResponseEntity<byte[]> exportBpmnFile(HttpServletRequest request, HttpServletResponse response,String modelId) {
		ByteArrayInputStream in = null;
		BpmnModel bpmnModel = null;
		byte[] bpmnBytes = null;
		 HttpHeaders headers = null;
		try {
			Model modelData = repositoryService.getModel(modelId);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService
					.getModelEditorSource(modelData.getId()));
			 bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			bpmnBytes = xmlConverter.convertToXML(bpmnModel);
			//ActionContext.getContext().put("exportBpmnFileName", bpmnModel.getMainProcess().getId() + ".bpmn20.xml");

			//in = new ByteArrayInputStream(bpmnBytes);
		 headers = new HttpHeaders();   
		 String fileName=new String((modelData.getName()+".xml").getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
		 headers.setContentDispositionFormData("attachment", fileName);
		 headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(bpmnBytes,headers, HttpStatus.CREATED);    
	}
	
	@RequestMapping(params="method=isApproving")
	@ResponseBody
	public Object isApproving(HttpServletRequest request, HttpServletResponse response,String modelId) {
		JSONObject json = new JSONObject();
		try {
			Model modelData = repositoryService.getModel(modelId);
			String modelKey = "";
			if(modelData != null){
				modelKey = modelData.getKey();
			}
			
			ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey(modelKey).singleResult();
			
			String defId = "";
			
			if(pd != null){
				defId = pd.getId();
			}
			
			/**
			 * 通过流程定义id查询是否已经产生流程实例
			 */
			List<WfRuProcinst> insts = processBaseService.listWfRuProcinst(defId);
			int count = 0;
			json.put("key", modelKey);
			if(insts != null){
				count = insts.size();
			}
			json.put("count", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;

	}
	
	
	//deleteWfProcessCfg
	@RequestMapping(params="method=deleteWfProcessCfg")
	@ResponseBody
	public Object deleteWfProcessCfg(HttpServletRequest request, HttpServletResponse response,String modelId) {
		JSONObject json = new JSONObject();
		boolean suc = true;
		try {
			Model modelData = repositoryService.getModel(modelId);
			String modelKey = "";
			if(modelData != null){
				modelKey = modelData.getKey();
			}
			
			WfReProcess wfprcs = processBaseService.getWfProcessCfg(modelKey);
			
			if(wfprcs != null){
				for(WfReTask task : wfprcs.getTasks()){
					for(WfReTaskOper oper : task.getTaskOpers()){
						processBaseService.delete(oper);
					}
					processBaseService.delete(task);
				}
				processBaseService.delete(wfprcs);
			}
			
		} catch (Exception e) {
			suc = false;
		}
		json.put("suc", suc);
		return json;
	}
}
