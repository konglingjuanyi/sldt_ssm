package com.sldt.dmc.ssm.cgbsso.web;

public class CgbssoConfigMgr {
	/*
	 * 是否总是跳转到单独登录页面，将ssm的登录页面透明化处理
	 */
    String flagAllwaysRedirectToSSO="";
	public String getFlagAllwaysRedirectToSSO() {
		return flagAllwaysRedirectToSSO;
	}
	public void setFlagAllwaysRedirectToSSO(String flagAllwaysRedirectToSSO) {
		this.flagAllwaysRedirectToSSO = flagAllwaysRedirectToSSO;
	}

	public boolean isAllwaysRedirectToSSO() {
		if("true".equalsIgnoreCase(flagAllwaysRedirectToSSO)
	      ||"t".equalsIgnoreCase(flagAllwaysRedirectToSSO)
	      ||"1".equalsIgnoreCase(flagAllwaysRedirectToSSO)
	      ||"y".equalsIgnoreCase(flagAllwaysRedirectToSSO)
	      ||"yes".equalsIgnoreCase(flagAllwaysRedirectToSSO)
	    ){
			return true;
		}else{
			return false;
		}
	}

  
}
