package com.sldt.activiti.process.rest.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;

import com.sldt.activiti.process.rest.IActivitiRestService;
import com.sldt.activiti.process.service.IProcessBaseService;

public class ActivitiRestServiceImpl implements IActivitiRestService{
	
	@Resource(name="processBaseService")
	private IProcessBaseService processBaseService;
	
	@Resource(name = "cacheManager")
	private EhCacheManager cacheManager;
	
	@Override
	public String getStatisticsWfRuTaskInst(String userId, String callback) {
		/**
		 * 新增对象缓存机制
		 * modify by chenbo 2016-09-13
		 */
		String count = "0";
		//如果是超级管理员的话就查询所有待办
		if(StringUtils.equals("admin", userId)){
			userId = null;
		}
		List list = this.processBaseService.getWfRuTaskByUserId_(userId);
		count = String.valueOf(list.size());
		return callback+"("+count+")";
	}

	
}
