package com.ut.comm.xml.report;

import java.io.File;

import com.ut.comm.tool.string.StringUtil;
import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.XMLParaLoadHelper;

public class ReportPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2154078835274952555L;

	private String name;
	private String desc;
	/**
	 * P
	 * M
	 */
	private String type;
	/**
	 * XML,Excel,Pdf...
	 */
	private String reporttype;
	private String template;
	private String output;
	private String js;
	private String datasourcetype;
	private String datasource;

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
	public String getReporttype() {
		return reporttype;
	}
	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getDatasourcetype() {
		return datasourcetype;
	}
	public void setDatasourcetype(String datasourcetype) {
		this.datasourcetype = datasourcetype;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	//移到其他地方begin
	private String getReportRootPath(){
		return XMLParaLoadHelper.getApParaDeinePath("report",super.getBu());
	}
	
	private String getReportDesignFile(){
		if(StringUtil.isTrimEmpty(this.template)){
			return "";
		}
		StringBuffer buff = new StringBuffer();
		buff.append(getReportRootPath()).append(File.separator)
		.append("designer").append(File.separator)
		.append(this.template);
		return buff.toString();
	}
	//end
	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_REPORT;
	}
	public String getJs() {
		return js;
	}
	public void setJs(String js) {
		this.js = js;
	}
}
