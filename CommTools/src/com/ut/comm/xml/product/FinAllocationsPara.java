package com.ut.comm.xml.product;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class FinAllocationsPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FinAllocationsPara(){
		XMLParaHelper.registeObjectBean("finallocation", FinAllocationPara.class);
	}

	@Override
	public String getNodeName() {
		return "finallocations";
	}

	private List<FinAllocationPara> finallocations;

	public List<FinAllocationPara> getFinallocations() {
		return finallocations;
	}

	public void setFinallocation(FinAllocationPara finSources) {
		if(this.finallocations==null)
			this.finallocations = new ArrayList<FinAllocationPara>();
		this.finallocations.add(finSources);
	}
	
	public FinAllocationPara getFinallocation(int index) {
		FinAllocationPara finPara = finallocations.get(index);
		return finPara;
	}
	public void setFinallocation(FinAllocationPara logic,int index) {
		if(this.finallocations==null)
			this.finallocations = new ArrayList<FinAllocationPara>();
		if(index>this.finallocations.size())
			this.finallocations.add(logic);
	}
	
	public int getFinAllocationSize(){
		return this.finallocations==null?0:this.finallocations.size();
	}
}
