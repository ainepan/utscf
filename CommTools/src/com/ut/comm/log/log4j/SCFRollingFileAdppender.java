package com.ut.comm.log.log4j;

import java.io.File;

import org.apache.log4j.RollingFileAppender;

import com.ut.comm.tool.consts.ASPathConst;
import com.ut.comm.tool.string.StringUtil;

public class SCFRollingFileAdppender extends RollingFileAppender{
	
	private String logPath = ASPathConst.getUserOutputPath();//支持独立设置log盘符，默认在Para/logs
	private String sysFolder = "systemlog";
	private String userFolder = "userslog";
	
	private final String LOG_LEVEL_USER = "user";
	
	private boolean byUser;
	private String fileName;
	private String bu;
	private String logLevel;

	public boolean isByUser() {
		return byUser;
	}

	public void setByUser(boolean byUser) {
		this.byUser = byUser;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public void activateOptions() {
		super.setFile(initSCFLogFile());
		super.activateOptions();
	}

	private String initSCFLogFile() {
		try {
			String logpath = getAbsoluteLogPath();
			String newFileName = logpath + File.separator;
		
			newFileName += getFileName();
			File file = new File(newFileName);
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				if (!parentFile.mkdirs()) {
					System.out.println(new StringBuffer(parentFile.getPath()).append(" make failed").toString());
				}
			}
			if (!file.exists() && !file.createNewFile()) {
				System.out.println(new StringBuffer(newFileName).append(" make failed").toString());
			}

			return newFileName;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return getFile();
	}
	
	protected String getAbsoluteLogPath()
	{
		if(StringUtil.isTrimEmpty(bu)){
			bu = ASPathConst.getDefaultBuName();
		}
		StringBuffer pathBuff = new StringBuffer();
		pathBuff.append(logPath).append(File.separator).append(bu).append(File.separator)
		.append("logs").append(File.separator);
		if(LOG_LEVEL_USER.equalsIgnoreCase(logLevel)){
			pathBuff.append(userFolder);
		}else{
			pathBuff.append(sysFolder);
		}
		pathBuff.append(File.separator);
		
		return pathBuff.toString();
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
}
