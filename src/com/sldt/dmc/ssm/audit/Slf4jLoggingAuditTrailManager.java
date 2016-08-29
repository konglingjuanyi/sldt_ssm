package com.sldt.dmc.ssm.audit;

import com.github.inspektr.audit.AuditActionContext;
import com.github.inspektr.audit.AuditTrailManager;
import com.github.inspektr.audit.support.AbstractStringAuditTrailManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 记录用户登录审计日志到文件
 *  
 * @author zzg 2012-04-29
 * @version 1.0
 */
public final class Slf4jLoggingAuditTrailManager extends AbstractStringAuditTrailManager {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private String getAuditDateString(AuditActionContext auditActionContext){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(auditActionContext.getWhenActionWasPerformed());
    }
    public void record(final AuditActionContext auditActionContext) {
    	log.info("\n"
    		     +"=============================================================\n"
    			 +"用户: "+auditActionContext.getPrincipal().toString()  //WHO
				 +"\n审计资源: "+auditActionContext.getResourceOperatedUpon().toString() //WHAT
				 +"\n审计动作: "+auditActionContext.getActionPerformed() //ACTION
				 +"\n应用名称: "+auditActionContext.getApplicationCode() //APPLICATION
				 +"\n审计时间: "+getAuditDateString(auditActionContext) //WHEN
				 +"\n客户端IP: "+auditActionContext.getClientIpAddress()  //CLIENT IP ADDRESS
				 +"\n服务器端IP: "+auditActionContext.getServerIpAddress()     //SERVER IP ADDRESS	
				 +"\n=============================================================\n\n"
    	);
       //log.info(toString(auditActionContext));
    }
}