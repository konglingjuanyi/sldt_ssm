package com.sldt.dmc.ssm.teller.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sldt.dmc.ssm.teller.bean.AuthLogInfoBean;
import com.sldt.dmc.ssm.teller.dao.IAuthenticateLogInfoDao;
import com.sldt.dmc.ssm.teller.service.IAuthenticateLogInfoService;
import com.sldt.dmc.ssm.teller.service.impl.AuthenticateLogInfoServiceImpl;

public class AuthenticateLogInfoServiceImpl implements IAuthenticateLogInfoService {

	private static Log logger = LogFactory.getLog(AuthenticateLogInfoServiceImpl.class);
	
	private IAuthenticateLogInfoDao authenticateLogInfoDao;
	
	@Override
	public AuthLogInfoBean getAuthLogInfoByUserId(String userId) {
		AuthLogInfoBean authLogInfoBean = null;
		if(userId!=null && !"".equals(userId)){
			String authLogInfo = authenticateLogInfoDao.getAuthenticateLogInfo(userId);
			if(authLogInfo!=null && !"".equals(authLogInfo)){
				authLogInfoBean = new AuthLogInfoBean();
				String[] msg = authLogInfo.split("##");
				authLogInfoBean.setUserId(userId);
				authLogInfoBean.setErrorCode(msg[0]);
				authLogInfoBean.setErrorMsg(msg[1]);
				authLogInfoBean.setPwdmodFlag(msg[2]);
				authLogInfoBean.setPwdLimit(msg[3]);
			}
		}
		return authLogInfoBean;
	}

	public IAuthenticateLogInfoDao getAuthenticateLogInfoDao() {
		return authenticateLogInfoDao;
	}

	public void setAuthenticateLogInfoDao(
			IAuthenticateLogInfoDao authenticateLogInfoDao) {
		this.authenticateLogInfoDao = authenticateLogInfoDao;
	}
}
