package com.ut.comm.cache.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ut.comm.cache.CommCacheManager;
import com.ut.comm.cache.ICacheClient;

public abstract class AbsCacheAbleObj implements ICacheAbleObj,ICacheStoreManager{
	private boolean isEnable = true;
	
	protected boolean isInitilize = false;
	
//	private ICacheClient cacheImpl = CommCacheManager.getInstance().getCache();
	
	private String cacheSource;
	
	private String cacheKey;
	
	private String cacheName;
	
	protected Logger getLogger(){
		return LoggerFactory.getLogger(this.getClass());
	}
	
	protected ICacheClient getCache() throws Exception{
		return CommCacheManager.getInstance().getCache(getCacheName());
	}
	
	protected String getCacheName(){
		return setCacheName();
	}
	
	public abstract String setCacheName();

	@Override
	public void refresh() {
		suspend();
		
		setInitilize(false);
		
		initilize();
		
		start();
	}

	@Override
	public void start() {
		isEnable = true;
	}

	@Override
	public void suspend() {
		isEnable = false;
	}

	@Override
	public boolean isEnable() {
		return isEnable;
	}

	protected String getCacheSource() {
		return cacheSource;
	}
	
	protected String getCacheKey() {
		return cacheKey;
	}

	protected abstract String setCacheSource();
	
	@Override
	public Object getData(String cacheKey,Object reqData) throws Exception {
		if(!this.isInitilize()){
			initilize();
		}
		if(getCache().existData(cacheKey)){
			return getCache().getData(cacheKey);
		}
		return getDataFromSource(cacheKey);
	}
	
	@Override
	final public void setData(String cacheKey, Object data) throws Exception {
		getCache().createData(cacheKey, data);
	}
	
	protected abstract Object getDataFromSource(String cacheKey);

	protected boolean isInitilize() {
		return isInitilize;
	}

	protected void setInitilize(boolean isInitilize) {
		this.isInitilize = isInitilize;
	}
}
