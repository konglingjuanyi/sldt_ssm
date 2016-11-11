package com.sldt.dmc.sm.bean;

public class ExPriv {
	private String id;
	private String pId;
	private String name;
	private String iconSkin;
	private  String isParent;
	private boolean checked;
	private String halfCheck;
	private boolean open;
	
	public ExPriv(){}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIconSkin() {
		return iconSkin;
	}
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getHalfCheck() {
		return halfCheck;
	}
	public void setHalfCheck(String halfCheck) {
		this.halfCheck = halfCheck;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public ExPriv(String id, String pId, String name, String iconSkin,
			String isParent, boolean checked, String halfCheck, boolean open) {
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.iconSkin = iconSkin;
		this.isParent = isParent;
		this.checked = checked;
		this.halfCheck = halfCheck;
		this.open = open;
	}
	
	

}
