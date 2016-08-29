package com.sldt.dmc.ssm.teller.service;

import com.sldt.dmc.ssm.teller.bean.AuthLogInfoBean;

public interface IAuthenticateLogInfoService {
	/**
	 * 根据用户编号，查找最新登录审计日志信息
	 * @param userId
	 * @return
	 */
	public AuthLogInfoBean getAuthLogInfoByUserId(String userId);
}
