package com.sldt.dmc.ssm.pac4j.cgbsso.client;

import org.pac4j.core.client.BaseClient;
import org.pac4j.core.client.Protocol;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.RequiresHttpAction;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.CommonHelper;

import com.hzbank.sso.client.validation.Assertion;
import com.sldt.dmc.ssm.pac4j.cgbsso.credentials.HzbankCredentials;
import com.sldt.dmc.ssm.pac4j.cgbsso.credentials.HzbankSsoAuthenticator;

/**
 * 定制杭州银行版本的统一登录验证网关客户端
 * @author chenbo
 * @date 2016-11-01
 */
public class HzbankSsoClient  extends BaseClient<HzbankCredentials,CommonProfile> {

	 private String loginUrl;
	 
	 protected HzbankSsoAuthenticator hzbankSsoAuthenticator;
	 
	 public final static String ASSERTION = "_const_sso_assertion_";
	 
	 public final static String DEFAULT_TOKEN_PARAMETER = "token"; 
	 
	 public final static String ERROR_PARAMETER = "error";
	 
	 public final static String MISSING_FIELD_ERROR = "missing_field";
	
	  
    public String getLoginUrl() {
        return this.loginUrl;
    }
    
    public void setLoginUrl(final String loginUrl) {
        this.loginUrl = loginUrl;
    }
    
	public HzbankSsoAuthenticator getHzbankSsoAuthenticator() {
		return hzbankSsoAuthenticator;
	}

	public void setHzbankSsoAuthenticator(
			HzbankSsoAuthenticator hzbankSsoAuthenticator) {
		this.hzbankSsoAuthenticator = hzbankSsoAuthenticator;
	}

	@Override
	protected BaseClient<HzbankCredentials, CommonProfile> newClient() {
		  final HzbankSsoClient newClient = new HzbankSsoClient();
	      return newClient;
	}

	@Override
	protected boolean isDirectRedirection() {
		return true;
	}


	@Override
	protected String retrieveRedirectionUrl(WebContext context) {
		return this.loginUrl;
	}
	

	@Override
	protected HzbankCredentials retrieveCredentials(WebContext context)
			throws RequiresHttpAction {
	       final String token = context.getRequestParameter(DEFAULT_TOKEN_PARAMETER);
       
	       if (CommonHelper.isNotBlank(token)) {
	            final HzbankCredentials credentials = new HzbankCredentials();
	            credentials.setClientName(getName());
	            credentials.setToken(token);
	            if(context.getSessionAttribute(ASSERTION)!=null){
	 	    	   Assertion assertion = (Assertion)context.getSessionAttribute(ASSERTION); 
	 	    	   credentials.setAssertion(assertion);
	 	        }
	            logger.debug("HzbankCredentials : {}", credentials);
	            try { 
	                // validate credentials
	                this.hzbankSsoAuthenticator.validate(credentials);
	            } catch (final TechnicalException e) {
	                String errorMessage = computeErrorMessage(e);
	                String redirectionUrl = CommonHelper.addParameter(this.loginUrl, ERROR_PARAMETER, errorMessage);
	                logger.debug("redirectionUrl : {}", redirectionUrl);
	                final String message = "Credentials validation fails -> return to the form with error";
	                logger.error(message);
	                throw RequiresHttpAction.redirect(message, context, redirectionUrl);
	            }
	            
	            return credentials;
	        }
	        //验证失败后，跳转逻辑，如果需要可以修改这里
	        String redirectionUrl = CommonHelper.addParameter(this.loginUrl, ERROR_PARAMETER, MISSING_FIELD_ERROR);
	        logger.debug("redirectionUrl : {}", redirectionUrl);
	        final String message = "Username and password cannot be blank -> return to the form with error";
	        logger.error(message);
	        throw RequiresHttpAction.redirect(message, context, redirectionUrl);
	}

	@Override
	protected CommonProfile retrieveUserProfile(HzbankCredentials credentials) {
		  // create user profile
        final CommonProfile profile = new CommonProfile();
        String userId = credentials.getUserId();
        String userName = credentials.getUsername();
        profile.setId(userId);
        if(userName==null ||"".equals(userName)){
        	userName = userId;
        }
        profile.addAttribute(CommonProfile.USERNAME, userName);
        return profile;
	}
	

	@Override
	public Protocol getProtocol() {
		 return Protocol.HZBANKSSO;
	}
	

	@Override
	protected void internalInit() {
		 CommonHelper.assertNotBlank("callbackUrl", this.callbackUrl);
		 if (CommonHelper.isBlank(this.loginUrl)) {
		       throw new TechnicalException("casLoginUrl cannot be blank!!");
		 } 
	}
	
	
	protected String computeErrorMessage(final TechnicalException e) {
	    return e.getClass().getSimpleName();
	}

}
