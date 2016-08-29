package com.sldt.dmc.ssm.parms.dao;

import java.util.List;

import com.sldt.dmc.ssm.parms.bean.Parameter;


public interface OnlineParmsDao {
	
	public List<Parameter> getOnlineParms(String sql);

}
