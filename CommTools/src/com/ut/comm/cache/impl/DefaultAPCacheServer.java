package com.ut.comm.cache.impl;

public class DefaultAPCacheServer extends AbsCacheServer{

	@Override
	public void start() {
		logger.debug("No catch server start...");
	}

	@Override
	public void shutdown() {
		logger.debug("No catch server need to shutdown...");
	}

	@Override
	public void initilise() {
		logger.debug("No catch server need to initilise...");
	}

	@Override
	public String getConfig() {
		return null;
	}

	@Override
	public String getName() {
		return "default catch";
	}

}
