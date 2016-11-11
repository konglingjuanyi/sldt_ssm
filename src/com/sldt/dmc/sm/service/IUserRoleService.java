package com.sldt.dmc.sm.service;

import java.util.List;

import com.sldt.mds.dmc.sm.entity.TSmRole;
import com.sldt.mds.dmc.sm.vo.TreeNode;

public interface IUserRoleService{
	
	public List<TSmRole> fuzzyQueryUserRole(String serVal);
	public List<TreeNode> userRoleTree(String roleId);
	
}
