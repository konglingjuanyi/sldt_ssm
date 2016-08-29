package com.sldt.dmc.ssm.parms.service.impl;

import java.util.List;

import com.sldt.dmc.ssm.parms.bean.Parameter;
import com.sldt.dmc.ssm.parms.dao.OnlineParmsDao;
import com.sldt.dmc.ssm.parms.service.OnlineParmsService;

public class OnlineParmsServiceImpl implements OnlineParmsService{
	private OnlineParmsDao onlineParmsDao;

	public OnlineParmsDao getOnlineParmsDao() {
		return onlineParmsDao;
	}

	public void setOnlineParmsDao(OnlineParmsDao onlineParmsDao) {
		this.onlineParmsDao = onlineParmsDao;
	}

	@Override
	public List<Parameter> findBySql() {
		StringBuffer sql = new StringBuffer("SELECT * FROM P_MS_PARAM_INFO T WHERE 1=1 and T.PARAM_TYPE='pms.online.statics' and T.PARAM_CODE='base.online.num'");
		return onlineParmsDao.getOnlineParms(sql.toString());
	}

}
