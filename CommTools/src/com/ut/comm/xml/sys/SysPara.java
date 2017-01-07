package com.ut.comm.xml.sys;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class SysPara extends AbsObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1865078744895197903L;

	public SysPara(){
		super();
		XMLParaHelper.registeObjectBean("mappingschema", SchemaMapping.class);
	} 
	
	private String bankid;
	private String uploadtempdir;
	private String downloadtempdir;
	private String reportengine;
	
	private SchemaMapping schemamapping;
	
	private String workflowtype;
	private String workflowengine;
	private String workflowport;
	private String workflowpassword;
	private String workflowflag;
	private String singleSignOn;
	private String logoUrl;
	private String pageStyle;
	private String busiUnit;
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getPageStyle() {
		return pageStyle;
	}

	public void setPageStyle(String pageStyle) {
		this.pageStyle = pageStyle;
	}

	/*public boolean getSingleSignOn() {
		return "true".equalsIgnoreCase(singleSignOn);
	}*/
	public String getSingleSignOn() {
		return singleSignOn;
	}

	public void setSingleSignOn(String singleSignOn) {
		this.singleSignOn = singleSignOn;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public SchemaMapping getSchemamapping() {
		return schemamapping;
	}

	public void setMappingschema(SchemaMapping schemamapping) {
		this.schemamapping = schemamapping;
	}

	public String getUploadtempdir() {
		return uploadtempdir;
	}

	public void setUploadtempdir(String uploadtempdir) {
		this.uploadtempdir = uploadtempdir;
	}

	public String getReportengine() {
		return reportengine;
	}

	public void setReportengine(String reportengine) {
		this.reportengine = reportengine;
	}

	public String getWorkflowtype() {
		return workflowtype;
	}

	public void setWorkflowtype(String workflowtype) {
		this.workflowtype = workflowtype;
	}

	public String getWorkflowengine() {
		return workflowengine;
	}

	public void setWorkflowengine(String workflowengine) {
		this.workflowengine = workflowengine;
	}

	public String getWorkflowport() {
		return workflowport;
	}

	public void setWorkflowport(String workflowport) {
		this.workflowport = workflowport;
	}

	public String getWorkflowpassword() {
		return workflowpassword;
	}

	public void setWorkflowpassword(String workflowpassword) {
		this.workflowpassword = workflowpassword;
	}

	/*public boolean getWorkflowflag() {
		return "on".equalsIgnoreCase(workflowflag);
	}*/

	public String  getWorkflowflag() {
		return workflowflag;
	}
	public void setWorkflowflag(String workflowflag) {
		this.workflowflag = workflowflag;
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_SYST;
	}
	
	public boolean isPropertyBean(){
		return true;
	}
	public String getDownloadtempdir() {
		return downloadtempdir;
	}
	public void setDownloadtempdir(String downloadtempdir) {
		this.downloadtempdir = downloadtempdir;
	}
	public String getBusiUnit() {
		return busiUnit;
	}
	public void setBusiUnit(String busiUnit) {
		this.busiUnit = busiUnit;
	}
	
	
	
}
