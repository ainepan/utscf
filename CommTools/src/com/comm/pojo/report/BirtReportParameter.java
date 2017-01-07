package com.comm.pojo.report;


public class BirtReportParameter implements IReportParameter{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8093373347342107635L;

	private String operateType;
	
	private String dataSource;
	
	private String reportType;
	
	private String reportFilePath;
	
	private String designFilePath;
	
	private String trxData;
	
	private String dataSourceType;
	private String bu;

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public String getDesignFilePath() {
		return designFilePath;
	}

	public void setDesignFilePath(String designFilePath) {
		this.designFilePath = designFilePath;
	}

	public String getTrxData() {
		return trxData;
	}

	public void setTrxData(String trxData) {
		this.trxData = trxData;
	}

	public String getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}
	
	
}
