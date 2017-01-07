package com.comm.pojo.report;

import java.io.Serializable;

public interface IReportResponse extends Serializable{
	public String getMessage();

	public void setMessage(String message);

	public boolean isSuccess();

	public void setSuccess(boolean success);

	public String getLevel();

	public void setLevel(String level);

	public String getFileContent() ;

	public void setFileContent(String fileContent);
}
