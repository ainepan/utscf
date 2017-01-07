package com.ut.comm.xml.catalog;

import com.ut.comm.xml.AbsObject;

public class FieldObj extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private String datatype;
	private String hidden;
	private String field;
	private String width;
	private String checkbox;
	private String formatter;
	private String align;
	private String styler;
	private String distinct;
	private String alis;
	private String sum;
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	private String defaultvalue;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getStyler() {
		return styler;
	}

	public void setStyler(String styler) {
		this.styler = styler;
	}

	public String getDistinct() {
		return distinct;
	}

	public void setDistinct(String distinct) {
		this.distinct = distinct;
	}

	public String getAlis() {
		return alis;
	}

	public void setAlis(String alis) {
		this.alis = alis;
	}

	@Override
	public String getNodeName() {
		return "field";
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

}
