package com.ut.comm.xml.risk;

import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.method.MethodDefinePara;

public class RiskPara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7093716174600261172L;
	
	public RiskPara() {
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_METHOD_DEFINE, MethodDefinePara.class);
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_RISK;
	}
	
	private String name;
	private String desc;
	/**
	 * 0:
	 * 1：
	 * 2：
	 */
	private String module;
	
	private String level;
	
	private String custlevel;
	
	/**
	 * 0 : 标记为风险
	 */
	private String label;
	
	private List<MethodDefinePara> methods;

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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCustlevel() {
		return custlevel;
	}

	public void setCustlevel(String custlevel) {
		this.custlevel = custlevel;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<MethodDefinePara> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodDefinePara> methods) {
		this.methods = methods;
	}

}
