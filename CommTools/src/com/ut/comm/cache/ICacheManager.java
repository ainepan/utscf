package com.ut.comm.cache;

public interface ICacheManager {
	public ICacheClient getCache() throws Exception;
	
	public ICacheClient getCache(String cacheName) throws Exception;
	
	public void start(String initFile) throws Exception;
	
	public void shutdown();
}
