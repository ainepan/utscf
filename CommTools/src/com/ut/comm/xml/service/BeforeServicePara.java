package com.ut.comm.xml.service;

import com.ut.comm.xml.XMLParaHelper;

public class BeforeServicePara extends ServicePara{
	public BeforeServicePara(){
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_BEFORE_SERVICE+","+XMLParaHelper.NOTE_NAME_SERVICE;
	}
}
