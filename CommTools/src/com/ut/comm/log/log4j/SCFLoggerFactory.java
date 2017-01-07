package com.ut.comm.log.log4j;

import org.apache.log4j.spi.LoggerFactory;

public class SCFLoggerFactory implements LoggerFactory{

	@Override
	public SCFLogger makeNewLoggerInstance(String name) {
		SCFLogger logger = new SCFLogger(name);

		return logger;
	}

}
