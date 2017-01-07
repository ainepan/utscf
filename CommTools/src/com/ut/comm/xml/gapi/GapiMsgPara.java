package com.ut.comm.xml.gapi;

import com.ut.comm.xml.AbsObject;

public class GapiMsgPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeName() {
		return "gapimsg";
	}
	
	private String name;
	private String modle;
	private String ack;
	private String ackMsg;
	private String sendtemp;
	private String receivetemp;
	private String gapi;
	private String reformatclass;
	private String validateclass;
	private String generator;
	private String characterset;
	private String msgtype;
	private String msgstore;
	private String sendinterrupt;
	private String receiveinterrupt;
	private String reformatjs;
	private String validatejs;
	private String sendservice;
	private String receiveservice;
	private String onsuccess;
	private String onfailed;
	/*
	 * 0   直接发送
	 * 1 for batch
	 * 2 for receive
	 */
	private String gapiSts;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModle() {
		return modle;
	}
	public void setModle(String modle) {
		this.modle = modle;
	}
	public String getAck() {
		return ack;
	}
	public void setAck(String ack) {
		this.ack = ack;
	}
	public String getSendtemp() {
		return sendtemp;
	}
	public void setSendtemp(String sendtemp) {
		this.sendtemp = sendtemp;
	}
	public String getReceivetemp() {
		return receivetemp;
	}
	public void setReceivetemp(String receivetemp) {
		this.receivetemp = receivetemp;
	}
	public String getGapi() {
		return gapi;
	}
	public void setGapi(String gapi) {
		this.gapi = gapi;
	}
	public String getReformatclass() {
		return reformatclass;
	}
	public void setReformatclass(String reformatclass) {
		this.reformatclass = reformatclass;
	}
	public String getValidateclass() {
		return validateclass;
	}
	public void setValidateclass(String validateclass) {
		this.validateclass = validateclass;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public String getCharacterset() {
		return characterset;
	}
	public void setCharacterset(String characterset) {
		this.characterset = characterset;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getMsgstore() {
		return msgstore;
	}
	public void setMsgstore(String msgstore) {
		this.msgstore = msgstore;
	}
	public String getSendinterrupt() {
		return sendinterrupt;
	}
	public void setSendinterrupt(String sendinterrupt) {
		this.sendinterrupt = sendinterrupt;
	}
	public String getReceiveinterrupt() {
		return receiveinterrupt;
	}
	public void setReceiveinterrupt(String receiveinterrupt) {
		this.receiveinterrupt = receiveinterrupt;
	}
	public String getReformatjs() {
		return reformatjs;
	}
	public void setReformatjs(String reformatjs) {
		this.reformatjs = reformatjs;
	}
	public String getValidatejs() {
		return validatejs;
	}
	public void setValidatejs(String validatejs) {
		this.validatejs = validatejs;
	}
	public String getSendservice() {
		return sendservice;
	}
	public void setSendservice(String sendservice) {
		this.sendservice = sendservice;
	}
	public String getReceiveservice() {
		return receiveservice;
	}
	public void setReceiveservice(String receiveservice) {
		this.receiveservice = receiveservice;
	}
	/**
	 * ack msg id , 独立的GAPI msg
	 * @return
	 */
	public String getAckMsg() {
		return ackMsg;
	}
	public void setAckMsg(String ackMsg) {
		this.ackMsg = ackMsg;
	}
	public String getOnsuccess() {
		return onsuccess;
	}
	public void setOnsuccess(String onsuccess) {
		this.onsuccess = onsuccess;
	}
	public String getOnfailed() {
		return onfailed;
	}
	public void setOnfailed(String onfailed) {
		this.onfailed = onfailed;
	}
	public String getGapiSts() {
		return gapiSts;
	}
	public void setGapiSts(String gapiSts) {
		this.gapiSts = gapiSts;
	}
	

}
