package com.ut.comm.xml.mapping;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.template.MappingField;

public class MappingPara extends AbsObject{
	public MappingPara(){
		super();
		XMLParaHelper.registeObjectBean("mfield", MappingField.class);
	}

	private static final long serialVersionUID = 1L;
	
	private String name;
	private Integer beginRow;
	private Integer endRow;
	private Integer beginColumn;
	private Integer endColumn;
	
	
	private List<MappingField> filedsObj;
	
	public void setMfield(MappingField filedObj) {
		if(filedsObj ==null)
			filedsObj = new ArrayList<MappingField>();
		filedsObj.add(filedObj);
	}
	
	public MappingField getFieldObj(int index) {
		MappingField fieldObj =filedsObj.get(index);
		return fieldObj;
	}

	public int size(){
		return filedsObj.size();
	}

	public List<MappingField> getSearch(){
		return filedsObj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBeginRow() {
		return beginRow;
	}

	public void setBeginRow(Integer beginRow) {
		this.beginRow = beginRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public Integer getBeginColumn() {
		return beginColumn;
	}

	public void setBeginColumn(Integer beginColumn) {
		this.beginColumn = beginColumn;
	}

	public Integer getEndColumn() {
		return endColumn;
	}

	public void setEndColumn(Integer endColumn) {
		this.endColumn = endColumn;
	}

	@Override
	public String getNodeName() {
		return "mapping";
	}		
}