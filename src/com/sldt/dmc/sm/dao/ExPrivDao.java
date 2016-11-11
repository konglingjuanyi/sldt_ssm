package com.sldt.dmc.sm.dao;

import java.util.List;
import java.util.Map;

public interface ExPrivDao {

	public List<Map<String,Object>> queryExPriv();
	
	public List<Map<String,Object>> queryisParent(String pid);
}
