package com.ut.comm.xml.ref;

import com.ut.comm.xml.AbsObject;

public class RefPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1865078744895197903L;

	private String refname;
	private String reffield;

	public String getRefname() {
		return refname;
	}

	public void setRefname(String refname) {
		this.refname = refname;
	}

	public String getReffield() {
		return reffield;
	}

	public void setReffield(String reffield) {
		this.reffield = reffield;
	}

	@Override
	public String getNodeName() {
		return "refno";
	}

}
