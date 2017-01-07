package com.ut.comm.xml.service;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class ServicePara extends AbsObject{
	public ServicePara(){
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	/**
	 * 服务类型:
	 * EDI
	 * Report
	 * Loan
	 * limits
	 * ...
	 */
	private String type;
	private String component;
	private String gapiid;
	private String templateid;
	private String servicejs;
	private List<String> rules;
	private String methodname;
	private String trigger;
	private String onsuccess;
	private String onfailed;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getServicejs() {
		return servicejs;
	}

	public void setServicejs(String servicejs) {
		this.servicejs = servicejs;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public List<String> getRules() {
		return rules;
	}

	public void setRule(String rule) {
		if(this.rules==null)
			this.rules = new ArrayList<String>();
		this.rules.add(rule);
	}
	
	public String getRule(int index) {
		if(rules==null||rules.isEmpty()){
			return "";
		}
		return rules.get(index);
	}
	
	public int getRuleSize(){
		return this.rules==null?0:this.rules.size();
	}

	/**
	 * @see gapi msg id
	 * @return
	 */
	public String getGapiid() {
		return gapiid;
	}

	public void setGapiid(String gapiid) {
		this.gapiid = gapiid;
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_SERVICE;
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getOnsuccess() {
		return onsuccess;
	}

	public void setOnsuccess(String onsuccess) {
		this.onsuccess = onsuccess;
	}

	public String getOnfailed() {
		return onfailed;
	}

	public void setOnfailed(String onfailed) {
		this.onfailed = onfailed;
	}
	
	
}
