package com.sldt.dmc.sm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sldt.dmc.sm.dao.ExstatusDao;
import com.sldt.dmc.sm.service.ExstatusService;

@Service(value = "statusService")
public class ExstatusServiceImpl implements ExstatusService{
	@Resource(name="statusDao")
	private ExstatusDao statusDao;
	
	@Override
	public String queryStatus(String orgId){
		return statusDao.queryStatus(orgId);
	}

	@Override
	public int updateStatus(String orgId, String status) {
		return statusDao.updateStatus(orgId, status);
	}
}

