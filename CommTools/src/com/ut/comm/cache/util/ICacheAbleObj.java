package com.ut.comm.cache.util;

import java.util.List;

public interface ICacheAbleObj {
	public void initilize();
	
	public String setCacheKey();
	
	public List<String> setCacheStoreKeys();
	
	public Object getData(String cacheKey,Object reqData) throws Exception;
	
	public final String CACHE_DATASOURCE_TYPE_DB = "DB";
	
	public final String CACHE_DATASOURCE_TYPE_INPUT = "INPUT";
	
	public final String CACHE_NAME_DEFAULT = "CACHE_DATA";
	
	public boolean isEnable();
	
	public void setData(String cacheKey, Object data) throws Exception;
}
