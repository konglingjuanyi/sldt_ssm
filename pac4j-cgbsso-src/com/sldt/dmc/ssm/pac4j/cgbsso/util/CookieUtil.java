package com.sldt.dmc.ssm.pac4j.cgbsso.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

  public static Cookie getCookie(HttpServletRequest request, String name) {
    Cookie cookies[] = request.getCookies();
    if (cookies == null || name == null || name.length() == 0) {
      return null;
    }
    for (int i = 0; i < cookies.length; i++) {
      if (name.equals(cookies[i].getName())
         // && request.getServerName().equals(cookies[i].getDomain())
        ) {
        return cookies[i];
      }
    }
    return null;
  }
  public static String getCookieValue(HttpServletRequest request, String cookieName)
		    throws Exception
		  {
		    Cookie[] cookies = request.getCookies();
		    if (cookies == null) {
		      return null;
		    }
		    for (int i = 0; i < cookies.length; i++) {
		      if (cookies[i].getName().equals(cookieName)) {
		        return cookies[i].getValue();
		      }
		    }
		    return null;
	}

  public static void deleteCookie(HttpServletRequest request,
      HttpServletResponse response, Cookie cookie) {
    if (cookie != null) {
      cookie.setValue("");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }
  }

  public static void setCookie(HttpServletRequest request,
      HttpServletResponse response, String name, String value) {
    setCookie(request, response, name, value, 0x278d00);
  }
  public static void setCookie(
		  HttpServletRequest request,
	      HttpServletResponse response, 
	      String name,
	      String value,
	      String path,
	      boolean isSecure,
	      int maxAge
	      ) {
    Cookie cookie = new Cookie(name, value == null ? "" : value);
    cookie.setMaxAge(maxAge);
    cookie.setPath(path);
    cookie.setSecure(isSecure);//是否存储为安全的https协议,经过F5的应该配置为true,普通开发环境不需要配置为true
    response.addCookie(cookie);
  }
  public static void setCookie(HttpServletRequest request,
      HttpServletResponse response, String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value == null ? "" : value);
    cookie.setMaxAge(maxAge);
    cookie.setPath(getPath(request));
    response.addCookie(cookie);
  }

  private static String getPath(HttpServletRequest request) {
    String path = request.getContextPath();
    return (path == null || path.length()==0) ? "/" : path;
  }

}