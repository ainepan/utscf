package com.ut.comm.cache.util;

public interface ICacheStoreManager {
	
	/**
	 * 刷新缓存的数据
	 */
	public void refresh();
	
	/**
	 * 启用缓存
	 */
	public void start();
	
	/**
	 * 暂停缓存
	 */
	public void suspend();
}
