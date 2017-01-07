package com.ut.comm.cache;

import java.util.List;


public interface ICacheClient {
	public Object getData(String key);
	
	public void createData(String key,Object data);
	
	public void updateData(String key,Object data);
	
	public void removeData(String key);
	
	public void removeDataContainsChild(String key);
	
	public boolean existData(String key);
	
	public List<String> getChildKeys(String key);
}
