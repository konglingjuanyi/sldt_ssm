package bingo.sso.client.web;

import bingo.sso.client.web.utils.HttpUtil;
import bingo.sso.client.web.utils.OpenIdUtils;
import bingo.sso.client.web.utils.URLBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class AbstractSingleSignOnServlet
  extends HttpServlet
{
  private static final long serialVersionUID = 7316356393964594055L;
  protected Map initParam = new HashMap();
  protected String servletName;
  
  public void setInitParam(Map initParam)
  {
    this.initParam = initParam;
  }
  
  public static AbstractSingleSignOnServlet get(ServletContext context)
  {
    return (AbstractSingleSignOnServlet)context.getAttribute(AbstractSingleSignOnServlet.class.getName());
  }
  
  public final void init(ServletConfig config)
    throws ServletException
  {
    this.servletName = config.getServletName();
    initSingleSignOnConfig(config);
  }
  
  protected void initSingleSignOnConfig(ServletConfig config)
  {
    SingleSignOnConfig singleSignOnConfig = new SingleSignOnConfig();
    singleSignOnConfig.setLoginBackEndpoint("/" + config.getServletName());
    singleSignOnConfig.setLogoutBackEndpoint("/" + config.getServletName());
    singleSignOnConfig.setClientLogoutEndpoint("/" + config.getServletName() + "?" + "openid.mode" + "=" + "logout");
    
    Map params = new HashMap();
    

    Enumeration names = config.getInitParameterNames();
    while (names.hasMoreElements())
    {
      String name = (String)names.nextElement();
      params.put(name, config.getInitParameter(name));
    }
    Iterator it = this.initParam.keySet().iterator();
    while (it.hasNext())
    {
      Object key = it.next();
      params.put(key, this.initParam.get(key));
    }
    singleSignOnConfig.initParam(params);
    
    SingleSignOnConfigHolder.set(singleSignOnConfig);
  }
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    doService(req, resp);
  }
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    doService(req, resp);
  }
  
  protected void doService(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException
  {
    try
    {
      String mode = req.getParameter("openid.mode");
      if ("id_res".equals(mode))
      {
        doAuthentication(req, resp);
      }
      else if ("setup_needed".equals(mode))
      {
        onSetupNeeded(req, resp, parseReturnUrl(req));
      }
      else if ("error".equals(mode))
      {
        String error = req.getParameter("openid.error");
        doError(req, resp, new SingleSignOnException(error));
      }
      else if ("logout".equals(mode))
      {
        doLogout(req, resp);
      }
      else if ("logout_res".equals(mode))
      {
        doLogoutBack(req, resp);
      }
      else
      {
        String path = req.getRequestURI();
        if (path.endsWith("/")) {
          path = path.substring(0, path.length() - 1);
        }
        if (path.endsWith("/login")) {
          doLogin(req, resp, false);
        } else if (path.endsWith("/islogin")) {
          doLogin(req, resp, true);
        } else if (path.endsWith("/login/endpoint")) {
          doLoginEndpoint(req, resp);
        } else if (path.endsWith("/login/serviceticket")) {
          doServiceTicketLogin(req, resp);
        } else if (path.endsWith("/logout")) {
          doLogoutByUser(req, resp);
        } else if (path.endsWith("/windowclose")) {
          resp.getWriter().write("<script>window.onload=function(){window.close()}</script>");
        } else {
          resp.setStatus(404);
        }
      }
    }
    catch (Exception e)
    {
      throw new ServletException(e);
    }
  }
  
  protected void doLogin(HttpServletRequest req, HttpServletResponse resp, boolean immediate)
    throws Exception
  {
    String returnUrl = parseReturnUrl(req);
    String loginUrl = SingleSignOnContext.getSSOLoginEndpoint(req, returnUrl, immediate);
    Map parameterData = buildLoginParameterData(req);
    URLBuilder loginUrlBuilder = new URLBuilder(loginUrl, parameterData);
    
    //zzg 20141029修改302重定向为JS重定向
    //resp.sendRedirect(loginUrlBuilder.build());
    responseSendRedirect(req,resp,loginUrlBuilder.build());
  }
  
  protected void doAuthentication(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      StringBuffer result = new StringBuffer();
      
      Authentication authentication = checkAuthentication(request, result);
      String returnUrl = parseReturnUrl(request);
      onSuccessSignIn(request, response, authentication);
      onSuccessReturn(request,response, returnUrl);
    }
    catch (SingleSignOnException e)
    {
      onFailSignIn(request, response, e);
    }
    catch (Exception e)
    {
      onFailSignIn(request, response, new SingleSignOnException(e));
    }
  }
  
  protected void doServiceTicketLogin(HttpServletRequest req, HttpServletResponse resp)
    throws Exception
  {
    StringBuffer result = new StringBuffer();
    try
    {
      Authentication authentication = checkAuthentication(req, result);
      
      onSuccessSignIn(req, resp, authentication);
      

      resp.setContentType("text/plain");
      resp.setCharacterEncoding("UTF-8");
      resp.getOutputStream().write(result.toString().getBytes("UTF-8"));
    }
    catch (Exception e)
    {
      resp.setContentType("text/plain");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(500);
      resp.getOutputStream().write(result.toString().getBytes("UTF-8"));
    }
  }
  
  protected void doLoginEndpoint(HttpServletRequest req, HttpServletResponse resp)
    throws Exception
  {
    try
    {
      String returnUrl = parseReturnUrl(req);
      String loginUrl = SingleSignOnContext.getSSOLoginEndpoint(req, returnUrl, false);
      Map parameterData = buildLoginParameterData(req);
      URLBuilder loginUrlBuilder = new URLBuilder(loginUrl, parameterData);
      StringBuilder out = new StringBuilder();
      out.append("ex.login_endpoint").append(":").append(loginUrlBuilder.build()).append("\n");
      
      resp.setContentType("text/plain");
      resp.setCharacterEncoding("UTF-8");
      resp.getOutputStream().write(out.toString().getBytes("UTF-8"));
    }
    catch (Exception e)
    {
      StringBuilder out = new StringBuilder();
      out.append("error").append(":").append(e.getMessage()).append("\n");
      
      resp.setContentType("text/plain");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(500);
      resp.getOutputStream().write(out.toString().getBytes("UTF-8"));
    }
  }
  
  protected void doLogout(HttpServletRequest req, HttpServletResponse response)
    throws Exception
  {
    req.getSession().invalidate();
  }
  
  protected void doLogoutBack(HttpServletRequest req, HttpServletResponse response)
    throws Exception
  {
    String returnUrl = parseReturnUrl(req);
    responseSendRedirect(req,response,response.encodeRedirectURL(returnUrl));
  }
  
  protected void doLogoutByUser(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String returnUrl = parseReturnUrl(request);
    String logoutBackUrl = SingleSignOnConfigHolder.get().getLogoutBackEndpoint(request);
    logoutBackUrl = logoutBackUrl + (logoutBackUrl.indexOf('?') > 0 ? "&" : "?") + "return_url" + "=" + URLEncoder.encode(returnUrl, "UTF-8");
    
    StringBuffer logoutUrlBuf = new StringBuffer();
    String providerUrl = SingleSignOnConfigHolder.get().getSSOLogoutEndpoint(request);
    logoutUrlBuf.append(providerUrl).append(providerUrl.indexOf("?") >= 0 ? '&' : '?').append("openid.return_to").append("=").append(URLEncoder.encode(logoutBackUrl, "UTF-8")).append("&").append("openid.mode").append("=").append("logout");
    


    //zzg 20141029 修改302重定向为js重定向
    //response.sendRedirect(response.encodeRedirectURL(logoutUrlBuf.toString()));
    responseSendRedirect(request,response,response.encodeRedirectURL(logoutUrlBuf.toString()));
  }
  
  protected abstract void onSuccessSignIn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Authentication paramAuthentication)
    throws Exception;
  
  //zzg 20141029
  //增加了一个req参数
  protected void onSuccessReturn(HttpServletRequest req,HttpServletResponse resp, String returnUrl)
    throws Exception
  {
	  responseSendRedirect(req,resp,returnUrl);
  }
  
  protected void onFailSignIn(HttpServletRequest req, HttpServletResponse response, SingleSignOnException e)
    throws Exception
  {
    throw e;
  }
  
  protected abstract void onSetupNeeded(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString)
    throws Exception;
  
  protected String parseReturnUrl(HttpServletRequest request)
  {
    String returnUrl = request.getParameter("return_url");
    if (null == returnUrl) {
      returnUrl = (String)request.getAttribute(SingleSignOnContext.REQUEST_ATTRIBUTE_RETURN_URL);
    }
    if ((null == returnUrl) && ("popup".equals(request.getParameter("openid.ex.ui_mode")))) {
      returnUrl = this.servletName + "/windowclose";
    }
    if (null == returnUrl) {
      returnUrl = "/";
    }
    String contextPath = "/".equals(request.getContextPath()) ? "" : request.getContextPath();
    if (!returnUrl.startsWith("http"))
    {
      if (!returnUrl.startsWith("/")) {
        returnUrl = "/" + returnUrl;
      }
      returnUrl = HttpUtil.convertFullPathUrl(request, contextPath + returnUrl);
    }
    if (!returnUrl.startsWith(HttpUtil.getServerUrl(request) + contextPath)) {
      throw new RuntimeException("not allowed return url！");
    }
    return returnUrl;
  }
  
  protected void doError(HttpServletRequest request, HttpServletResponse response, Exception e)
    throws ServletException, IOException
  {
    throw new ServletException(e.getMessage(), e);
  }
  
  private Map buildLoginParameterData(HttpServletRequest req)
  {
    Map postData = new HashMap();
    
    Enumeration enumer = req.getParameterNames();
    while (enumer.hasMoreElements())
    {
      String name = (String)enumer.nextElement();
      if (!name.equalsIgnoreCase("return_url")) {
        postData.put(name, req.getParameter(name));
      }
    }
    return postData;
  }
  
  protected Authentication checkAuthentication(HttpServletRequest req, StringBuffer result)
  {
    try
    {
      Map params = new HashMap();
      params.put("openid.mode", "check_authentication");
      params.put("openid.ex.logout_url", SingleSignOnConfigHolder.get().getClientLogoutEndpoint(req));
      params.put("openid.ex.client_id", SingleSignOnConfigHolder.get().getClientId(req));
      params.put("openid.ex.client_secret", SingleSignOnConfigHolder.get().getClientSecret(req));
      Enumeration<String> enumeration = req.getParameterNames();
      while (enumeration.hasMoreElements())
      {
        String name = (String)enumeration.nextElement();
        if (!"openid.mode".equals(name)) {
          params.put(name, req.getParameter(name));
        }
      }
      parseExtendParams4CheckAuthentication(req, params);
      URLBuilder ub = new URLBuilder(SingleSignOnConfigHolder.get().getSSOCheckAuthenticationEndpoint(req), params);
      
      Map map = HttpUtil.request(ub.build(), "POST", "*/*", "", 10000);
      

      String content = HttpUtil.getContent(map);
      if ((content == null) || ("".equals(content))) {
        throw new SingleSignOnException("check_authentication response is null!");
      }
      result.append(content);
      
      int responseCode = HttpUtil.getResopnseCode(map);
      if (responseCode == 200)
      {
        Map returnData = OpenIdUtils.parseResult(content);
        String mode = (String)returnData.get("mode");
        if ("ok".equals(mode))
        {
          Authentication authentication = createAuthentication();
          authentication.setIdentity((String)returnData.get("identity"));
          parseExtendAttributes(authentication, returnData);
          return authentication;
        }
        if ("fail".equals(mode)) {
          throw new SingleSignOnException((String)returnData.get("error"));
        }
        throw new SingleSignOnException("Not support response mode :" + mode);
      }
      if ((responseCode == 400) || (responseCode == 500))
      {
        Map returnData = OpenIdUtils.parseResult(content);
        throw new SingleSignOnException((String)returnData.get("error"));
      }
      throw new SingleSignOnException("Bad response code: " + responseCode);
    }
    catch (SingleSignOnException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SingleSignOnException(e);
    }
  }
  
  protected void parseExtendParams4CheckAuthentication(HttpServletRequest req, Map params) {}
  
  protected Authentication createAuthentication()
  {
    return new Authentication();
  }
  
  protected void parseExtendAttributes(Authentication authentication, Map responseData) {}
  //zzg 20141029 支持f5环境下的客户端重定向
  //302重定向会被f5强制将http协议修改为https，引起混乱
  //本方法实现JS客户端页面跳转，避免F5干扰引起的相关问题
  protected void  responseSendRedirect(HttpServletRequest req,HttpServletResponse resp,String returnUrl) throws IOException{
	 // System.out.println("==debug===responseSendRedirect()"+returnUrl);
	  req.getSession().setAttribute("CGB_SSO_RedirectURL", returnUrl);
	  resp.sendRedirect("/ssm/redirect.jsp"); //通过页面跳转
  }
}
