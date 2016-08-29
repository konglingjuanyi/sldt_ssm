package com.sldt.dmc.ssm.parms.service;

import java.util.List;

import com.sldt.dmc.ssm.parms.bean.Parameter;


public interface OnlineParmsService {
	public List<Parameter> findBySql();

}
