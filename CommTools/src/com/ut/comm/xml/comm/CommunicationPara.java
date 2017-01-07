package com.ut.comm.xml.comm;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class CommunicationPara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6608916937313705892L;

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_COMM;
	}
	
	private String name;
	private String desc;
	private String consumer;
	private String provider;
	private String method;
	/**
	 * 0:单向通讯
	 * 1：双向同步通讯
	 * 2：双向推送异步通讯
	 * 3：双线查询异步通讯
	 */
	private String type;
	/**
	 * 11 : 标记为费用
	 */
	private String label;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getConsumer() {
		return consumer;
	}
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	

}
