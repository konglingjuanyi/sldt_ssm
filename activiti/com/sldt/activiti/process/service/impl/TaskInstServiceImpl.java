package com.sldt.activiti.process.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sldt.activiti.process.dao.IProcessBaseDao;
import com.sldt.activiti.process.domain.WfRuTask;
import com.sldt.activiti.process.service.ITaskInstService;
@Service(value = "taskInstService")
public class TaskInstServiceImpl implements ITaskInstService {
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
     * 获取下一个用户任务用户组信息  
     * @param String taskId     任务Id信息  
     * @return  下一个用户任务用户组信息  
     * @throws Exception 
     */  
    public TaskDefinition getNextTaskGroup(String wftaskId) throws Exception {  
    	WfRuTask wfTask = (WfRuTask)processBaseDao.get(WfRuTask.class, wftaskId);
    	String taskId = wfTask.getActTaskInstId();
        ProcessDefinitionEntity processDefinitionEntity = null;  
          
        String id = null;  
          
        TaskDefinition task = null;  
          
        //获取流程实例Id信息   
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();  
          
        //获取流程发布Id信息   
        String definitionId = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();  
          
        processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(definitionId);  
          
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();  
          
        //当前流程节点Id信息   
        String activitiId = execution.getActivityId();    
          
        //获取流程所有节点信息   
        List<ActivityImpl> activitiList = processDefinitionEntity.getActivities();   
          
        //遍历所有节点信息   
        for(ActivityImpl activityImpl : activitiList){      
            id = activityImpl.getId();     
              
            // 找到当前节点信息  
            if (activitiId.equals(id)) {  
                //获取下一个节点信息   
                task = nextTaskDefinition(activityImpl, activityImpl.getId(), null, processInstanceId);  
                break;  
            }  
        }  
          
        return task;  
    }  
      
    /**  
     * 下一个任务节点信息,  
     *  
     * 如果下一个节点为用户任务则直接返回,  
     *  
     * 如果下一个节点为排他网关, 获取排他网关Id信息, 根据排他网关Id信息和execution获取流程实例排他网关Id为key的变量值,  
     * 根据变量值分别执行排他网关后线路中的el表达式, 并找到el表达式通过的线路后的用户任务信息  
     * @param ActivityImpl activityImpl     流程节点信息  
     * @param String activityId             当前流程节点Id信息  
     * @param String elString               排他网关顺序流线段判断条件, 例如排他网关顺序留线段判断条件为${money>1000}, 若满足流程启动时设置variables中的money>1000, 则流程流向该顺序流信息  
     * @param String processInstanceId      流程实例Id信息  
     * @return  
     */    
    private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String elString, String processInstanceId){   
              
        PvmActivity ac = null;  
          
        Object s = null;  
          
        //如果遍历节点为用户任务并且节点不是当前节点信息   
            if("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())){    
                //获取该节点下一个节点信息   
                TaskDefinition taskDefinition = ((UserTaskActivityBehavior)activityImpl.getActivityBehavior()).getTaskDefinition();    
                return taskDefinition;    
            }else{    
                //获取节点所有流向线路信息   
                List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();    
                List<PvmTransition> outTransitionsTemp = null;    
                for(PvmTransition tr : outTransitions){      
                    ac = tr.getDestination(); //获取线路的终点节点      
                    //如果流向线路为排他网关   
                    if("exclusiveGateway".equals(ac.getProperty("type"))){    
                        outTransitionsTemp = ac.getOutgoingTransitions();  
                          
                        //如果网关路线判断条件为空信息   
                        if(StringUtils.isEmpty(elString)) {  
                            //获取流程启动时设置的网关判断条件信息   
                            elString = getGatewayCondition(ac.getId(), processInstanceId);  
                        }  
                          
                        //如果排他网关只有一条线路信息   
                        if(outTransitionsTemp.size() == 1){    
                            return nextTaskDefinition((ActivityImpl)outTransitionsTemp.get(0).getDestination(), activityId, elString, processInstanceId);    
                        }else if(outTransitionsTemp.size() > 1){  //如果排他网关有多条线路信息   
                            for(PvmTransition tr1 : outTransitionsTemp){    
                                s = tr1.getProperty("conditionText");  //获取排他网关线路判断条件信息   
                                //判断el表达式是否成立   
                                if(isCondition(ac.getId(), StringUtils.trim(s.toString()), elString)){    
                                    return nextTaskDefinition((ActivityImpl)tr1.getDestination(), activityId, elString, processInstanceId);    
                                }    
                            }    
                        }    
                    }else if("userTask".equals(ac.getProperty("type"))){    
                        return ((UserTaskActivityBehavior)((ActivityImpl)ac).getActivityBehavior()).getTaskDefinition();    
                    }else{    
                    }    
                }     
            return null;    
        }    
    }  
      
    /** 
     * 查询流程启动时设置排他网关判断条件信息  
     * @param String gatewayId          排他网关Id信息, 流程启动时设置网关路线判断条件key为网关Id信息  
     * @param String processInstanceId  流程实例Id信息  
     * @return 
     */  
    public String getGatewayCondition(String gatewayId, String processInstanceId) {  
        Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();  
        return runtimeService.getVariable(execution.getId(), gatewayId).toString();  
    }  
      
    /** 
     * 根据key和value判断el表达式是否通过信息  
     * @param String key    el表达式key信息  
     * @param String el     el表达式信息  
     * @param String value  el表达式传入值信息  
     * @return 
     */  
    public boolean isCondition(String key, String el, String value) {  
        ExpressionFactory factory = new ExpressionFactoryImpl();    
        SimpleContext context = new SimpleContext();    
        context.setVariable(key, factory.createValueExpression(value, String.class));    
        ValueExpression e = factory.createValueExpression(context, el, boolean.class);    
        return (Boolean) e.getValue(context);  
    }

	@Override
	public void getNextTaskInfo(String wftaskId) throws Exception {
		WfRuTask wfTask = (WfRuTask)processBaseDao.get(WfRuTask.class, wftaskId);
		String procInstanceId = wfTask.getActProcInstId();
	    List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstanceId).list(); 

	    for(Task task : tasks){
	    ProcessDefinitionEntity def = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(task.getProcessDefinitionId());
	    List<ActivityImpl> activitiList = def.getActivities(); //rs是指RepositoryService的实例 

	    String excId = task.getExecutionId();
	    ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
	    String activitiId = execution.getActivityId(); 

	    for(ActivityImpl activityImpl:activitiList){
	    String id = activityImpl.getId();
	    if(activitiId.equals(id)){
	    System.out.println("当前任务："+activityImpl.getProperty("name")); //输出某个节点的某种属性
	    List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();//获取从某个节点出来的所有线路
	    for(PvmTransition tr:outTransitions){
	    PvmActivity ac = tr.getDestination(); //获取线路的终点节点
	    System.out.println("下一步任务任务："+ac.getProperty("name"));
	    }
	    break;
	    }
	    } 
    }

	} 
    
    
}
