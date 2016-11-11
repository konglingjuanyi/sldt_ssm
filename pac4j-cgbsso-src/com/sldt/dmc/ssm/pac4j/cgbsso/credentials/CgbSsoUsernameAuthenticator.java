package com.sldt.dmc.ssm.pac4j.cgbsso.credentials;

import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 广发SSO用户信息验证器
 * 该类用于验证SSO用户名是否为空，为空不合法，
 * 后期将添加是否在SSM系统中存在的用户判断。
 * @author zzg
 * @since 1.4.0
 */
public class CgbSsoUsernameAuthenticator implements SsoUsernameAuthenticator {
    
    protected static final Logger logger = LoggerFactory.getLogger(CgbSsoUsernameAuthenticator.class);
    
    public void validate(final SsoUsernameCredentials credentials) {
        if (credentials == null) {
            throwsException("No credential");
        }
        String username = credentials.getUsername();
        //String password = credentials.getPassword();
        if (!CommonHelper.isNotBlank(username) 
        	//&& CommonHelper.isNotBlank(password)
           // && CommonHelper.areNotEquals(username, password)
            ) {
            throwsException("Username : '" + username + "' does not match password");
        }
    }
    
    protected void throwsException(final String message) {
        logger.error(message);
        throw new CredentialsException(message);
    }
}
