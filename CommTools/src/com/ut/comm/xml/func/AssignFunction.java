package com.ut.comm.xml.func;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class AssignFunction extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_ASSIGNFUNC;
	}

	private String type;
	
	private String js;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}
	
}
