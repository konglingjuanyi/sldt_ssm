package com.sldt.dmc.sm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sldt.dmc.sm.dao.IUserRoleDao;
import com.sldt.dmc.sm.service.IUserRoleService;
import com.sldt.framework.common.PageResults;
import com.sldt.mds.dmc.sm.dao.RoleDao;
import com.sldt.mds.dmc.sm.dao.UserDao;
import com.sldt.mds.dmc.sm.entity.TSmRole;
import com.sldt.mds.dmc.sm.entity.TSmUser;
import com.sldt.mds.dmc.sm.vo.TreeNode;
@Service(value = "userRoleService")
public class UserRoleServiceImpl implements IUserRoleService {
	@Resource(name="userRoleDao")
	private IUserRoleDao userRoleDao;
	@Resource(name="roleDao")
	private RoleDao roleDao;
	@Resource(name="userDao")
	private UserDao userDao;
	
	public IUserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(IUserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	@Override
	public List<TSmRole> fuzzyQueryUserRole(String serVal) {
		
		return userRoleDao.fuzzyQueryUserRole(serVal);
	}

	@Override
	public List<TreeNode> userRoleTree(String roleId) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode node = null;
		TreeNode userNode = null;
		List<TSmRole> roles = null;
		Map<String, Object> filter = new HashMap<String, Object>();
//		if("0000".equals(roleId)){
			roles = roleDao.listAllRoles();
			if(roles != null){
				for(TSmRole role : roles){
					node = new TreeNode();
					node.setIconSkin("pIcon01");
					node.setId(role.getRoleId());
					node.setName(role.getRoleName());
//					if(hasCfgUserRole(null, role.getRoleId())){
//						node.setIsParent("true");
//					}
					
					nodes.add(node);
				}
				List<TSmUser> list = userDao.listAllUser(null);
				for(TSmUser user : list){
					userNode = new TreeNode();
					userNode.setIconSkin("pIcon01");
					userNode.setId(user.getUserId());
					userNode.setName(user.getName());
					nodes.add(userNode);
				}
			}
//		}
//		else{
//			filter.put("roleId", roleId);
//			List<TSmUser> list = userRoleDao.listUsers(filter);
//			for(TSmUser user : list){
//				userNode = new TreeNode();
//				userNode.setId(user.getUserId());
//				userNode.setName(user.getName());
//				userNode.setpId(roleId);
//				nodes.add(userNode);
//			}
//		}
		
		
		
		return nodes;
	}
	
	private boolean hasCfgUserRole(String userId, String roleId){
		PageResults page = new PageResults();
		page.setParameter("userId", userId);
		page.setParameter("roleId", roleId);
		return userDao.hasCfgUserRole(page);
	}
	
	

}
