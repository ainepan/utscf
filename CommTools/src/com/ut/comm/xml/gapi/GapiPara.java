package com.ut.comm.xml.gapi;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

/**
 * @see 定义协议层属性
 * @author PanHao
 *
 */
public class GapiPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Override
	public boolean isPropertyBean(){
		return true;
	}
	
	public GapiPara() {
		
	}

	@Override
	public String getNodeName() {
		// TODO Auto-generated method stub
		return "gapi";
	}
	
}
