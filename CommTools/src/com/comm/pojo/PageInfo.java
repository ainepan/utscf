package com.comm.pojo;

import java.io.Serializable;

public class PageInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8171114812467078628L;
	private String url;
	private int index;
	private int total;
	private String name;
	private String functionId;
	private String pageType;
	private String jsfile;
	private String queryId;
	private boolean isTransaction;
	private int sysTrxTotalPage;
	private int sysTrxPageIndex;
	private String doname;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public String getJsfile() {
		return jsfile;
	}
	public void setJsfile(String jsfile) {
		this.jsfile = jsfile;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public boolean isTransaction() {
		return isTransaction;
	}
	public void setTransaction(boolean isTransaction) {
		this.isTransaction = isTransaction;
	}
	public int getSysTrxTotalPage() {
		return sysTrxTotalPage;
	}
	public void setSysTrxTotalPage(int sysTrxTotalPage) {
		this.sysTrxTotalPage = sysTrxTotalPage;
	}
	public int getSysTrxPageIndex() {
		return sysTrxPageIndex;
	}
	public void setSysTrxPageIndex(int sysTrxPageIndex) {
		this.sysTrxPageIndex = sysTrxPageIndex;
	}
	public String getDoname() {
		return doname;
	}
	public void setDoname(String doname) {
		this.doname = doname;
	}
}
