package com.ut.comm.xml.query;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class QueryNode  extends AbsObject{
	
	public QueryNode(){
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 实现类名称
	 */
	private String component;
	/**
	 * 操作的表名
	 */
	private String tablename;
	/**
	 * 对应一对多结构数据
	 */
	private String doname;
	
	/**
	 * E:Entity 查询
	 * S:SQL查询
	 * C:Component 查询
	 */
	private String type;
	/**
	 * 是否需要联动触发Event表
	 * Y 表示需要,其他值表示不触发
	 */
	private String cascadeevent;
	/**
	 * 查询的栏位
	 */
	private String fields;
	/**
	 * 查询的调节
	 */
	private String condition;
	/**
	 * 多栏位使用，分割
	 */
	private String orderby;
	/**
	 * default desc 即降序，Y 为asc 即升序
	 */
	private String asc;
	
	private String sql;
	
	private String index;
	
	private String lockcheck;
	
	private String params;
	
	private String groupBy;
	
	private String sumFields;
	
	/**
	 * 分页查询
	 */
//	private Page page;
	
	private String bybu;
	
	/**
	 * 
	 */

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

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

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getDoname() {
		return doname;
	}

	public void setDoname(String doname) {
		this.doname = doname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}

	public String getCascadeevent() {
		return cascadeevent;
	}

	public void setCascadeevent(String cascadeevent) {
		this.cascadeevent = cascadeevent;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getLockcheck() {
		return lockcheck;
	}

	public void setLockcheck(String lockcheck) {
		this.lockcheck = lockcheck;
	}

//	public Page getPage() {
//		return page;
//	}
//
//	public void setPage(Page page) {
//		this.page = page;
//	}

	public String getBybu() {
		return bybu;
	}

	public void setBybu(String bybu) {
		this.bybu = bybu;
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_QUERY_NODE;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getSumFields() {
		return sumFields;
	}

	public void setSumFields(String sumFields) {
		this.sumFields = sumFields;
	}

}
