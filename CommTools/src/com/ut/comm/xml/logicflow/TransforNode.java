package com.ut.comm.xml.logicflow;

import com.ut.comm.xml.XMLParaHelper;

public class TransforNode extends LogicNode{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TransforNode() {
		super();
		XMLParaHelper.registeObjectBean("pending", TransforField.class);
		XMLParaHelper.registeObjectBean("delepending", TransforField.class);
		XMLParaHelper.registeObjectBean("release", TransforField.class);
	}
	private TransforField pending;
	
	private TransforField delepending;
	
	private TransforField release;

	public TransforField getPending() {
		if(pending==null)
			return null;
		return pending;
	}

	public void setPending(TransforField pending) {
		this.pending = pending;
	}

	public TransforField getRelease() {
		if(release==null)
			return null;
		return release;
	}

	public void setRelease(TransforField release) {
		this.release = release;
	}
	
	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_LOGIC_TRANS_NODE;
	}

	public TransforField getDelepending() {
		return delepending;
	}

	public void setDelepending(TransforField delepending) {
		this.delepending = delepending;
	}
}
