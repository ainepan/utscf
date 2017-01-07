package com.comm.pojo.report;

import java.io.Serializable;

public interface IReportParameter extends Serializable{
	public void setOperateType(String operateType);

	public void setDataSource(String dataSource);

	public void setReportType(String reportType);

	public void setReportFilePath(String reportFilePath);

	public void setDesignFilePath(String designFilePath);

	public void setTrxData(String trxData);
	
	public void setDataSourceType(String dataSourceType);
	public void setBu(String bu);
	
	String getBu();
	String getOperateType();

	String getDataSource();

	String getReportType();

	String getReportFilePath();

	String getDesignFilePath();

	String getTrxData();
	
	String getDataSourceType();

}
