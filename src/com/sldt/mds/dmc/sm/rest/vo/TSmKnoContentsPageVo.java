package com.sldt.mds.dmc.sm.rest.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sldt.mds.dmc.sm.extend.entity.TSmKnoContents;
import com.sldt.mds.dmc.sm.extend.entity.TSmNotice;


@XmlRootElement(name = "TSmKnoContentsPageVo")
@XmlAccessorType(XmlAccessType.FIELD)
public class TSmKnoContentsPageVo implements Serializable {
	private int page;
	private int total;
	private int records;
	private int pageSize;
	private List<TSmKnoContents> rows;
	
	public TSmKnoContentsPageVo(){}
	public int getPage() {
		return this.page;
	}

	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<TSmKnoContents> rows) {
		this.rows = rows;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return this.records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public TSmKnoContentsPageVo(int pageSize, int page, int records) {
		this.pageSize = pageSize;
		this.page = page;
		this.records = records;

		if (records % pageSize == 0)
			this.total = (records / pageSize);
		else {
			this.total = (records / pageSize + 1);
		}
		if (page >= this.total) {
			page = this.total;
		}
		if (page <= 1)
			page = 1;
	}
}