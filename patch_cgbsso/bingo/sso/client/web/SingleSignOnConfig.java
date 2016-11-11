package bingo.sso.client.web;

import bingo.sso.client.web.utils.HttpUtil;
import bingo.sso.client.web.utils.Signature;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class SingleSignOnConfig
  implements ISingleSignOnConfig
{
  public static final String CONFIG_SSO_BASE_ENDPOINT = "ssoBaseEndpoint";
  public static final String CONFIG_SSO_DIRECT_BASE_ENDPOINT = "ssoDirectBaseEndpoint";
  public static final String CONFIG_SSO_INDIRECT_BASE_ENDPOINT_MAP = "ssoIndirectBaseEndpointMap";
  public static final String CONFIG_SSO_SERVLET_PATH_BASE = "ssoServletPathBase";
  public static final String CONFIG_SSO_LOGIN_ENDPOINT = "ssoLoginEndpoint";
  public static final String CONFIG_SSO_LOGOUT_ENDPOINT = "ssoLogoutEndpoint";
  public static final String CONFIG_SSO_CHECK_AUTHENTICATION_ENDPOINT = "ssoCheckAuthenticationEndpoint";
  public static final String CONFIG_SSO_AUTH_TYPE = "ssoAuthType";
  public static final String CONFIG_SSO_AUTH_TYPE_PARAMETER_NAME = "ssoAuthTypeParameterName";
  public static final String CONFIG_SSO_COOKIE_SUFFIX = "ssoCookieSuffix";
  public static final String CONFIG_CLIENT_PRIVATE_KEY = "clientPrivateKey";
  public static final String CONFIG_CLIENT_ID = "clientId";
  public static final String CONFIG_CLIENT_SECRET = "clientSecret";
  public static final String CONFIG_LOGIN_BACK_ENDPOINT = "loginBackEndpoint";
  public static final String CONFIG_LOGOUT_BACK_ENDPOINT = "logoutBackEndpoint";
  public static final String CONFIG_CLIENT_LOGOUT_ENDPOINT = "clientLogoutEndpoint";
  private String ssoBaseEndpoint = null;
  private String ssoDirectBaseEndpoint = null;
  private Map ssoIndirectBaseEndpointMap = null;
  private String ssoServletPathBase = "/v2";
  private String ssoLoginEndpoint = null;
  private String ssoLogoutEndpoint = null;
  private String ssoCheckAuthenticationEndpoint = null;
  private String ssoAuthType = null;
  private String ssoAuthTypeParameterName = "openid.ex.auth_type";
  private String ssoCookieSuffix = null;
  private PrivateKey privateKey = null;
  private String clientId = null;
  private String clientSecret = null;
  private String loginBackEndpoint = null;
  private String logoutBackEndpoint = null;
  private String clientLogoutEndpoint = null;
  
  //////////////////////////////////////////////////////////////////
  //zzg 20141029增加F5判断参数
  private boolean isForceUseHttpsRedirect=false;
  private String f5OriginalClientIpHttpHeaderKey="X-Forwarded-For";
  private String f5OriginalClientIpHttpHeaderKeyEx="X-Remote-Addr";

  public boolean isForceUseHttpsRedirect() {
	return isForceUseHttpsRedirect;
  }
	
  public void setForceUseHttpsRedirect(boolean isForceUseHttpsRedirect) {
		this.isForceUseHttpsRedirect = isForceUseHttpsRedirect;
  }
	
  public String getF5OriginalClientIpHttpHeaderKey() {
		return f5OriginalClientIpHttpHeaderKey;
  }
	
  public void setF5OriginalClientIpHttpHeaderKey(
			String f5OriginalClientIpHttpHeaderKey) {
		this.f5OriginalClientIpHttpHeaderKey = f5OriginalClientIpHttpHeaderKey;
  }
	
  public String getF5OriginalClientIpHttpHeaderKeyEx() {
		return f5OriginalClientIpHttpHeaderKeyEx;
  }
	
  public void setF5OriginalClientIpHttpHeaderKeyEx(
			String f5OriginalClientIpHttpHeaderKeyEx) {
		this.f5OriginalClientIpHttpHeaderKeyEx = f5OriginalClientIpHttpHeaderKeyEx;
  }
  /////////////////////////////////////////////////////////////
  
  
  
public void initParam(Map conf)
  {
    String url = (String)conf.get("ssoBaseEndpoint");
    if ((url != null) && (!"".equals(url))) {
      this.ssoBaseEndpoint = url;
    }
    url = (String)conf.get("ssoDirectBaseEndpoint");
    if ((url != null) && (!"".equals(url))) {
      this.ssoDirectBaseEndpoint = url;
    }
    url = (String)conf.get("ssoIndirectBaseEndpointMap");
    if ((url != null) && (!"".equals(url)))
    {
      this.ssoIndirectBaseEndpointMap = new HashMap();
      String[] maps = url.replaceAll("\\s*|\t|\r|\n", "").split(";");
      for (int i = 0; i < maps.length; i++)
      {
        String[] map = maps[i].split("->");
        this.ssoIndirectBaseEndpointMap.put(map[0], map[1]);
      }
    }
    url = (String)conf.get("ssoServletPathBase");
    if ((url != null) && (!"".equals(url))) {
      this.ssoServletPathBase = url;
    }
    String url1 = (String)conf.get("ssoLoginEndpoint");
    if ((url1 != null) && (!"".equals(url1))) {
      this.ssoLoginEndpoint = url1;
    }
    String url3 = (String)conf.get("ssoLogoutEndpoint");
    if ((url3 != null) && (!"".equals(url3))) {
      this.ssoLogoutEndpoint = url3;
    }
    String url4 = (String)conf.get("ssoCheckAuthenticationEndpoint");
    if ((url4 != null) && (!"".equals(url4))) {
      this.ssoCheckAuthenticationEndpoint = url4;
    }
    String authType = (String)conf.get("ssoAuthType");
    if ((authType != null) && (!"".equals(authType))) {
      this.ssoAuthType = authType;
    }
    String authTypeParameterName = (String)conf.get("ssoAuthTypeParameterName");
    if ((authTypeParameterName != null) && (!"".equals(authTypeParameterName))) {
      this.ssoAuthTypeParameterName = authTypeParameterName;
    }
    String privateKeyStr = (String)conf.get("clientPrivateKey");
    if ((privateKeyStr != null) && (!"".equals(privateKeyStr))) {
      this.privateKey = Signature.decodePrivateKey(privateKeyStr.replaceAll(" ", "").replaceAll("\\t", ""));
    }
    String clientIdP = (String)conf.get("clientId");
    if ((clientIdP != null) && (!"".equals(clientIdP))) {
      this.clientId = clientIdP;
    }
    String clientSecretP = (String)conf.get("clientSecret");
    if ((clientSecretP != null) && (!"".equals(clientSecretP))) {
      this.clientSecret = clientSecretP;
    }
    String ssoCookieSuffixP = (String)conf.get("ssoCookieSuffix");
    if ((ssoCookieSuffixP != null) && (!"".equals(ssoCookieSuffixP))) {
      this.ssoCookieSuffix = ssoCookieSuffixP;
    }
    
    /////////////////////////////////////////////////
    //设置F5识别参数
    String isForceUseHttpsRedirectP = (String)conf.get("isForceUseHttpsRedirect");
    if ((isForceUseHttpsRedirectP != null) && (!"".equals(isForceUseHttpsRedirectP))) {
    	if("true".equalsIgnoreCase(isForceUseHttpsRedirectP)
    	||"t".equalsIgnoreCase(isForceUseHttpsRedirectP)
    	||"1".equalsIgnoreCase(isForceUseHttpsRedirectP)
    	||"yes".equalsIgnoreCase(isForceUseHttpsRedirectP)
    	||"y".equalsIgnoreCase(isForceUseHttpsRedirectP)
    	){
    		 this.isForceUseHttpsRedirect = true;
    	}
    }
    String f5OriginalClientIpHttpHeaderKeyP = (String)conf.get("f5OriginalClientIpHttpHeaderKey");
    if ((f5OriginalClientIpHttpHeaderKeyP != null) && (!"".equals(f5OriginalClientIpHttpHeaderKeyP))) {
      this.f5OriginalClientIpHttpHeaderKey = f5OriginalClientIpHttpHeaderKeyP;
    }
    String f5OriginalClientIpHttpHeaderKeyExP = (String)conf.get("f5OriginalClientIpHttpHeaderKeyEx");
    if ((f5OriginalClientIpHttpHeaderKeyExP != null) && (!"".equals(f5OriginalClientIpHttpHeaderKeyExP))) {
      this.f5OriginalClientIpHttpHeaderKeyEx = f5OriginalClientIpHttpHeaderKeyExP;
    }
    
  }
  
  public String getSSOLoginEndpoint(HttpServletRequest req)
  {
    if ((this.ssoLoginEndpoint != null) && (!"".equals(this.ssoLoginEndpoint))) {
      return HttpUtil.convertFullPathUrl(req, this.ssoLoginEndpoint);
    }
    return HttpUtil.convertFullPathUrl(req, parseSSOBaseEndpoint(req) + this.ssoServletPathBase);
  }
  
  public String getLoginBackEndpoint(HttpServletRequest req)
  {
    String contextPath = "/".equals(req.getContextPath()) ? "" : req.getContextPath();
    if ((!this.loginBackEndpoint.startsWith(contextPath)) && (!this.loginBackEndpoint.startsWith("http")) && (!this.loginBackEndpoint.startsWith("HTTP"))) {
      return HttpUtil.convertFullPathUrl(req, contextPath + this.loginBackEndpoint);
    }
    return HttpUtil.convertFullPathUrl(req, this.loginBackEndpoint);
  }
  
  public String getLogoutBackEndpoint(HttpServletRequest req)
  {
    String contextPath = "/".equals(req.getContextPath()) ? "" : req.getContextPath();
    if ((!this.logoutBackEndpoint.startsWith(contextPath)) && (!this.logoutBackEndpoint.startsWith("http")) && (!this.logoutBackEndpoint.startsWith("HTTP"))) {
      return HttpUtil.convertFullPathUrl(req, contextPath + this.logoutBackEndpoint);
    }
    return HttpUtil.convertFullPathUrl(req, this.logoutBackEndpoint);
  }
  
  public String getSSOLogoutEndpoint(HttpServletRequest req)
  {
    if ((this.ssoLogoutEndpoint != null) && (!"".equals(this.ssoLogoutEndpoint))) {
      return HttpUtil.convertFullPathUrl(req, this.ssoLogoutEndpoint);
    }
    return HttpUtil.convertFullPathUrl(req, parseSSOBaseEndpoint(req) + this.ssoServletPathBase);
  }
  
  public String getClientId(HttpServletRequest req)
  {
    return this.clientId;
  }
  
  public String getClientSecret(HttpServletRequest req)
  {
    return this.clientSecret;
  }
  
  public String getSSOCheckAuthenticationEndpoint(HttpServletRequest req)
  {
    if ((this.ssoCheckAuthenticationEndpoint != null) && (!"".equals(this.ssoCheckAuthenticationEndpoint))) {
      return HttpUtil.convertFullPathUrl(req, this.ssoCheckAuthenticationEndpoint);
    }
    if ((this.ssoDirectBaseEndpoint != null) && (!"".equals(this.ssoDirectBaseEndpoint))) {
      return HttpUtil.convertFullPathUrl(req, this.ssoDirectBaseEndpoint + this.ssoServletPathBase);
    }
    return HttpUtil.convertFullPathUrl(req, parseSSOBaseEndpoint(req) + this.ssoServletPathBase);
  }
  
  public void setSsoBaseEndpoint(String ssoBaseEndpoint)
  {
    this.ssoBaseEndpoint = ssoBaseEndpoint;
  }
  
  public void setSsoLoginEndpoint(String ssoLoginEndpoint)
  {
    this.ssoLoginEndpoint = ssoLoginEndpoint;
  }
  
  public void setSsoLogoutEndpoint(String ssoLogoutEndpoint)
  {
    this.ssoLogoutEndpoint = ssoLogoutEndpoint;
  }
  
  public void setSsoServiceTicketValidateEndpoint(String ssoServiceTicketValidateEndpoint)
  {
    this.ssoCheckAuthenticationEndpoint = ssoServiceTicketValidateEndpoint;
  }
  
  public void setSsoAuthType(String ssoAuthType)
  {
    this.ssoAuthType = ssoAuthType;
  }
  
  public void setSsoAuthTypeParameterName(String ssoAuthTypeParameterName)
  {
    this.ssoAuthTypeParameterName = ssoAuthTypeParameterName;
  }
  
  public void setSsoCookieSuffix(String ssoCookieSuffix)
  {
    this.ssoCookieSuffix = ssoCookieSuffix;
  }
  
  public void setPrivateKey(PrivateKey privateKey)
  {
    this.privateKey = privateKey;
  }
  
  public void setClientId(String clientId)
  {
    this.clientId = clientId;
  }
  
  public void setClientSecret(String clientSecret)
  {
    this.clientSecret = clientSecret;
  }
  
  public void setLoginBackEndpoint(String loginBackEndpoint)
  {
    this.loginBackEndpoint = loginBackEndpoint;
  }
  
  public void setSsoIndirectBaseEndpointMap(Map ssoIndirectBaseEndpointMap)
  {
    this.ssoIndirectBaseEndpointMap = ssoIndirectBaseEndpointMap;
  }
  
  protected String parseSSOBaseEndpoint(HttpServletRequest req)
  {
    if ((this.ssoIndirectBaseEndpointMap != null) && (!this.ssoIndirectBaseEndpointMap.isEmpty()))
    {
      String serverUrl = HttpUtil.getServerUrl(req);
      return (String)this.ssoIndirectBaseEndpointMap.get(serverUrl);
    }
    return this.ssoBaseEndpoint;
  }
  
  public String getSSOAuthType(HttpServletRequest req)
  {
    return this.ssoAuthType;
  }
  
  public String getSSOAuthTypeParameterName(HttpServletRequest req)
  {
    return this.ssoAuthTypeParameterName;
  }
  
  public void setLogoutBackEndpoint(String logoutBackEndpoint)
  {
    this.logoutBackEndpoint = logoutBackEndpoint;
  }
  
  public String getClientLogoutEndpoint(HttpServletRequest req)
  {
    String contextPath = "/".equals(req.getContextPath()) ? "" : req.getContextPath();
    if ((!this.clientLogoutEndpoint.startsWith(contextPath)) && (!this.clientLogoutEndpoint.startsWith("http")) && (!this.clientLogoutEndpoint.startsWith("HTTP"))) {
      return HttpUtil.convertFullPathUrl(req, contextPath + this.clientLogoutEndpoint);
    }
    return HttpUtil.convertFullPathUrl(req, this.clientLogoutEndpoint);
  }
  
  public void setClientLogoutEndpoint(String clientLogoutEndpoint)
  {
    this.clientLogoutEndpoint = clientLogoutEndpoint;
  }
  
  public String getSSOBaseEndpoint(HttpServletRequest req)
  {
    return parseSSOBaseEndpoint(req);
  }
}
