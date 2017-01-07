package com.ut.comm.log.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Marker;

public class SCFLogger extends Logger implements org.slf4j.Logger{
	
	private boolean initialized = false;
	
	static String FQCN = SCFLogger.class.getName() + ".";

	protected SCFLogger(String name) {
		super(name);
	}
	
	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	
	@Override
	public void forcedLog(String fqcn, Priority level, Object message, Throwable t){
		callAppenders(new LoggingEvent(FQCN, this, level, message,t));
	}

	@Override
	public void debug(String arg0) {
		super.debug(arg0);
	}

	@Override
	public void debug(String arg0, Object arg1) {
		super.debug(arg0);
		
	}

	@Override
	public void debug(String arg0, Object[] arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(String arg0, Throwable arg1) {
		super.debug(arg0,arg1);
	}

	@Override
	public void debug(Marker arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(String arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(Marker arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(Marker arg0, String arg1, Object[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(Marker arg0, String arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(Marker arg0, String arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String arg0) {
		super.error(arg0);
		
	}

	@Override
	public void error(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String arg0, Object[] arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String arg0, Throwable arg1) {
		super.error(arg0,arg1);
		
	}

	@Override
	public void error(Marker arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(Marker arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(Marker arg0, String arg1, Object[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(Marker arg0, String arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(Marker arg0, String arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(String arg0) {
		super.info(arg0);
	}

	@Override
	public void info(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(String arg0, Object[] arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(String arg0, Throwable arg1) {
		super.info(arg0,arg1);
		
	}

	@Override
	public void info(Marker arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(String arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(Marker arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(Marker arg0, String arg1, Object[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(Marker arg0, String arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(Marker arg0, String arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDebugEnabled(Marker arg0) {
		return super.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return super.isEnabledFor(Priority.ERROR);
	}

	@Override
	public boolean isErrorEnabled(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInfoEnabled(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTraceEnabled(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWarnEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWarnEnabled(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void trace(String arg0) {
		super.trace(arg0);
		
	}

	@Override
	public void trace(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(String arg0, Object[] arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(String arg0, Throwable arg1) {
		super.trace(arg0,arg1);
	}

	@Override
	public void trace(Marker arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(String arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(Marker arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(Marker arg0, String arg1, Object[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(Marker arg0, String arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(Marker arg0, String arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(String arg0) {
		super.warn(arg0);
		
	}

	@Override
	public void warn(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(String arg0, Object[] arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(String arg0, Throwable arg1) {
		super.warn(arg0,arg1);
	}

	@Override
	public void warn(Marker arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(String arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(Marker arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(Marker arg0, String arg1, Object[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(Marker arg0, String arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(Marker arg0, String arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub
		
	}

}
