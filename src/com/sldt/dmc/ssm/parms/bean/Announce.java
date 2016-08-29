/**
 * @author chenbo
 * @date 2013-12-01
 *
 */
package com.sldt.dmc.ssm.parms.bean;

import java.io.Serializable;

public class Announce  implements Serializable{
	
	private static final long serialVersionUID = 2735155241393445711L;
	String id;
	String title;
	String content;
	String state;
	String origin;
	String kind;
	String level;
	String mailTag;
	String mailGroupId;
	String tipTag;
	String createrId;
	String createrName;
	String createDate;
	String latestUpdateDate;
	String publishDate;
	String archiverId;
	String archiverName;
	String archiveDate;
	String remember;
	String validDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMailTag() {
		return mailTag;
	}

	public void setMailTag(String mailTag) {
		this.mailTag = mailTag;
	}

	public String getMailGroupId() {
		return mailGroupId;
	}

	public void setMailGroupId(String mailGroupId) {
		this.mailGroupId = mailGroupId;
	}

	public String getTipTag() {
		return tipTag;
	}

	public void setTipTag(String tipTag) {
		this.tipTag = tipTag;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getLatestUpdateDate() {
		return latestUpdateDate;
	}

	public void setLatestUpdateDate(String latestUpdateDate) {
		this.latestUpdateDate = latestUpdateDate;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getArchiverId() {
		return archiverId;
	}

	public void setArchiverId(String archiverId) {
		this.archiverId = archiverId;
	}

	public String getArchiverName() {
		return archiverName;
	}

	public void setArchiverName(String archiverName) {
		this.archiverName = archiverName;
	}

	public String getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(String archiveDate) {
		this.archiveDate = archiveDate;
	}

	public String getRemember() {
		return remember;
	}

	public void setRemember(String remember) {
		this.remember = remember;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
}
