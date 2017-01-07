package com.ut.comm.xml.page;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.ref.RefPara;

public class PagePara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PagePara(){
		XMLParaHelper.registeObjectBean("refno", RefPara.class);
	} 

	private String name;
	private String desc;
	private String jspfile;
	private String cascadeevent;
	private String lockcheck;
	private String holdmaster;
	private String pagetype;
	private String maincomp;
	private String maintable;
	private String queryid;
	private String index;
	private String logicid;
	private String doname;
	private String isMainPage;
	
	private String gapi;
	private String istransaction;
	private String catalog;
	
	private String jsfile;
	private List<RefPara> refList;
	private String pagejs;
	private String accountingjs;
	
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

	public String getJspfile() {
		return jspfile;
	}

	public void setJspfile(String jspfile) {
		this.jspfile = jspfile;
	}

	public String getCascadeevent() {
		return cascadeevent;
	}

	public void setCascadeevent(String cascadeevent) {
		this.cascadeevent = cascadeevent;
	}

	public String getLockcheck() {
		return lockcheck;
	}

	public void setLockcheck(String lockcheck) {
		this.lockcheck = lockcheck;
	}

	public String getHoldmaster() {
		return holdmaster;
	}

	public void setHoldmaster(String holdmaster) {
		this.holdmaster = holdmaster;
	}

	public String getMaincomp() {
		return maincomp;
	}

	public void setMaincomp(String maincomp) {
		this.maincomp = maincomp;
	}

	public String getGapi() {
		return gapi;
	}

	public void setGapi(String gapi) {
		this.gapi = gapi;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getQueryid() {
		return queryid;
	}

	public void setQueryid(String queryid) {
		this.queryid = queryid;
	}

	public String getLogicid() {
		return logicid;
	}

	public void setLogicid(String logicid) {
		this.logicid = logicid;
	}
	public String getMaintable() {
		return maintable;
	}

	public void setMaintable(String maintable) {
		this.maintable = maintable;
	}

	public String getIstransaction() {
		return istransaction;
	}
	
	public boolean isTransaction() {
		return "y".equalsIgnoreCase(istransaction);
	}

	public void setIstransaction(String istransaction) {
		this.istransaction = istransaction;
	}

	/**
	 * 目前定义Add Edit Delete
	 * @see ComponentConst.COMP_PAGE_TYPE_*
	 * @return 
	 */
	public String getPagetype() {
		return pagetype;
	}

	public void setPagetype(String pagetype) {
		this.pagetype = pagetype;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getJsfile() {
		return jsfile;
	}

	public void setJsfile(String jsfile) {
		this.jsfile = jsfile;
	}
	
	public void setRefList(List<RefPara> refList) {
		this.refList = refList;
	}
	
	public RefPara getRefPara(int index) {
		RefPara refPara = refList.get(index);
		return refPara;
	}
	
	public void setRefno(RefPara ref,int index) {
		if(this.refList==null)
			this.refList = new ArrayList<RefPara>();
		if(index>this.refList.size())
			this.refList.add(ref);
	}
	
	public void setRefno(RefPara ref) {
		if(this.refList==null)
			this.refList = new ArrayList<RefPara>();
		this.refList.add(ref);
	}
	
	public int getRefsSize(){
		return this.refList==null?0:this.refList.size();
	}

	public String getDoname() {
		return doname;
	}

	public void setDoname(String doname) {
		this.doname = doname;
	}

	public String getPagejs() {
		return pagejs;
	}

	public void setPagejs(String pagejs) {
		this.pagejs = pagejs;
	}

	public String getAccountingjs() {
		return accountingjs;
	}

	public void setAccountingjs(String accountingjs) {
		this.accountingjs = accountingjs;
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_PAGE;
	}

	public String getIsMainPage() {
		return isMainPage;
	}

	public void setIsMainPage(String isMainPage) {
		this.isMainPage = isMainPage;
	}
	
	public boolean isMainPage(){
		return "Y".equalsIgnoreCase(this.isMainPage);
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		boolean b = obj instanceof PagePara;
		if (b) {
			PagePara u = (PagePara) obj;
			if (this.getId().equals(u.getId()) && this.name.equals(u.name)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
