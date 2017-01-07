package com.ut.comm.xml.catalog;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class CatalogPara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CatalogPara() {
		super();
		XMLParaHelper.registeObjectBean("select", SelectObj.class);
		XMLParaHelper.registeObjectBean("search", SearchObj.class);
		XMLParaHelper.registeObjectBean("join", JoinObj.class);

	}

	private String id;
	private String name;
	private String jspfile;
	private String tablename;
	private SelectObj select;
	private String condition;
	private String bybu;

	private SearchObj search;

	private String checkMethod;
	
	private String orderby;
	private String asc;
	private String params;

	private JoinObj join;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJspfile() {
		return jspfile;
	}

	public void setJspfile(String jspfile) {
		this.jspfile = jspfile;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public int size() {
		return select.size();
	}

	public SelectObj getSelect() {
		return select;
	}

	public void setSelect(SelectObj select) {
		this.select = select;
		// this.select.parseXml(currentNode);
	}

	public int getSelectSize() {
		return this.select == null ? 0 : this.select.size();
	}

	public int getSearchSize() {
		return this.search == null ? 0 : this.search.size();
	}

	public SearchObj getSearch() {
		return search;
	}

	public void setSearch(SearchObj search) {
		this.search = search;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public JoinObj getJoin() {
		return join;
	}

	public void setJoin(JoinObj join) {
		this.join = join;
	}

	public String getBybu() {
		return bybu;
	}

	public void setBybu(String bybu) {
		this.bybu = bybu;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}

	public String getCheckMethod() {
		return checkMethod;
	}

	public void setCheckMethod(String checkMethod) {
		this.checkMethod = checkMethod;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_CATALOG;
	}
}
