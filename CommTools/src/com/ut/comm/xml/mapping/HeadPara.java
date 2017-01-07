package com.ut.comm.xml.mapping;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.template.MappingField;

public class HeadPara extends AbsObject{
	public HeadPara(){
		super();
		XMLParaHelper.registeObjectBean("mfield", MappingField.class);
	}

	
	private static final long serialVersionUID = 1L;
	
	private String name;
		
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

	@Override
	public String getNodeName() {
		return "head";
	}	
}
