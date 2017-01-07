package com.ut.comm.xml.product;

import com.ut.comm.xml.AbsObject;

public class FinSourcePara  extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeName() {
		return "finsource";
	}
	
	private String desc;
	
	private String priority;
	
	private String maxamt;
	
	private String ccy;
	
	private String type;
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getMaxamt() {
		return maxamt;
	}

	public void setMaxamt(String maxamt) {
		this.maxamt = maxamt;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
