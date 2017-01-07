package com.ut.comm.cache.util;

import com.ut.comm.tool.string.StringUtil;

public abstract class AbsDBCacheAbleObj extends AbsCacheAbleObj{

	@Override
	public void initilize() {
		
	}

	@Override
	final protected String setCacheSource() {
		return CACHE_DATASOURCE_TYPE_DB;
	}

	/**
	 * cacheKey Query Id
	 */
	@Override
	protected Object getDataFromSource(String cacheKey) {
		if(StringUtil.isTrimEmpty(cacheKey)){
			cacheKey = this.getCacheKey();
		}
		if(StringUtil.isTrimEmpty(cacheKey)){
			return null;
		}
		
		this.refresh();
		
		try {
			return this.getData(cacheKey, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
