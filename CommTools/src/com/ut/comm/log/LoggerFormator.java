package com.ut.comm.log;

import org.slf4j.Logger;

public class LoggerFormator {
	public static void debug(Logger logger, Object... strings) {
		if (logger == null || !logger.isDebugEnabled()) {
			return;
		}
		StringBuffer buff = new StringBuffer();
		for (Object string : strings) {
			buff.append(string).append(" ");
		}
		logger.debug(buff.toString());
	}

	public static void error(Logger logger, Object... strings) {
		if (logger == null || !logger.isErrorEnabled()) {
			return;
		}
		StringBuffer buff = new StringBuffer();
		for (Object string : strings) {
			buff.append(string).append(" ");
		}
		logger.error(buff.toString());
	}

	public static void info(Logger logger, Object... strings) {
		if (logger == null || !logger.isInfoEnabled()) {
			return;
		}
		StringBuffer buff = new StringBuffer();
		for (Object string : strings) {
			buff.append(string).append(" ");
		}
		logger.info(buff.toString());
	}

	public static void warn(Logger logger, Object... strings) {
		if (logger == null || !logger.isWarnEnabled()) {
			return;
		}
		StringBuffer buff = new StringBuffer();
		for (Object string : strings) {
			buff.append(string).append(" ");
		}
		logger.warn(buff.toString());
	}
}
