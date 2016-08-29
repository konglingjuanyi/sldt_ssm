package com.sldt.dmc.ssm.online.statics.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sldt.dmc.ssm.online.statics.util.OnlineStaticsUtil;

public class OnlineStaticsServlet extends HttpServlet{

	private static final long serialVersionUID = -1604748383848590581L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String unexpiredTgts = OnlineStaticsUtil.getOnlinePersons();
		response.setContentType("text/json; charset=utf-8"); 
		response.getWriter().write("{success:true,info:"+unexpiredTgts+"}");
	}
}
