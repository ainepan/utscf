package com.comm.pojo;

import java.io.Serializable;

public class FunctionInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5503492849224400571L;
	private String sysFuncId;
	private String sysFuncName;
	private String module;
	private String funcType;
	private String sysOrgnFuncId;
	private int sysEventTimes;
	private String sysRefNo;
	private String entryTp;
	public String getSysFuncId() {
		return sysFuncId;
	}
	public void setSysFuncId(String sysFuncId) {
		this.sysFuncId = sysFuncId;
	}
	public String getSysFuncName() {
		return sysFuncName;
	}
	public void setSysFuncName(String sysFuncName) {
		this.sysFuncName = sysFuncName;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getFuncType() {
		return funcType;
	}
	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}
	public String getSysOrgnFuncId() {
		return sysOrgnFuncId;
	}
	public void setSysOrgnFuncId(String sysOrgnFuncId) {
		this.sysOrgnFuncId = sysOrgnFuncId;
	}
	public int getSysEventTimes() {
		return sysEventTimes;
	}
	public void setSysEventTimes(int sysEventTimes) {
		this.sysEventTimes = sysEventTimes;
	}
	public String getSysRefNo() {
		return sysRefNo;
	}
	public void setSysRefNo(String sysRefNo) {
		this.sysRefNo = sysRefNo;
	}
	public String getEntryTp() {
		return entryTp;
	}
	public void setEntryTp(String entryTp) {
		this.entryTp = entryTp;
	}
}
