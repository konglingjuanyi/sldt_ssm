package com.sldt.sdp.spring.web.init;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.session.HttpServletSession;

public class CasLogin404PatchFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		HttpSession session = request.getSession(false);
		
		
		String uri = request.getRequestURI();
		String queryStr = request.getQueryString();
		
		System.out.println("zzg---debug---20160413--uri:"+uri);
		System.out.println("zzg----debug---20160413---queryStr:"+queryStr);
		
		int idx_key = uri.indexOf("/login;JSESSIONID=");
		if(idx_key != -1){
			int idx_key_2 = uri.indexOf(";JSESSIONID=");
			String newUri = uri.substring(0, idx_key_2);
			String newRequestUrl = newUri + "?" + queryStr;
			if(queryStr != null){
				newRequestUrl = newUri + "?" + queryStr;
			}else{
				newRequestUrl = newUri;
			}
			System.out.println("zzg---debug---20160413---newRequestUrl:" 
					+ newRequestUrl);
			response.sendRedirect(newRequestUrl);
		}else{
			filterChain.doFilter(servletRequest, servletResponse);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
			
	}

}
