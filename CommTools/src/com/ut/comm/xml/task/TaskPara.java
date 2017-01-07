package com.ut.comm.xml.task;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.trx.TrxDataPara;

public class TaskPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TaskPara(){
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_TRX_DATA_OBJ, TrxDataPara.class);
	}

	private String name;
	private String desc;
	private String component;
	private String sqlcondition;
	private String type;
	private String gapiid;
	private String js;
	private String catalog;
	private String singlethread;
	private String jspfile;
	private TrxDataPara trxdatapara;
	private List<String> rules;

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
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getSqlcondition() {
		return sqlcondition;
	}
	public void setSqlcondition(String sqlcondition) {
		this.sqlcondition = sqlcondition;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGapiid() {
		return gapiid;
	}
	public void setGapiid(String gapiid) {
		this.gapiid = gapiid;
	}
	public String getJs() {
		return js;
	}
	public void setJs(String js) {
		this.js = js;
	}
	@Override
	public String getNodeName() {
		return "task";
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getSinglethread() {
		return singlethread;
	}
	public void setSinglethread(String singlethread) {
		this.singlethread = singlethread;
	}
	public String getJspfile() {
		return jspfile;
	}
	public void setJspfile(String jspfile) {
		this.jspfile = jspfile;
	}
	public TrxDataPara getTrxdatapara() {
		return trxdatapara;
	}
	public void setTrxdatapara(TrxDataPara trxdatapara) {
		this.trxdatapara = trxdatapara;
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
}
