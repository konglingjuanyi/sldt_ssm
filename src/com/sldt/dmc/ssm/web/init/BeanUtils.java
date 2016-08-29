/**
 * @author Administrator
 *
 */
package com.sldt.dmc.ssm.web.init;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.filter.DelegatingFilterProxy;

public class BeanUtils {
	private static final long serialVersionUID = -6586977215261542283L;
	private static Log logger = LogFactory.getLog(BeanUtils.class);
	//static ApplicationContext beanTool =  new ClassPathXmlApplicationContext("spring-applicationContext.xml");
	private static BeanFactory beanFactory=null;
//	public static ApplicationContext getApplicationContext(){
//		return beanTool;
//	}
	public static Object getBean(String beanName) {
		if(beanFactory==null){
			//未初始化 打印日志
			logger.info("----------------spring beanFactory 未初始化------------------");
		}
		return beanFactory.getBean(beanName); 
	}
	public static void setBeanFactory(BeanFactory beanFactory) {
		BeanUtils.beanFactory = beanFactory;
	}
	
}
