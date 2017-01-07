package com.ut.comm.xml.method;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class MethodDefinePara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5769205066575779170L;

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_METHOD_DEFINE;
	}
	
	private String name;
	
	private String desc;
	
	/**
	 * 映射到GAPI MSG定义
	 */
	private String gapimsg;
	
	private String type;

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

	public String getGapimsg() {
		return gapimsg;
	}

	public void setGapimsg(String gapimsg) {
		this.gapimsg = gapimsg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
