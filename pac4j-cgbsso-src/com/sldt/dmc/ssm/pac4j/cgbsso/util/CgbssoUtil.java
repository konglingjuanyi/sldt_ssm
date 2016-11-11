package com.sldt.dmc.ssm.pac4j.cgbsso.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CgbssoUtil {
	public static final String CGB_SSO_COOKIE_USERNAME="CSC_USERNAME";
	
	public static String getSSOLoginedUser(HttpServletRequest req)
	  {
	    String publicCookieValue;
	    try
	    {
	      publicCookieValue = CookieUtil.getCookieValue(req, CGB_SSO_COOKIE_USERNAME);
	    }
	    catch (Exception e)
	    {
	      return null;
	    }
	    if (publicCookieValue == null) {
	      return null;
	    }
	    //将获取的Cookie解密
	    String username=com.sldt.sdp.cas.util.CasCgbSsoCookieMgr.decode(publicCookieValue);
	    return username;
	    /*
	    String[] keyValues = publicCookieValue.split(",");
	    String identity = null;
	    String expires = null;
	    for (int i = 0; i < keyValues.length; i++)
	    {
	      String[] keyValue = keyValues[i].split(":");
	      if ("identity".equals(keyValue[0])) {
	        identity = keyValue[1];
	      } else if ("expires".equals(keyValue[0])) {
	        expires = keyValue[1];
	      }
	    }
	    if ((expires != null) && (System.currentTimeMillis() < Long.parseLong(expires))) {
	      return identity;
	    }
	 
	    return null; 
	      */
	  }
	public static boolean removeSSOLoginedUser(
			HttpServletRequest req,
			HttpServletResponse resp)
	  {
		Cookie cookie=CookieUtil.getCookie(req, CGB_SSO_COOKIE_USERNAME);
		if(cookie!=null){
			CookieUtil.deleteCookie(req, resp, cookie);
		}
		return true;
	  }
	/**
	 * 设置SSO登录Cookie
	 * @param req
	 * @param response
	 * @param value
	 * @return
	 */
	public static boolean setSSOLoginedUser(
			HttpServletRequest req,
			HttpServletResponse resp, String path,boolean isSecure,int maxAge,String value)
	  {
			String encUsername="";
			if(value==null||value.length()==0){
				encUsername="";
			}else{
				encUsername=com.sldt.sdp.cas.util.CasCgbSsoCookieMgr.encode(value);
			}
		  //Cookie参数应该来源于配置，适应UAT和生产环境的差异
	      //String path="/cas";
	      //boolean isSecure=false;
	      //int maxAge=60*30;//30秒有效
		  CookieUtil.setCookie(req,resp,CGB_SSO_COOKIE_USERNAME,encUsername,path,isSecure,maxAge);
		  return true;
	  }
}
