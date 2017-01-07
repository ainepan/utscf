package com.ut.comm.log.log4j;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.DailyRollingFileAppender;

import com.ut.comm.tool.consts.ASPathConst;
import com.ut.comm.tool.string.StringUtil;

public class SCFDailyRollingFileAppender extends DailyRollingFileAppender {
	private String dateformateStr = "yyyy-MM-dd";
	private String logPath = ASPathConst.getUserOutputPath();//支持独立设置log盘符，默认在Para/logs
	private String sysFolder = "systemlog";
	private String userFolder = "userslog";
	
	private final String LOG_LEVEL_SYST = "syst";
	private final String LOG_LEVEL_USER = "user";
	
	private boolean byUser;
	private String fileName;
	private String userId;
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

	public void setUserId(String userId) {
		this.userId = userId;
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
			String newFileName = null;
			String logpath = getAbsoluteLogPath();
		
			if (this.isByUser()) {
				newFileName = logpath + File.separator + "%y-%m-%d" + File.separator ;
			} else {
				newFileName = logpath + File.separator;
			}
			Calendar cal = Calendar.getInstance();

			String sday = "00" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
			sday = sday.substring(sday.length() - 2, sday.length());

			String smonth = "00" + String.valueOf(cal.get(Calendar.MONTH) + 1);
			smonth = smonth.substring(smonth.length() - 2, smonth.length());

			String syear = String.valueOf(cal.get(Calendar.YEAR));

			int inx = newFileName.indexOf("%y");
			if (inx >= 0)
				newFileName = newFileName.substring(0, inx) + syear + newFileName.substring(inx + 2);

			inx = newFileName.indexOf("%m");
			if (inx >= 0)
				newFileName = newFileName.substring(0, inx) + smonth + newFileName.substring(inx + 2);

			inx = newFileName.indexOf("%d");
			if (inx >= 0)
				newFileName = newFileName.substring(0, inx) + sday + newFileName.substring(inx + 2);

			SimpleDateFormat format = new SimpleDateFormat(this.dateformateStr);
			String currentDate = format.format(new Date(System.currentTimeMillis()));

			if (this.isByUser()&&StringUtil.isTrimNotEmpty(userId)) {
				String f = "[" + userId + "]_";
				newFileName += f+getFileName();
			} else {
				newFileName += currentDate + "_" + getFileName();
			}
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
