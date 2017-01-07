package com.ut.comm.cache;

/**
 * C/S架构下的Cache client 需要 连接和释放连接
 * @author PanHao
 *
 */
public interface ICSCacheClient extends ICacheClient{
	public void createConnection();
	
	public void connect();
	
	public void close();
	
	public Object getConnection();
}
