package com.ut.comm.xml.verify;

import com.ut.comm.xml.AbsObject;

public class VerifyPara  extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeName() {
		return "verify";
	}
	/*
	 * 1为验证码服务，2为短信通知
	 */
	private String sendtp;
	private String gapiid;
	private String validtime;
	private String codelen;
	private String keyname;
	private String errorTimes;
	public String getErrorTimes() {
		return errorTimes;
	}
	public void setErrorTimes(String errorTimes) {
		this.errorTimes = errorTimes;
	}
	/*
	 * 验证码格式
	 * 1纯数字
	 * 2纯英文
	 * 3英文加数字
	 */
	private String codeTp;
	

	public String getSendtp() {
		return sendtp;
	}
	public String getCodeTp() {
		return codeTp;
	}
	public void setCodeTp(String codeTp) {
		this.codeTp = codeTp;
	}
	public void setSendtp(String sendtp) {
		this.sendtp = sendtp;
	}
	public String getGapiid() {
		return gapiid;
	}
	public void setGapiid(String gapiid) {
		this.gapiid = gapiid;
	}
	public String getValidtime() {
		return validtime;
	}
	public void setValidtime(String validtime) {
		this.validtime = validtime;
	}
	public String getCodelen() {
		return codelen;
	}
	public void setCodelen(String codelen) {
		this.codelen = codelen;
	}
	public String getKeyname() {
		return keyname;
	}
	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}

}
