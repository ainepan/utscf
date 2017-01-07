package com.ut.comm.log;

import org.apache.log4j.Logger;

public interface ILogManager {
	public Logger getLogger(String logName,String bu,String userId);
	
	public Logger getLogger(String logName,String bu);
	
	public Logger getLogger(String logName);
	
	public void initlize();
	
	public void shutdown();
}
