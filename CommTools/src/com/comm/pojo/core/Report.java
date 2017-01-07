package com.comm.pojo.core;

import java.io.Serializable;

import com.comm.pojo.report.IReportParameter;

public class Report implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String type;

	private String engineUrl;
	private String trxData;
	
	private IReportParameter para;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEngineUrl() {
		return engineUrl;
	}

	public void setEngineUrl(String engineUrl) {
		this.engineUrl = engineUrl;
	}

	public String getTrxData() {
		return trxData;
	}

	public void setTrxData(String trxData) {
		this.trxData = trxData;
	}

	public IReportParameter getPara() {
		return para;
	}

	public void setPara(IReportParameter para) {
		this.para = para;
	}
}
