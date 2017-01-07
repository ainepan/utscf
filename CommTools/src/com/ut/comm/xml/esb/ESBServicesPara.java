package com.ut.comm.xml.esb;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class ESBServicesPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ESBServicesPara(){
		super();
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_ESB_SERVICE, ESBServicePara.class);
	}
	
	private List<ESBServicePara> esbserviceList;
	
	public void setEsbservice(ESBServicePara filedObj) {
		if(esbserviceList ==null)
			esbserviceList = new ArrayList<ESBServicePara>();
		esbserviceList.add(filedObj);
	}
	
	public ESBServicePara getEsbservice(int index) {
		ESBServicePara serviceObj = esbserviceList.get(index);
//		XMLParaParseHelper.parseESBSvicePara(serviceObj);
		return serviceObj;
	}

	public int size(){
		if(esbserviceList ==null)
			esbserviceList = new ArrayList<ESBServicePara>();
		return esbserviceList.size();
	}
	
	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_ESB_SERVICES;
	}

	public List<ESBServicePara> getEsbserviceList() {
		return esbserviceList;
	}

	public void setEsbserviceList(List<ESBServicePara> esbserviceList) {
		this.esbserviceList = esbserviceList;
	}
}
