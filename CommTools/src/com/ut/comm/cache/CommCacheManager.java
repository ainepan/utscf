package com.ut.comm.cache;

import com.ut.comm.cache.impl.EhCacheManager;
import com.ut.comm.cache.impl.ZooKeeperManager;
import com.ut.comm.xml.XMLParaParseHelper;
import com.ut.comm.xml.root.SysRootPara;

public class CommCacheManager implements ICacheManager{
	private static ICacheManager cacheManager;
	
	private static ICacheManager realManager;
	
	private CommCacheManager(){
		
	}
	
	public static ICacheManager getInstance(){
		if (cacheManager == null) {
			cacheManager = new CommCacheManager();
//			cacheManager.start();
		}
		return cacheManager;
	}
	
	@Override
	public ICacheClient getCache(String cacheName) throws Exception {
		if (cacheManager == null||realManager == null) {
			cacheManager =  getInstance();
		}
		return realManager.getCache(cacheName);
	}
	
	@Override
	public ICacheClient getCache() throws Exception {
		if (cacheManager == null||realManager == null) {
			cacheManager =  getInstance();
		}
		return realManager.getCache();
	}

	/**
	 * 此方法只会在启动时，执行一次
	 */
	@Override
	public void start(String initFile) {
		SysRootPara root = XMLParaParseHelper.parseSystPara();
		String cacheName = root.getApCache();
		try {
			if ("ehcache".equalsIgnoreCase(cacheName)) {
				realManager = new EhCacheManager();
			} else if ("zookeeper".equalsIgnoreCase(cacheName)) {
				realManager = new ZooKeeperManager();
			}else{
				try {
					realManager = (ICacheManager) Class.forName(root.getApCache()).newInstance();
				} catch (InstantiationException e) {
					throw e;
				} catch (IllegalAccessException e) {
					throw e;
				}
			}
			realManager.start(initFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void shutdown() {
		if (cacheManager != null&&realManager!=null) {
			realManager.shutdown();
		}
		realManager= null;
		cacheManager = null;
	}
	
//	public static ICacheManager getDefaultCache() {
//		if (cacheManager == null) {
//			return null;
//		}
//		return cacheManager;
//	}
}
