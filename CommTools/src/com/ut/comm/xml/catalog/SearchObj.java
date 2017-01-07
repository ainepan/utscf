package com.ut.comm.xml.catalog;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class SearchObj extends AbsObject{
	public SearchObj(){
		XMLParaHelper.registeObjectBean("field", FieldObj.class);
	}

	private static final long serialVersionUID = 1L;
	
	private List<FieldObj> filedsObj;
	
	public void setField(FieldObj filedObj) {
		if(filedsObj ==null)
			filedsObj = new ArrayList<FieldObj>();
		filedsObj.add(filedObj);
	}
	
	public FieldObj getFieldObj(int index) {
		FieldObj fieldObj =filedsObj.get(index);
		return fieldObj;
	}

	public int size(){
		return filedsObj.size();
	}

	public List<FieldObj> getSearch(){
		return filedsObj;
	}

	@Override
	public String getNodeName() {
		return  "search";
	}
	
}
