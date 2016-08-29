package com.sldt.dmc.ssm.parms.dao.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sldt.dmc.ssm.parms.bean.Parameter;
import com.sldt.dmc.ssm.parms.dao.OnlineParmsDao;

public class OnlineParmsDaoImpl implements OnlineParmsDao{
	 private static Log logger = LogFactory.getLog(OnlineParmsDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Parameter> getOnlineParms(String sql) {
		List<Parameter> parameterList = new ArrayList<Parameter>();
		List<Map<String, Object>> maps = null;
		try {
			maps = getJdbcTemplate().queryForList(sql);
		} catch (Exception e) {
			logger.info("系统参数数据访问层执行sql=" + sql + "失败，失败原因：" + e.getMessage());
			return parameterList;
		}
		if (maps != null && maps.size() > 0) {
			for (Map map : maps) {
				Parameter p = initParameter(map);
				parameterList.add(p);
			}
		}
		// TODO Auto-generated method stub
		return parameterList;
	}
	
	private Parameter initParameter(Map map) {
		Parameter parameter = new Parameter();
		parameter.setId(genId(map));
		parameter.setParamType((String) map.get("PARAM_TYPE"));
		parameter.setParamCode((String) map.get("PARAM_CODE"));
		parameter.setParamValue((String) map.get("PARAM_VALUE"));
		return parameter;
	}
	private String genId(Map map) {
		String id = (String) map.get("PARAM_TYPE") + ";";
		id += (String) map.get("PARAM_CODE");
		return id;
	}

}
