package com.sldt.dmc.sm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sldt.dmc.sm.dao.ExstatusDao;
import com.sldt.framework.orm.hibernate.dao.BaseDao;


@SuppressWarnings("unchecked")
@Repository(value = "statusDao")
public class ExstatusDaoImpl extends BaseDao implements ExstatusDao{

	@SuppressWarnings("unchecked")
	@Override
	public String queryStatus(String orgId){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orgId", orgId);
        String  sql=this.getSQL("queryStatus", map);
		Object []  params=this.getParam("queryStatus", map);	
		List<?> list=this.queryListForMap(sql, params);
		if(list.size()==0){
			return null;
		}else{
			Map<?,?> row = (Map<?,?>) list.get(0);
			String status = (String) row.get("IS_DAP_STS");
			return status;		
	}
}

	@Override
	public int updateStatus(String orgId, String status) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("status", status);
        String  sql=this.getSQL("updateStatus", map);
		Object []  params=this.getParam("updateStatus", map);
		return this.updateSQL(sql, params);
	}
}