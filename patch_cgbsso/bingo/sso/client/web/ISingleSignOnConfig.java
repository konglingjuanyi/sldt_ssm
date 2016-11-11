package bingo.sso.client.web;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISingleSignOnConfig
{
  public abstract void initParam(Map paramMap);
  
  public abstract String getSSOBaseEndpoint(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getSSOLoginEndpoint(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getLoginBackEndpoint(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getSSOLogoutEndpoint(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getClientLogoutEndpoint(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getLogoutBackEndpoint(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getSSOAuthType(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getSSOAuthTypeParameterName(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getClientId(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getClientSecret(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getSSOCheckAuthenticationEndpoint(HttpServletRequest paramHttpServletRequest);
  //zzg 20141029 F5识别参数
  public boolean isForceUseHttpsRedirect();
  public String getF5OriginalClientIpHttpHeaderKey();
  public String getF5OriginalClientIpHttpHeaderKeyEx();
  
}
