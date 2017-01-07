package com.ut.comm.xml.sys;

import com.ut.comm.xml.AbsObject;

public class Schema extends AbsObject{
	private static final long serialVersionUID = -1865078744895197903L;

	private String type;
	private String schema;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	@Override
	public String getNodeName() {
		return  "schema";
	}
}
