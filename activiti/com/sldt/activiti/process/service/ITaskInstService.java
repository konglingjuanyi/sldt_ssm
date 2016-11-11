package com.sldt.activiti.process.service;

import org.activiti.engine.impl.task.TaskDefinition;

public interface ITaskInstService {
	public TaskDefinition getNextTaskGroup(String wftaskId) throws Exception;
	public void getNextTaskInfo(String wftaskId) throws Exception;
}
