package com.ut.comm.xml.trx;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class TrxDataPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_TRX_DATA_OBJ;
	}
	
	@Override
	public boolean isPropertyBean(){
		return true;
	}
}
