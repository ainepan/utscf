package com.ut.comm.xml.func;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.tool.string.StringUtil;
import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.XMLParaParseHelper;
import com.ut.comm.xml.page.PagePara;
import com.ut.comm.xml.report.ReportPara;
import com.ut.comm.xml.service.BeforeServicePara;
import com.ut.comm.xml.service.ServicePara;

public class FunctionPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FunctionPara(){
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_PAGE, PagePara.class);
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_REPORT, ReportPara.class);
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_SERVICE, ServicePara.class);
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_BEFORE_SERVICE, BeforeServicePara.class);
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_ASSIGNFUNC, AssignFunction.class);
	}

	private String name;
	private String shortnm;
	private String desc;
	private String module;
	private String functype;
	private String template;
	private List<PagePara> pageList;
	private List<ReportPara> reportList;
	private String workflow;
	private List<ServicePara> bfServiceList;
	private List<ServicePara> serviceList;
	private String isValidCode;
	private List<AssignFunction> autoassignList;
	private String autoassign;
	private String funjs;
	private String doname;

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

	public PagePara getPage(int index) {
		PagePara pagePara = pageList.get(index);
		pagePara = XMLParaParseHelper.parsePage(pagePara,super.getBu());
		return pagePara;
	}
	
	public PagePara getTrxPage(int index) {
		if(pageList.isEmpty())
			return null;
		int match = -1;
		for (int i = 0; i < pageList.size(); i++) {
			PagePara pagePara = getPage(i);
			if(pagePara.isTransaction()){
				match++;
			}
			if(match == index)
				return pagePara;
		}

		return null;
	}
	
	public void setPage(PagePara page,int index) {
		if(this.pageList==null)
			this.pageList = new ArrayList<PagePara>();
		if(index>this.pageList.size())
			this.pageList.add(page);
	}
	
	public void setPage(PagePara page) {
		if(this.pageList==null)
			this.pageList = new ArrayList<PagePara>();
		this.pageList.add(page);
	}
	
	public int getPagesSize(){
		return this.pageList==null?0:this.pageList.size();
	}
	public String getFunctype() {
		return functype;
	}
	public void setFunctype(String functype) {
		this.functype = functype;
	}

	public int getTransactionPage(){
		int totalPage = 0;
		for (int i = 0; i < getPagesSize(); i++) {
			//是否有必要重新解析，待分析
			if(this.getPage(i).isTransaction()){
				totalPage++;
			}
		}
		return totalPage;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public ReportPara getReport(int index) {
		ReportPara reportPara = reportList.get(index);
		XMLParaParseHelper.parseReport(reportPara,super.getBu());
		return reportPara;
	}
	
	public void setReport(ReportPara report,int index) {
		if(this.reportList==null)
			this.reportList = new ArrayList<ReportPara>();
		if(index>this.reportList.size())
			this.reportList.add(report);
	}
	
	public void setReport(ReportPara report) {
		if(this.reportList==null)
			this.reportList = new ArrayList<ReportPara>();
		this.reportList.add(report);
	}
	
	public int getReportsSize(){
		return this.reportList==null?0:this.reportList.size();
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
	
	public ServicePara getService(int index) {
		ServicePara servicePara = serviceList.get(index);
		XMLParaParseHelper.parseFuncService(servicePara,super.getBu());
		return servicePara;
	}
	
	public void setService(ServicePara service,int index) {
		if(this.serviceList==null)
			this.serviceList = new ArrayList<ServicePara>();
		if(index>this.serviceList.size())
			this.serviceList.add(service);
	}
	
	public void setService(ServicePara service) {
		if(this.serviceList==null)
			this.serviceList = new ArrayList<ServicePara>();
		this.serviceList.add(service);
	}
	
	public int getServiceSize(){
		return this.serviceList==null?0:this.serviceList.size();
	}
	
	public ServicePara getBfservice(int index) {
		ServicePara servicePara = bfServiceList.get(index);
		XMLParaParseHelper.parseFuncService(servicePara,super.getBu());
		return servicePara;
	}
	
	public void setBfservice(ServicePara service,int index) {
		if(this.bfServiceList==null)
			this.bfServiceList = new ArrayList<ServicePara>();
		if(index>this.bfServiceList.size())
			this.bfServiceList.add(service);
	}
	
	public void setBfservice(ServicePara service) {
		if(this.bfServiceList==null)
			this.bfServiceList = new ArrayList<ServicePara>();
		this.bfServiceList.add(service);
	}
	
	public int getBfServiceSize(){
		return this.bfServiceList==null?0:this.bfServiceList.size();
	}


	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_FUNCTION;
	}
	public List<PagePara> getPageList() {
		return pageList;
	}
	public List<ReportPara> getReportList() {
		return reportList;
	}
	public List<ServicePara> getServiceList() {
		return serviceList;
	}
	public List<ServicePara> getBfServiceList() {
		return bfServiceList;
	}
	public String getIsValidCode() {
		return isValidCode;
	}
	public void setIsValidCode(String isValidCode) {
		this.isValidCode = isValidCode;
	}
	public String getAutoassign() {
		return autoassign;
	}
	public void setAutoassign(String autoassign) {
		this.autoassign = autoassign;
	}
	
	public AssignFunction getAutoassign(int index) {
		AssignFunction servicePara = autoassignList.get(index);
		return servicePara;
	}
	
	public void setAutoassign(AssignFunction service,int index) {
		if(this.autoassignList==null)
			this.autoassignList = new ArrayList<AssignFunction>();
		if(index>this.autoassignList.size())
			this.autoassignList.add(service);
	}
	
	public void setAutoassign(AssignFunction service) {
		if(this.autoassignList==null)
			this.autoassignList = new ArrayList<AssignFunction>();
		this.autoassignList.add(service);
	}
	
	public int getAutoassignSize(){
		return this.autoassignList==null?0:this.autoassignList.size();
	}
	public String getShortnm() {
		if(StringUtil.isTrimEmpty(shortnm))
			return this.name;
		return shortnm;
	}
	public void setShortnm(String shortnm) {
		this.shortnm = shortnm;
	}
	public String getFunjs() {
		return funjs;
	}
	public void setFunjs(String funjs) {
		this.funjs = funjs;
	}
	public String getDoname() {
		return doname;
	}
	public void setDoname(String doname) {
		this.doname = doname;
	}
}
