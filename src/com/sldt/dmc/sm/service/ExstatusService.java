package com.sldt.dmc.sm.service;

public interface ExstatusService{

	public String queryStatus(String orgId);
	
	public int updateStatus(String orgId,String status);
}
