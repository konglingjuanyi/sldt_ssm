package com.sldt.dmc.ssm.pac4j.cgbsso.credentials;

/**
 * 杭州sso 统一认证的验证器接口
 * @author chenbo
 * @date 2016-11-01
 */
public interface SsoAuthenticator {
	
	public void validate(HzbankCredentials credentials);
}
