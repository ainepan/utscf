package com.ut.comm.cache.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ut.comm.cache.ICacheClient;

public class EhCacheClientImpl implements ICacheClient{
	
	public static Logger logger = LoggerFactory.getLogger(EhCacheClientImpl.class);
	
	private Cache cache;
	
	public EhCacheClientImpl(Cache c){
		this.cache = c;
	}

//	public boolean isEnabled() {
//		return cache.getMemoryStoreSize() > 0;
//	}

	@Override
	public Object getData(String key) {
		Element e = cache.get(key);
		if (e == null)
			return null;

		return e.getObjectValue();
	}

	@Override
	public void createData(String key, Object data){
		Element e = new Element(key, data);
		cache.put(e);
	}

	@Override
	public void updateData(String key, Object data){
		Element e = new Element(key, data);
		cache.put(e);
	}

	@Override
	public void removeData(String key) {
		List<String> keys = cache.getKeys();
		String strKey = key.toString();
		for (String string : keys) {
			if(string.contains(strKey)){
				logger.debug("Cache remove... key:"+string);
				cache.remove(string);
			}
		}
	}

	@Override
	public void removeDataContainsChild(String key) {
		List<String> keys = cache.getKeys();
		String strKey = key.toString();
		for (String string : keys) {
			if(string.contains(strKey)){
				logger.debug("Cache remove... key:"+string);
				cache.remove(string);
			}
		}
	}

	@Override
	public boolean existData(String key) {
		List<String> keys = cache.getKeys();
		return keys.contains(key)&&getData(key)!=null;
		
	}

	@Override
	public List<String> getChildKeys(String key) {
		List<String> keys = cache.getKeys();
		List<String> retKeys = new ArrayList<String>();
		String strKey = key.toString();
		for (String string : keys) {
			if(string.contains(strKey)){
				retKeys.add(string);
			}
		}
		return retKeys;
	}
}
