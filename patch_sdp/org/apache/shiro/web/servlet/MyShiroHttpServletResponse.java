package org.apache.shiro.web.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class MyShiroHttpServletResponse extends ShiroHttpServletResponse {
     public MyShiroHttpServletResponse(HttpServletResponse wrapped,
			ServletContext context, ShiroHttpServletRequest request) {
		super(wrapped, context, request);
	}

	@Override  
    public String encodeRedirectURL(String url) {
    	 //不调用父类实现，直接返回，不增加JSESSIIONID等内容
         // return super.encodeRedirectURL(url);   
          return url;
 
    }
}
