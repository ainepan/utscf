package com.ut.comm.xml.product;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class FinAllocationPara extends AbsObject{

	/**
	 * 		<id>10001</id>
			<type>P</type>
			<finsource> 10001</finsource>
			<finreceiptor value="40">20001</finreceiptor>
			<finreceiptor value="60">20002</finreceiptor>
	 */
	private static final long serialVersionUID = 1L;
	
	public FinAllocationPara (){
		XMLParaHelper.registeObjectBean("finreceiptor", FinReceiptorPara.class);
	}

	@Override
	public String getNodeName() {
		return "finallocation";
	}
	
	private String type;
	private String finsinglesource;
	private String status;
	
	private List<FinReceiptorPara> finreceiptor;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<FinReceiptorPara> getFinreceiptor() {
		return finreceiptor;
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

	public String getFinsinglesource() {
		return finsinglesource;
	}

	public void setFinsinglesource(String finsinglesource) {
		this.finsinglesource = finsinglesource;
	}
	
}
