package com.sldt.dmc.ssm.pac4j.cgbsso.credentials;

import org.pac4j.core.credentials.Credentials;

import com.hzbank.sso.client.validation.Assertion;

public class HzbankCredentials extends Credentials{

	private static final long serialVersionUID = -3599943836014066415L;
	
	private String token;
	
	private String userId;

	private String userName;
	
	private Assertion assertion;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Assertion getAssertion() {
		return assertion;
	}

	public void setAssertion(Assertion assertion) {
		this.assertion = assertion;
	}

	public String getUserName() {
		return userName;
	}
	
}
