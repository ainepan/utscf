package com.ut.comm.xml.product;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class FinReceiptorsPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FinReceiptorsPara() {
		XMLParaHelper.registeObjectBean("finreceiptor", FinReceiptorPara.class);
	}

	@Override
	public String getNodeName() {
		return "finreceiptors";
	}
	
	private List<FinReceiptorPara> finreceiptor;

	public List<FinReceiptorPara> getFinreceiptor() {
		return finreceiptor;
	}

	public void setFinreceiptor(List<FinReceiptorPara> finreceiptor) {
		this.finreceiptor = finreceiptor;
	}
	

	public void setFinreceiptor(FinReceiptorPara finreceiptor) {
		if(this.finreceiptor==null)
			this.finreceiptor = new ArrayList<FinReceiptorPara>();
		this.finreceiptor.add(finreceiptor);
	}
	
	public FinReceiptorPara getFinreceiptor(int index) {
		FinReceiptorPara finPara = finreceiptor.get(index);
		return finPara;
	}
	public void setFinreceiptor(FinReceiptorPara logic,int index) {
		if(this.finreceiptor==null)
			this.finreceiptor = new ArrayList<FinReceiptorPara>();
		if(index>this.finreceiptor.size())
			this.finreceiptor.add(logic);
	}
	
	public int getFinreceiptorSize(){
		return this.finreceiptor==null?0:this.finreceiptor.size();
	}
}
