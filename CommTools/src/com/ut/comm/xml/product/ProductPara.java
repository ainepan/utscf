package com.ut.comm.xml.product;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class ProductPara extends AbsObject{
	
	public ProductPara() {
		XMLParaHelper.registeObjectBean("finsources", FinSourcesPara.class);
		XMLParaHelper.registeObjectBean("finreceiptors", FinReceiptorsPara.class);
		XMLParaHelper.registeObjectBean("finallocations", FinAllocationsPara.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924021959457366586L;

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_PRODUCT;
	}
	
	private String name;
	private String desc;
	private String type;
	private String status;
	private String fintp;
	private String finfrec;
	private FinSourcesPara finsources;   //资金优先级
	private String finsinglesource;//暂时没用（单资金来源时）
	private FinReceiptorsPara finreceiptors;//暂时没用（分润方）
	private FinAllocationsPara finallocations;//分润
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
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
	public String getFintp() {
		return fintp;
	}
	public void setFintp(String fintp) {
		this.fintp = fintp;
	}
	public String getFinfrec() {
		return finfrec;
	}
	public void setFinfrec(String finfrec) {
		this.finfrec = finfrec;
	}
	public FinSourcesPara getFinsources() {
		return finsources;
	}
	public void setFinsources(FinSourcesPara finsources) {
		this.finsources = finsources;
	}
	public FinReceiptorsPara getFinreceiptors() {
		return finreceiptors;
	}
	public void setFinreceiptors(FinReceiptorsPara finreceiptors) {
		this.finreceiptors = finreceiptors;
	}
	public FinAllocationsPara getFinallocations() {
		return finallocations;
	}
	public void setFinallocations(FinAllocationsPara finallocations) {
		this.finallocations = finallocations;
	}
	public String getFinsinglesource() {
		return finsinglesource;
	}
	public void setFinsinglesource(String finsinglesource) {
		this.finsinglesource = finsinglesource;
	}
	
	public boolean isPropertyBean(){
		return true;
	}
	
}
