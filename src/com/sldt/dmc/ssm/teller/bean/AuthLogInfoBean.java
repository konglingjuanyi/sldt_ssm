package com.sldt.dmc.ssm.teller.bean;

import java.io.Serializable;

public class AuthLogInfoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String errorCode;
	private String errorMsg;
	private String pwdmodFlag;
	private String pwdLimit;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getPwdmodFlag() {
		return pwdmodFlag;
	}
	public void setPwdmodFlag(String pwdmodFlag) {
		this.pwdmodFlag = pwdmodFlag;
	}
	public String getPwdLimit() {
		return pwdLimit;
	}
	public void setPwdLimit(String pwdLimit) {
		this.pwdLimit = pwdLimit;
	}

}
