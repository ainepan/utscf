package com.ut.comm.xml.template;

import com.ut.comm.xml.AbsObject;

public class MappingField extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8670039520642455183L;

	private String key;
	private String isnull;
	private String isunique;
	private String desc;
	private String type;
	private String defaultvalue;
	private String mfield;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIsnull() {
		return isnull;
	}

	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}

	public String getIsunique() {
		return isunique;
	}

	public void setIsunique(String isunique) {
		this.isunique = isunique;
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

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

//	public String getField() {
//		return field;
//	}
//
//	public void setField(String field) {
//		this.field = field;
//	}

	@Override
	public String getNodeName() {
		return "mfield";
	}

	public String getMfield() {
		return mfield;
	}

	public void setMfield(String mfield) {
		this.mfield = mfield;
	}

}
