package com.ut.comm.xml.logicflow;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class LogicNode extends AbsObject{
	
	public static final String LOGIC_TYPE_ENTITY="E";
	public static final String LOGIC_TYPE_SQL="S";
	public static final String LOGIC_TYPE_COMPONENT="C";

	public LogicNode() {
		super();
	}
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
	 * 是否需要联动触发Event表
	 * Y 表示需要,其他值表示不触发
	 */
	private String event;
	/**
	 * 查询的栏位
	 */
	private String fields;
	
	private String releaseFields;
	/**
	 * 查询的条件
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
	
	private String module;
	
	private String sql;
	
	private String params;
	
	private String nodejs;
	
	private String isMainLogic;
	
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getReleaseFields() {
		return releaseFields;
	}

	public void setReleaseFields(String releaseFields) {
		this.releaseFields = releaseFields;
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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getNodejs() {
		return nodejs;
	}

	public void setNodejs(String nodejs) {
		this.nodejs = nodejs;
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_LOGIC_NODE;
	}

	public String getIsMainLogic() {
		return isMainLogic;
	}

	public void setIsMainLogic(String isMainLogic) {
		this.isMainLogic = isMainLogic;
	}
	
	public boolean isMainLogic(){
		return "Y".equalsIgnoreCase(this.isMainLogic);
	}
}
