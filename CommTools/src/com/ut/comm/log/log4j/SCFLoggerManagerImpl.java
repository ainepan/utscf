package com.ut.comm.log.log4j;

import java.io.File;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.stereotype.Component;

import com.ut.comm.log.AbsLogManager;
import com.ut.comm.tool.consts.ASPathConst;
import com.ut.comm.tool.string.StringUtil;

@Component("loggerManager") 
public class SCFLoggerManagerImpl extends AbsLogManager{
	
	private String xmlConfigFile = "Log_Config.xml";
	

	@Override
	public void initLog() {
		String path = ASPathConst.getUserDirPath();
		xmlConfigFile =	path+File.separator+"syst"+File.separator+xmlConfigFile;
		DOMConfigurator.configure(xmlConfigFile);
	}
	
	@Override
	public void shutdownLog() {
	}


	public String getXmlConfigFile() {
		return xmlConfigFile;
	}

	public void setXmlConfigFile(String xmlConfigFile) {
		this.xmlConfigFile = xmlConfigFile;
	}

	@Override
	public Logger getLogger(String logName, String bu, String userId) {
		if (StringUtil.isTrimEmpty(logName)) {
			logName = "root";
		}

//		ApSessionContext context = ApSessionUtil.getContext();
//		String userID = context.getUserId();
		SCFLogger newLogger = (SCFLogger) Logger.getLogger(super.getLoggerName(logName, bu,userId), new SCFLoggerFactory());
		if (newLogger.isInitialized()) {
			return newLogger;
		} else {
			Logger lg = Logger.getLogger(logName);
			newLogger.setLevel(lg.getLevel());
			Enumeration<Appender> e = lg.getAllAppenders();
			while (e.hasMoreElements()) {
				Appender ob = e.nextElement();

				if (ob instanceof SCFDailyRollingFileAppender) {
					SCFDailyRollingFileAppender ofa = (SCFDailyRollingFileAppender) ob;
					SCFDailyRollingFileAppender newFileAdppender = new SCFDailyRollingFileAppender();

					newFileAdppender.setAppend(ofa.getAppend());
					newFileAdppender.setBufferedIO(ofa.getBufferedIO());
					newFileAdppender.setBufferSize(ofa.getBufferSize());
					newFileAdppender.setEncoding(ofa.getEncoding());
					newFileAdppender.setErrorHandler(ofa.getErrorHandler());
					newFileAdppender.setImmediateFlush(ofa.getImmediateFlush());
					newFileAdppender.setLayout(ofa.getLayout());
					newFileAdppender.setThreshold(ofa.getThreshold());
					newFileAdppender.setName(bu + "_" + ofa.getName());
					newFileAdppender.setByUser(ofa.isByUser());

					newFileAdppender.setFile(ofa.getFile());
					newFileAdppender.setFileName(ofa.getFileName());
					newFileAdppender.setUserId(userId);
					newFileAdppender.setBu(bu);
					newFileAdppender.setLogLevel(ofa.getLogLevel());
					newFileAdppender.activateOptions();

					newLogger.addAppender(newFileAdppender);
					newLogger.setInitialized(true);
				} else if (ob instanceof SCFRollingFileAdppender) {
					SCFRollingFileAdppender ofa = (SCFRollingFileAdppender) ob;
					SCFRollingFileAdppender newFileAdppender = new SCFRollingFileAdppender();

					newFileAdppender.setAppend(ofa.getAppend());
					newFileAdppender.setBufferedIO(ofa.getBufferedIO());
					newFileAdppender.setBufferSize(ofa.getBufferSize());
					newFileAdppender.setEncoding(ofa.getEncoding());
					newFileAdppender.setErrorHandler(ofa.getErrorHandler());
					newFileAdppender.setImmediateFlush(ofa.getImmediateFlush());
					newFileAdppender.setLayout(ofa.getLayout());
					newFileAdppender.setThreshold(ofa.getThreshold());
					newFileAdppender.setName(bu + "_" + ofa.getName());
					newFileAdppender.setByUser(ofa.isByUser());

					newFileAdppender.setFile(ofa.getFile());
					newFileAdppender.setFileName(ofa.getFileName());
//					newFileAdppender.setUserId(userID);
					newFileAdppender.setBu(bu);
					newFileAdppender.setLogLevel(ofa.getLogLevel());
					newFileAdppender.activateOptions();

					newLogger.addAppender(newFileAdppender);
					newLogger.setInitialized(true);
				}else if (ob instanceof ConsoleAppender) {
					newLogger.addAppender((ConsoleAppender) ob);
				}
			}
		}
		return newLogger;
	}
}
