package com.ut.comm.xml.esb;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

@Deprecated
public class ESBServiceObj extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String INBOUND = "inbound";
	private final String LOGICFLOW = "Logicflow";
	
	public ESBServiceObj(){
		XMLParaHelper.registeObjectBean(INBOUND, Inbound.class);
		XMLParaHelper.registeObjectBean(LOGICFLOW, Logicflow.class);
	}

	private String name;
	private String type;
	private Inbound inbound;
	private Logicflow logicflow;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Inbound getInbound() {
		return inbound;
	}

	public void setInbound(Inbound inbound) {
		this.inbound = inbound;
	}

	public Logicflow getLogicflow() {
		return logicflow; 
	}

	public void setLogicflow(Logicflow logicflow) {
		this.logicflow = logicflow;
	}

	@Override
	public String getNodeName() {
		return "esbservice";
	}

}
