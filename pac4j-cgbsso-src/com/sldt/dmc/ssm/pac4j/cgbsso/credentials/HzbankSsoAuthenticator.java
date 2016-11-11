package com.sldt.dmc.ssm.pac4j.cgbsso.credentials;

import java.util.Map;

import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hzbank.sso.client.validation.Assertion;

/**
 * 杭州统一认证平台验证器实现类
 * @author chenbo
 * @date 2016-11-01
 */
public class HzbankSsoAuthenticator implements SsoAuthenticator {

	 protected static final Logger logger = LoggerFactory.getLogger(HzbankSsoAuthenticator.class);
	
	 public static final String VALIDATEMODE_PRODUCT="hzbanksso";
	 
	 private String validateMode;
		
	 @Override
	 public void validate(HzbankCredentials credentials) {
	    if (credentials == null) {
	            throwsException("No credentials");
	    }
	    String token = credentials.getToken();
	    Assertion assertion = credentials.getAssertion();
	    String validateMode = this.getValidateMode();
	    if(validateMode!=null && "test".equals(validateMode)){//未对接杭州统一认证模式情况
	    	if (CommonHelper.isNotBlank(token)){
	    		credentials.setUserId("admin");
	    	}
	    }else{
	    	 if (CommonHelper.isNotBlank(token)  && assertion!=null) {
		            throwsException("token and assertion: '" + token + "' does not exisit！");
		         String userId = "";
		         Map sso_map = assertion.getAttributes();
		         userId = sso_map.get("operno").toString();
		         credentials.setUserId(userId);
		    }else{
		    	 throwsException("must having token and assertion for hzbanksso login return！");
		    }
	    }
	   
	  }
	    
	protected void throwsException(final String message) {
	    logger.error(message);
	    throw new CredentialsException(message);
	}

	public String getValidateMode() {
		return validateMode;
	}

	public void setValidateMode(String validateMode) {
		this.validateMode = validateMode;
	}

}
