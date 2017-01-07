package com.ut.comm.log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ut.comm.tool.consts.ASPathConst;
import com.ut.comm.tool.string.StringUtil;

public abstract class AbsLogManager implements ILogManager{
	
	private boolean inited = false;
	
	public static final String AP_PARA_DEFAULT_PATH = ASPathConst.getDefaultBuName();
	
	private static Map<String,Logger> registedLogger = new ConcurrentHashMap<String,Logger>();
	
	
	protected boolean isInited() {
		return inited;
	}

	protected void setInited(boolean inited) {
		this.inited = inited;
	}
	
	@Override
	public void initlize() {
		if(!isInited()){
			initLog();
			setInited(true);
		}		
	}

	@Override
	public void shutdown() {
		if(isInited()){
			shutdownLog();
			setInited(false);
		}		
	}
	
	protected abstract void initLog();
	
	protected abstract void shutdownLog();
	
	protected Logger getLogger(){
		return getLogger("");
	}
	
	public Logger getLogger(String logName){
		return getLogger("",AP_PARA_DEFAULT_PATH);
	}
	
	@Override
	public Logger getLogger(String logName, String bu) {
		return getLogger("",AP_PARA_DEFAULT_PATH,"");
	}
	
	protected boolean registedLogger(String logName,String bu){
		if(StringUtil.isTrimEmpty(logName))
			return false;
		if(StringUtil.isTrimEmpty(bu))
			bu = AP_PARA_DEFAULT_PATH;
		return registedLogger.containsKey(getLoggerName(logName,bu,""));
	}
	
	protected void registLogger(String logName,String bu,Logger l){
		if(StringUtil.isTrimEmpty(logName))
			return ;
		if(StringUtil.isTrimEmpty(bu))
			bu = AP_PARA_DEFAULT_PATH;
		registedLogger.put(getLoggerName(logName,bu,""),l);
	}
	
	protected Logger getRegistedLogger(String logName,String bu){
		if(StringUtil.isTrimEmpty(logName))
			return null;
		if(StringUtil.isTrimEmpty(bu))
			bu = AP_PARA_DEFAULT_PATH;
		return registedLogger.get(getLoggerName(logName,bu,""));
	}
	
	protected String getLoggerName(String logName,String bu,String userId){
		if(StringUtil.isTrimEmpty(logName))
			return "";
		if(StringUtil.isTrimEmpty(bu))
			bu = AP_PARA_DEFAULT_PATH;
		
		StringBuffer keyBuff = new StringBuffer();
		keyBuff.append(bu).append("_").append(logName);
		if(StringUtil.isTrimNotEmpty(userId)){
			keyBuff.append("_").append(userId);
		}
		
		return keyBuff.toString();
	}
}
