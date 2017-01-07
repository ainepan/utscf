package com.ut.comm.xml.edi;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class EDIPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String desc;
	private String type;
	private String template;
	private String mapping;
	private String js;

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
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	public String getJs() {
		return js;
	}
	public void setJs(String js) {
		this.js = js;
	}
	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_EDI;
	}

}
