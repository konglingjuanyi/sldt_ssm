package com.sldt.dmc.sm.controller;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sldt.dmc.sm.bean.ExPriv;
import com.sldt.dmc.sm.service.ExPrivService;
import com.sldt.framework.common.PageResults;


@Controller
@RequestMapping("/exPriv.do")
public class ExPrivController {
 
	@Resource(name = "exPrivService") 
	private  ExPrivService exPrivService;
	
	@RequestMapping(params = "method=queryExPriv")
	@ResponseBody
	public List<ExPriv> queryExPriv(HttpServletResponse res,HttpServletRequest req){

		List<ExPriv> list=exPrivService.queryExPriv();
		return list;
	}
	

}
