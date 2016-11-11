package com.sldt.dmc.ssm.cgbsso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bingo.sso.client.web.AbstractSingleSignOnServlet;
import bingo.sso.client.web.Authentication;


public class SSOSingleSignOnServlet extends AbstractSingleSignOnServlet {

	/**  
	  * @Fields: serialVersionUID 
	  * @Todo: TODO 
	  */
	private static final long serialVersionUID = -7780663594400841967L;

	public static final String AUTH_KEY = SSOSingleSignOnServlet.class
			.getName() + "$AUTHENTICATION";
	
	public static final String SSO_USER_NAME = "SSO_USER_NAME";
	
	/* (non-Javadoc)  
	  * @param arg0
	  * @param arg1
	  * @param arg2
	  * @throws Exception  
	  * @see bingo.sso.client.web.AbstractSingleSignOnServlet#onSetupNeeded(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)  
	  */
	@Override
	protected void onSetupNeeded(HttpServletRequest request,
			HttpServletResponse response, String str) throws Exception {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)  
	  * @param arg0
	  * @param arg1
	  * @param arg2
	  * @throws Exception  
	  * @see bingo.sso.client.web.AbstractSingleSignOnServlet#onSuccessSignIn(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, bingo.sso.client.web.Authentication)  
	  */
	@Override
	protected void onSuccessSignIn(HttpServletRequest request,
			HttpServletResponse response, Authentication auth) throws Exception {
		
		String userId = auth.getIdentity().substring(auth.getIdentity().indexOf("\\") + 1);
		System.out.println("-------------单点登录成功的用户名----------------:"+userId);
		request.getSession().setAttribute(SSO_USER_NAME, userId);
		//根据单点登录的用户名称查询实际使用的柜员编号
		//去数据库查询柜员编号
		//将结果存入Session
		
	}
}
