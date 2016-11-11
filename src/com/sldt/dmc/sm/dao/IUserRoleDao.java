package com.sldt.dmc.sm.dao;

import java.util.List;
import java.util.Map;

import com.mysql.fabric.xmlrpc.base.Param;
import com.sldt.mds.dmc.sm.entity.TSmRole;
import com.sldt.mds.dmc.sm.entity.TSmUser;
import com.sldt.mds.dmc.sm.vo.TreeNode;

public interface IUserRoleDao{
	
	public List<TSmRole> fuzzyQueryUserRole(String serVal);
	public List<TSmUser> listUsers(Map<String, Object> param);
	
}
