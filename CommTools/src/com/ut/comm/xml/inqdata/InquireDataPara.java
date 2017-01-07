package com.ut.comm.xml.inqdata;

import com.ut.comm.xml.AbsObject;

public class InquireDataPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4833915034662763099L;

	private String name;
	private String desc;
	//Q:Query,P:Page,S:Service L:Logic P:PA V:value
	private String type;
	private String target;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String getNodeName() {
		return "inqdata";
	}
	
	public boolean isPropertyBean(){
		return true;
	}

}
