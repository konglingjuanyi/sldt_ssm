package com.sldt.dmc.ssm.web.init;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;


public class SsmSpringIntegrationBean implements BeanFactoryAware,InitializingBean,ServletContextAware {
    /** A logger for this class */
    private static final Logger logger = LoggerFactory.getLogger( SsmSpringIntegrationBean.class );
    
	private BeanFactory beanFactory=null;
	private ServletContext servletContext=null;

	
	
	/**
	 * 保留Spring的BeanFactory
	 */
	public void setBeanFactory(BeanFactory bf) throws BeansException {
		this.beanFactory=bf;
		BeanUtils.setBeanFactory(bf);
	}
	
	/**
	 * 全局WEB应用部署全路径
	 */
	public static String gAppRealPath=null;
	/**
	 * 计算WEB应用部署路径全路径
	 * @return
	 */
	public String getAppRealPath(){
		if(gAppRealPath==null){
			//计算WEB应用部署路径全路径
			String appRealPath=servletContext.getRealPath("/");
			appRealPath=appRealPath.replaceAll("\\\\", "/");
			if(appRealPath.endsWith("/")){
				appRealPath=appRealPath.substring(0, appRealPath.length()-1);
			}
			logger.info("获取realpath=>"+appRealPath);
			//保持到全局变量
			gAppRealPath=appRealPath;
		}
		return gAppRealPath;
	}
	

	/**
	 * 属性配置完成后，完成属性参数调整
	 */
	public void afterPropertiesSet() throws Exception {
	
	}

	/**
	 * 保留ServletContext句柄
	 */
	public void setServletContext(ServletContext sc) {
		this.servletContext=sc;
	}
	
}
