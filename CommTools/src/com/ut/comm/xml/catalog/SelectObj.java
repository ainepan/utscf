package com.ut.comm.xml.catalog;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class SelectObj extends AbsObject{
	public SelectObj(){
		super();
		XMLParaHelper.registeObjectBean("field", FieldObj.class);
	}

	private static final long serialVersionUID = 1L;
	
	private List<FieldObj> filedsObj;
	
	private String groupby;
	
	public void setField(FieldObj filedObj) {
		if(filedsObj ==null)
			filedsObj = new ArrayList<FieldObj>();
		filedsObj.add(filedObj);
	}
	
	public FieldObj getFieldObj(int index) {
		FieldObj fieldObj = filedsObj.get(index);
		return fieldObj;
	}

	public int size(){
		return filedsObj.size();
	}
	
	public List<FieldObj> getSelect(){
		return filedsObj;
	}

	@Override
	public String getNodeName() {
		return  "select";
	}

	public String getGroupby() {
		return groupby;
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}
	
}
