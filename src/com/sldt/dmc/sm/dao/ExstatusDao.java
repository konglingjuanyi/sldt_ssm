package com.sldt.dmc.sm.dao;

public interface ExstatusDao {

	public String queryStatus(String orgId);
	
	public int updateStatus(String orgId,String status);
}
