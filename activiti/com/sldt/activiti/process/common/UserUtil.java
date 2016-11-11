package com.sldt.activiti.process.common;

import org.apache.shiro.SecurityUtils;

/**
 * 登陆用户信息，将来在session中获取，不可以做成静态的工具类
 * @author chensongwei
 * @datetime 2015年11月19日 上午9:59:12
 */
public class UserUtil {

	public static String getUserId(){
		return (String)SecurityUtils.getSubject().getPrincipal();
		//(String) SecurityUtils.getSubject().getSession().getAttribute("userId")
//		return "admin";
	}
	
	public static String getUserName(){
		//(String) SecurityUtils.getSubject().getSession().getAttribute("userName")
		return "admin";
	}
	
	public static String getCurrentUser(){
		return (String)SecurityUtils.getSubject().getPrincipal();
	}

}
