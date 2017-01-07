package com.ut.comm.xml.logicflow;

import com.ut.comm.xml.AbsObject;

public class TransforField extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fields;
	
	private String values;

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	@Override
	public String getNodeName() {
		return  "pending,release,delepending";
	}

}
