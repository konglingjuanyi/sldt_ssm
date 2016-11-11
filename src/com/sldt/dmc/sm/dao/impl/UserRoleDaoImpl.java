package com.sldt.dmc.sm.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.infinispan.util.FastCopyHashMap.EntrySet;
import org.springframework.stereotype.Repository;

import com.sldt.dmc.sm.dao.IUserRoleDao;
import com.sldt.framework.orm.hibernate.dao.BaseDao;
import com.sldt.mds.dmc.sm.entity.TSmRole;
import com.sldt.mds.dmc.sm.entity.TSmUser;

@SuppressWarnings("unchecked")
@Repository(value = "userRoleDao")
public class UserRoleDaoImpl extends BaseDao implements IUserRoleDao {

	@Override
	public List<TSmRole> fuzzyQueryUserRole(String serVal) {
		String hql = "from TSmRole where roleId like '%"+serVal+"%' or roleName like '%"+serVal+"%'";
		List<TSmRole> list = this.getListByHQL(hql, new Object[]{});
		return list;
	}

	@Override
	public List<TSmUser> listUsers(Map<String, Object> param) {
		List<TSmUser> ll = new ArrayList<TSmUser>();
		TSmUser user = null;
		SQLQuery query = this.createSQLQuery("select t1.USER_ID as userId,t1.NAME as name from t_sm_user t1, t_sm_user_role_cfg t2 where t1.USER_ID = t2.USER_ID and t2.ROLE_ID = '"+param.get("roleId")+"'");
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for(Map<String,Object> map : list){
			user = new TSmUser();
			user.setUserId((String)map.get("userId"));
			user.setName((String)map.get("name"));
			ll.add(user);
		}
		return ll;
	}
	
}
