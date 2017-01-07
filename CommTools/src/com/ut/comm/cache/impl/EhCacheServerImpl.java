package com.ut.comm.cache.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ut.comm.cache.ICacheServer;
import com.ut.comm.tool.consts.ASPathConst;

//@Service("ehCacheServer")
public class EhCacheServerImpl implements ICacheServer{

	private static Logger logger = LoggerFactory.getLogger("cacheLog");
	@Override
	public void start() {
//		StringBuffer paraPath = new StringBuffer(ASPathConst.getUserDirPath());
//		paraPath.append(File.separator).append("syst").append(File.separator).append("AP_Cache_Config").append(".xml");
//		try {
//			EhCacheClientImpl.init(paraPath.toString());
//		} catch (Exception e) {
//			logger.error("Initialize APCache failed");
//			logger.error(e.toString());
//		}
	}

	@Override
	public void shutdown() {
//		EhCacheClientImpl.shutdown();
	}

}
