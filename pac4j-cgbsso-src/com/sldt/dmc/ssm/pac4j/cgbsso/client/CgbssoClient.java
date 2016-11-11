/*
  Copyright 2012 - 2013 zzg

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.sldt.dmc.ssm.pac4j.cgbsso.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.web.support.WebUtils;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.RequiresHttpAction;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;

import com.sldt.dmc.ssm.pac4j.cgbsso.credentials.SsoUsernameAuthenticator;
import com.sldt.dmc.ssm.pac4j.cgbsso.credentials.SsoUsernameCredentials;
import com.sldt.dmc.ssm.pac4j.cgbsso.profile.ProfileCreator;
import com.sldt.dmc.ssm.pac4j.cgbsso.profile.SsoProfile;
import com.sldt.dmc.ssm.pac4j.cgbsso.util.CgbssoUtil;

/**
 * This class is the client to authenticate users through HTTP form.
 * <p />
 * The login url of the form must be defined through the {@link #setLoginUrl(String)} method. For authentication, the user is redirected to
 * this login form. The username and password inputs must be posted on the callback url. Their names can be defined by using the
 * {@link #setUsernameParameter(String)} and {@link #setPasswordParameter(String)} methods.
 * <p />
 * It returns a {@link com.sldt.dmc.ssm.pac4j.cgbsso.profile.SsoProfile}.
 * 
 * @see com.sldt.dmc.ssm.pac4j.cgbsso.profile.SsoProfile
 * @author zzg
 * @since 1.4.0
 */
public class CgbssoClient extends BaseHttpClient {
	public static final String SSO_USER_NAME = "SSO_USER_NAME";
    private String loginUrl;
    
    public final static String ERROR_PARAMETER = "error";
    
    public final static String MISSING_FIELD_ERROR = "missing_field";
    
    public final static String DEFAULT_USERNAME_PARAMETER = "username";
    
    private String usernameParameter = DEFAULT_USERNAME_PARAMETER;
    
    public final static String DEFAULT_PASSWORD_PARAMETER = "password";
    
    private String passwordParameter = DEFAULT_PASSWORD_PARAMETER;
    
    public CgbssoClient() {
    }
    
    public CgbssoClient(final String loginUrl, final SsoUsernameAuthenticator usernamePasswordAuthenticator) {
        setLoginUrl(loginUrl);
        setSsoUsernameAuthenticator(usernamePasswordAuthenticator);
    }
    
    public CgbssoClient(final String loginUrl, final SsoUsernameAuthenticator usernamePasswordAuthenticator,
                      final ProfileCreator profileCreator) {
        this(loginUrl, usernamePasswordAuthenticator);
        setProfileCreator(profileCreator);
    }
    
    @Override
    protected BaseClient<SsoUsernameCredentials, SsoProfile> newClient() {
        final CgbssoClient newClient = new CgbssoClient();
        newClient.setLoginUrl(this.loginUrl);
        newClient.setUsernameParameter(this.usernameParameter);
        newClient.setPasswordParameter(this.passwordParameter);
        return newClient;
    }
    
    @Override
    protected void internalInit() {
        super.internalInit();
        CommonHelper.assertNotBlank("loginUrl", this.loginUrl);
    }
    
    @Override
    protected String retrieveRedirectionUrl(final WebContext context) {
        return this.loginUrl;
    }
    
    @Override
    protected SsoUsernameCredentials retrieveCredentials(final WebContext context) throws RequiresHttpAction {
      //  final String username = context.getRequestParameter(this.usernameParameter);
       // final String password = context.getRequestParameter(this.passwordParameter);
    	
    	if(context instanceof J2EContext){
	    	J2EContext j2eContext=(J2EContext)context;
			final HttpServletRequest request = j2eContext.getRequest();
			final HttpServletResponse response = j2eContext.getResponse();
	        //从Cookie中获取单点登录用户名
	        //final String username =(String)context.getSessionAttribute(SSO_USER_NAME);
	        final String username = CgbssoUtil.getSSOLoginedUser(request);
	        //从Cookie中获取密码信息，辅助验证username的合法性
	        //这里的密码后期将该为其辅助验证username的信息,如对username进行的签名算法组合。
	        final String password = "abc";
	        if (CommonHelper.isNotBlank(username) 
	        		&& CommonHelper.isNotBlank(password)) {
	        	
	        	 //在username有效的情况下，获取到后可以进行清理
	        	 //zzg 20141020 获取到后，直接去掉SSO
		        CgbssoUtil.removeSSOLoginedUser(request, response);
		        //CgbssoUtil.setSSOLoginedUser(request, response, "/ssm", false, -1, "");
		        
	            final SsoUsernameCredentials credentials = new SsoUsernameCredentials(username, password,
	                                                                                            getName());
	            logger.debug("usernamePasswordCredentials : {}", credentials);
	            try {
	                // validate credentials
	                this.ssoUsernameAuthenticator.validate(credentials);
	            } catch (final TechnicalException e) {
	                String redirectionUrl = CommonHelper.addParameter(this.loginUrl, this.usernameParameter, username);
	                String errorMessage = computeErrorMessage(e);
	                redirectionUrl = CommonHelper.addParameter(redirectionUrl, ERROR_PARAMETER, errorMessage);
	                logger.debug("redirectionUrl : {}", redirectionUrl);
	                final String message = "Credentials validation fails -> return to the form with error";
	                logger.error(message);
	                throw RequiresHttpAction.redirect(message, context, redirectionUrl);
	            }
	            
	            return credentials;
	        }
    	}
        String redirectionUrl = CommonHelper.addParameter(this.loginUrl, this.usernameParameter, "");
        redirectionUrl = CommonHelper.addParameter(redirectionUrl, ERROR_PARAMETER, MISSING_FIELD_ERROR);
        logger.debug("redirectionUrl : {}", redirectionUrl);
        final String message = "Username and password cannot be blank -> return to the form with error";
        logger.error(message);
        throw RequiresHttpAction.redirect(message, context, redirectionUrl);
    }
    
    /**
     * Return the error message depending on the thrown exception. Can be overriden for other message computation.
     * 
     * @param e
     * @return the error message
     */
    protected String computeErrorMessage(final TechnicalException e) {
        return e.getClass().getSimpleName();
    }
    
    public String getLoginUrl() {
        return this.loginUrl;
    }
    
    public void setLoginUrl(final String loginUrl) {
        this.loginUrl = loginUrl;
    }
    
    public String getUsernameParameter() {
        return this.usernameParameter;
    }
    
    public void setUsernameParameter(final String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }
    
    public String getPasswordParameter() {
        return this.passwordParameter;
    }
    
    public void setPasswordParameter(final String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }
    
    @Override
    public String toString() {
        return CommonHelper.toString(this.getClass(), "callbackUrl", this.callbackUrl, "name", getName(), "loginUrl",
                                     this.loginUrl, "usernameParameter", this.usernameParameter, "passwordParameter",
                                     this.passwordParameter, "usernamePasswordAuthenticator",
                                     getSsoUsernameAuthenticator(), "profileCreator", getProfileCreator());
    }
    
    @Override
    protected boolean isDirectRedirection() {
        return true;
    }
}
