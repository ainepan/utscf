package com.ut.comm.xml.limit;

import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.method.MethodDefinePara;

public class LimitPara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LimitPara() {
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_METHOD_DEFINE, MethodDefinePara.class);
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_LIMIT;
	}
	
	private String name;
	private String desc;
	
	/**
	 * 1 : 标记为额度
	 */
	private String label;
	/**
	 * 0:循环额度
	 * 1：单笔额度
	 */
	private String module;
	
	/**
	 * 0：抵质押类，需要有质押物
	 * 1：信用类，需要有风控引擎
	 */
	private String type;
	
	private String loanrate;
	
	/**
	 * 预警值
	 * 一般设为百分比
	 */
	private String critical;
	
	/**
	 * 0:授信额度
	 * 1：关联额度
	 */
	private String structure;
	
	private String maxamount;
	
	private String riskid;
	
	private String expirydays; 
	
	private String ccy;
	
	
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLoanrate() {
		return loanrate;
	}

	public void setLoanrate(String loanrate) {
		this.loanrate = loanrate;
	}

	public String getCritical() {
		return critical;
	}

	public void setCritical(String critical) {
		this.critical = critical;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getMaxamount() {
		return maxamount;
	}

	public void setMaxamount(String maxamount) {
		this.maxamount = maxamount;
	}

	public String getRiskid() {
		return riskid;
	}

	public void setRiskid(String riskid) {
		this.riskid = riskid;
	}

	public String getExpirydays() {
		return expirydays;
	}

	public void setExpirydays(String expirydays) {
		this.expirydays = expirydays;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public List<MethodDefinePara> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodDefinePara> methods) {
		this.methods = methods;
	}
}
