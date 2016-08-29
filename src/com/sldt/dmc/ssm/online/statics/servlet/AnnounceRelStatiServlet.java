package com.sldt.dmc.ssm.online.statics.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.sldt.dmc.ssm.parms.bean.Announce;
import com.sldt.dmc.ssm.parms.dao.IAnnounceDao;
import com.sldt.dmc.ssm.web.init.BeanUtils;

public class AnnounceRelStatiServlet extends HttpServlet{

	private static final long serialVersionUID = 5906388125817522684L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IAnnounceDao announceDao =(IAnnounceDao) BeanUtils.getBean("announceDao");
		List<Announce> al = announceDao.findNewsRelease();
		
		response.setContentType("text/json; charset=utf-8");
		JSONObject reusltObj=new JSONObject();
		reusltObj.put("al", al); 
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(reusltObj.toString());
	}
}
