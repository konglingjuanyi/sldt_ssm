package com.sldt.mds.dmc.sm.rest.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.sf.json.JSONArray;

import com.sldt.framework.orm.hibernate.dao.BaseDao;
import com.sldt.mds.dmc.sm.rest.dao.ISsmRestDao;

@SuppressWarnings("unchecked")
@Repository(value = "ssmRestDao")
public class SsmRestDaoImpl extends BaseDao implements ISsmRestDao {
	
	@Override
	public String getDapDept() {
		String sql = getSQL("getDapDept");
		List<Map<String, Object>> list = this.queryListForMap(sql,null);
		return JSONArray.fromObject(list).toString();
	}
	
}
