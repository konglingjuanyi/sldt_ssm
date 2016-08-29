/**
 * @author Administrator
 *
 */
package com.sldt.dmc.ssm.parms.bean;

import java.io.Serializable;

public class Parameter  implements Serializable{
	
	private static final long serialVersionUID = -2186221662876605987L;
	
	String id;
	String paramType;
	String paramCode;
	String paramValue;
	String paramTypeDes;
	String paramCodeDes;
	String rem;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamTypeDes() {
		return paramTypeDes;
	}

	public void setParamTypeDes(String paramTypeDes) {
		this.paramTypeDes = paramTypeDes;
	}

	public String getParamCodeDes() {
		return paramCodeDes;
	}

	public void setParamCodeDes(String paramCodeDes) {
		this.paramCodeDes = paramCodeDes;
	}

	public String getRem() {
		return rem;
	}

	public void setRem(String rem) {
		this.rem = rem;
	}
}
