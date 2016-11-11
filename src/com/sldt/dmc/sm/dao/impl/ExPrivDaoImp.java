package com.sldt.dmc.sm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sldt.dmc.sm.dao.ExPrivDao;
import com.sldt.framework.orm.hibernate.dao.BaseDao;

@SuppressWarnings("unchecked")
@Repository(value = "exPrivDao")
public class ExPrivDaoImp extends BaseDao implements ExPrivDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> queryExPriv(){
		Map<String,Object> map=new HashMap<String, Object>();
        String  sql=this.getSQL("queryExPriv", map);
		Object []  params=this.getParam("queryExPriv", map);	
		List<Map<String,Object>> list=this.queryListForMap(sql, params);
		
		return list;
		
	}
	
	public List<Map<String,Object>> queryisParent(String pid){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pid", pid);
        String  sql=this.getSQL("queryExPriv", map);
		Object []  params=this.getParam("queryExPriv", map);	
		List<Map<String,Object>> list=this.queryListForMap(sql, params);
		
		return list;
		
	}
}
