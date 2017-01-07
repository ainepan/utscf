package com.ut.comm.xml.catalog;

import com.ut.comm.xml.AbsObject;

public class CatalogOnObj extends AbsObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int seq;
	
	private String on;
	
	private String condition;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getOn() {
		return on;
	}

	public void setOn(String on) {
		this.on = on;
	}

	@Override
	public String getNodeName() {
		return  "on";
	}

}
