package com.sldt.dmc.ssm.audit.task;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sldt.dmc.ssm.audit.JdbcAuditTrailManager;
import com.sldt.dmc.ssm.web.init.BeanUtils;

public class AuditCleanTask implements Serializable {

	private static final long serialVersionUID = 6557760524407033694L;
	
	private  Log logger = LogFactory.getLog(this.getClass());

	public void auditClean(){
		logger.info("-------单点登录审计日志归档开始---------归档时间："+new Date());
		System.out.println("****************单点登录审计日志归档开始***********");
		JdbcAuditTrailManager auditManager = (JdbcAuditTrailManager) BeanUtils.getBean("auditManager");
		auditManager.clean();
		
		logger.info("-------单点登录审计日志归档结束--------");
	}
}
