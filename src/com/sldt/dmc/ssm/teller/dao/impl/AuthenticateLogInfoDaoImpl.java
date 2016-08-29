package com.sldt.dmc.ssm.teller.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sldt.dmc.ssm.teller.dao.IAuthenticateLogInfoDao;
import com.sldt.dmc.ssm.teller.dao.impl.AuthenticateLogInfoDaoImpl;

public class AuthenticateLogInfoDaoImpl implements IAuthenticateLogInfoDao {

    private static Log logger = LogFactory.getLog(AuthenticateLogInfoDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getAuthenticateLogInfo(String userId) {
		String logInfo = "";
		//db2 
		String sql = "SELECT T.AUD_RESOURCE,T.AUD_USER FROM P_DS_AUDIT_TRAIL T WHERE T.AUD_USER='"+userId+"' ORDER BY T.AUD_DATE DESC  FETCH FIRST 1 ROWS ONLY ";
		//for oracle
		//String sql = "select * from (SELECT T.AUD_RESOURCE,T.AUD_USER FROM P_DS_AUDIT_TRAIL T WHERE T.AUD_USER='"+userId+"' ORDER BY T.AUD_DATE DESC) where rownum <2";
		List<Map<String, Object>> maps = null;
		try{
			maps =getJdbcTemplate().queryForList(sql);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查找用户【"+userId+"】登录日志不存在："+e.getMessage());
			return logInfo;
		}
		if(maps!=null && maps.size()>0){
			 Map map = null;
			 map = maps.get(0);
			 logInfo = (String) map.get("AUD_RESOURCE");
		}
		return logInfo;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
