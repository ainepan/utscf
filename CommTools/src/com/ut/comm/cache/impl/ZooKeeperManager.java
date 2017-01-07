package com.ut.comm.cache.impl;

import org.springframework.stereotype.Service;

import com.ut.comm.cache.ICacheClient;
import com.ut.comm.cache.ICacheManager;

//@Service("zkManager")
public class ZooKeeperManager implements ICacheManager{

	@Override
	public ICacheClient getCache() {
		return null;
	}

	@Override
	public ICacheClient getCache(String cacheName) {
		return null;
	}

	@Override
	public void shutdown() {
		
	}

	@Override
	public void start(String initFile) throws Exception {
		
	}

}
