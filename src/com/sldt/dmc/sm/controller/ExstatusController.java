package com.sldt.dmc.sm.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sldt.dmc.sm.service.ExstatusService;
import com.sldt.framework.common.MsgVo;

@Controller
@RequestMapping("/isDapSts.do")
public class ExstatusController {

	@Resource(name = "statusService") 
	private  ExstatusService statusService;
	
	@ResponseBody
	@RequestMapping(params = "method=queryStatus")
	public String queryStatus(HttpServletResponse res,HttpServletRequest req){

		String nodeId = req.getParameter("nodeId"); 
		
		String IsDapSts =statusService.queryStatus(nodeId);
		return IsDapSts;
	}
	
	@ResponseBody
	@RequestMapping(params = "method=updateStatus")
	public MsgVo updateStatus(HttpServletResponse res,HttpServletRequest req){
		MsgVo msg = new MsgVo();
		String orgId = req.getParameter("nodeId");
		String status = req.getParameter("status"); 
		
		int a = statusService.updateStatus(orgId, status);
		if(a == 1){
			msg.setCode("1");
			msg.setMessage("修改成功！");	
		}else {
			msg.setCode("0");
			msg.setMessage("修改出错，请联系管理人员！");	
		}
		return msg;
	}
}
