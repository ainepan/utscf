package com.ut.comm.cache.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.CacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ut.comm.cache.ICacheClient;
import com.ut.comm.cache.ICacheManager;
import com.ut.comm.tool.consts.ASPathConst;
import com.ut.comm.tool.string.StringUtil;

//@Service("ehManager")
public class EhCacheManager implements ICacheManager{
	
	public static Logger logger = LoggerFactory.getLogger(EhCacheManager.class);
	private static Map<String ,ICacheClient> mapCSCacheImpls = new HashMap<String ,ICacheClient>();
	private static CacheManager cm = null;
	private static final String DEFAULT_CACHE_NAME  = "CACHE_DATA";//所有数据
	private String initFile = null;
	@Override
	public ICacheClient getCache() throws Exception {
		if (cm == null)
		{
			start(this.initFile);
		}
		return getCache(DEFAULT_CACHE_NAME);
	}

	@Override
	public ICacheClient getCache(String name) throws Exception
	{
		if (cm == null)
		{
			start(this.initFile);
		}
		ICacheClient cacheImpl = mapCSCacheImpls.get(name);
		return cacheImpl;
	}

	@Override
	public void start(String initFile) throws Exception {
		
		if (cm != null)
		{
			return;
		}
		
		this.initFile = initFile;
		StringBuffer paraPath = new StringBuffer(ASPathConst.getUserDirPath());
		paraPath.append(File.separator).append("syst").append(File.separator);
		if(StringUtil.isTrimEmpty(initFile)){
			paraPath.append("Cache_Config").append(".xml");
		}else{
			paraPath.append(initFile);
		}
		checkFile(paraPath.toString());

		CacheManager.create(paraPath.toString());
		cm = CacheManager.getInstance();
		String[] caches = cm.getCacheNames();
		String cacheName = null;
		for (int i = caches.length - 1; i >= 0; i--) {
			cacheName = caches[i];
			mapCSCacheImpls.put(cacheName, new EhCacheClientImpl(cm.getCache(cacheName)));
			logger.debug("Cache initlize success..."+cacheName);
		}
		
		logger.debug("AP Cache initlize success...");
	}

	@Override
	public void shutdown() {
		if (cm == null)
		{
			return;
		}
		
		cm.shutdown();
		
		cm = null;
	}
	
	public void checkFile(String filePath) throws Exception{
		if(StringUtil.isTrimEmpty(filePath)){
			throw new Exception("Config file is empty!");
		}
		
		File f = new File(filePath);
		if(!f.exists()){
			throw new Exception("Config file is not existed!");
		}
		
		if(!f.canRead()){
			throw new Exception("Config file can not be readed!");
		}
	}

}
