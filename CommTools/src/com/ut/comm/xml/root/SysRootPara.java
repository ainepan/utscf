package com.ut.comm.xml.root;

import com.ut.comm.xml.AbsObject;

public class SysRootPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeName() {
		return "root";
	}

	private String devModle;
	private String apCache;
	private String apAddress;
	private String rootAddress;
	private String rmiPort;

	public String getDevModle() {
		return devModle;
	}
	public void setDevModle(String devModle) {
		this.devModle = devModle;
	}
	public String getApCache() {
		return apCache;
	}
	public void setApCache(String apCache) {
		this.apCache = apCache;
	}
	public String getApAddress() {
		return apAddress;
	}
	public void setApAddress(String apAddress) {
		this.apAddress = apAddress;
	}
	public String getRootAddress() {
		return rootAddress;
	}
	public void setRootAddress(String rootAddress) {
		this.rootAddress = rootAddress;
	}
	public String getRmiPort() {
		return rmiPort;
	}
	public void setRmiPort(String rmiPort) {
		this.rmiPort = rmiPort;
	}
	
	public boolean isPropertyBean(){
		return true;
	}
}
