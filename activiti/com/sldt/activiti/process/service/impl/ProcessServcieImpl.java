package com.sldt.activiti.process.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.springframework.stereotype.Service;

import com.sldt.activiti.process.dao.IProcessBaseDao;
import com.sldt.activiti.process.dao.IProcessDao;
import com.sldt.activiti.process.domain.WfRuTask;
import com.sldt.activiti.process.domain.WfRuTaskOper;
import com.sldt.activiti.process.service.IProcessService;

@Service
public class ProcessServcieImpl implements IProcessService {
	
	@Resource(name = "processDao")
	private IProcessDao processDao;
	
	@Resource(name = "processBaseDao")
	private IProcessBaseDao processBaseDao;
	
	@Resource
	private RuntimeService runtimeService;
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private RepositoryService repositoryService;
	
	@Resource
	private ProcessEngineImpl processEngine;
	@Resource
	private HistoryService historyService;
	
	/**
	 * 启动流程
	 * @param entity：业务数据的父类(通过多态重用代码)
	 * @param definitionKey：流程的key,如果请假流程leave,报销流程expense
	 */

	@Override
	public InputStream trace(String processInstanceId, String businessKey, String definitionKey) {
		ProcessInstance processInstance = null;
		if (null != processInstanceId && !"".equals(processInstanceId)) { // 流程实例中进行流程跟踪
			processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();
		} else { // 业务数据，审批中进行流程跟踪
			processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceBusinessKey(businessKey)
					.processDefinitionKey(definitionKey).singleResult();
		}		
		
		//通过流程定义id得到bpmnModel
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		//得到正在执行的环节id
		List<String> activeIds = runtimeService.getActiveActivityIds(processInstance.getId());
		//打印流程图
		Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());
		return ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeIds);		
	}	
	
	@Override
	public InputStream hisTrace(String processInstanceId, String businessKey, String definitionKey) {
		HistoricProcessInstance processInstance = null;
		if (null != processInstanceId && !"".equals(processInstanceId)) { // 流程实例中进行流程跟踪
			 //获取历史流程实例
	         processInstance =  historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		} else { // 业务数据，审批中进行流程跟踪
			processInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceBusinessKey(businessKey)
					.processDefinitionKey(definitionKey).singleResult();
		}		
		
		//通过流程定义id得到bpmnModel
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		//得到高亮环节id
		 List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
		//高亮环节id集合
		//ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
	    List<String> highLightedActivitis = new ArrayList<String>();
	  //高亮线路id集合
        //List<String> highLightedFlows = getHighLightedFlows(definitionEntity,highLightedActivitList);
//        for(HistoricActivityInstance tempActivity : highLightedActivitList){
//            String activityId = tempActivity.getActivityId();
//            highLightedActivitis.add(activityId);
//        }
	    highLightedActivitis.add(highLightedActivitList.get(highLightedActivitList.size()-1).getActivityId());//只高亮最后结束标识
		//List<String> activeIds = runtimeService.getActiveActivityIds(processInstance.getId());
		//打印流程图
		Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());
		return ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis);		
	}	
	
	@Override
	public List<Map<String, String>> getComments(String processInstanceId) {
		List<Attachment> attachments = taskService
				.getProcessInstanceAttachments(processInstanceId);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Attachment comment : attachments) {
			Map<String, String> map = new HashMap<String, String>();
			HistoricTaskInstance t = historyService
					.createHistoricTaskInstanceQuery()
					.taskId(comment.getTaskId()).singleResult();
			String commentName = t.getName() + "(" + comment.getName() + ")";
			map.put("taskName", commentName);
			map.put("comment", comment.getDescription());
			list.add(map);
		}
		return list;
	}
	
	/**
     * 获取需要高亮的线
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

	@Override
	public String startProcessInstance(String processCfgId, String bizType,
			String bizId, String startUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean completeUserTask(WfRuTask task, WfRuTaskOper taskOper,
			String currentUserId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
