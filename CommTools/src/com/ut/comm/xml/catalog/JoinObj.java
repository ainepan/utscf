package com.ut.comm.xml.catalog;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class JoinObj extends AbsObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JoinObj() {
		super();
		XMLParaHelper.registeObjectBean("on", CatalogOnObj.class);
	}
	

	private List<CatalogOnObj> onList;

	public List<CatalogOnObj> getJoin() {
		return onList;
	}
	
	public CatalogOnObj getOn(int index) {
		CatalogOnObj onObj = onList.get(index);
		return onObj;
	}

	public void setOn(List<CatalogOnObj> on) {
		this.onList = on;
	}
	
	public void setOn(CatalogOnObj join) {
		if(onList==null){
			onList = new ArrayList<CatalogOnObj>();
		}
		onList.add(join);
	}
	
	public int size(){
		return onList==null?0:onList.size();
	}


	@Override
	public String getNodeName() {
		return "join";
	}
}
