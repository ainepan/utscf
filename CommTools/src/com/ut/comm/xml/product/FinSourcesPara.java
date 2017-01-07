package com.ut.comm.xml.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class FinSourcesPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FinSourcesPara(){
		XMLParaHelper.registeObjectBean("finsource", FinSourcePara.class);
	}

	@Override
	public String getNodeName() {
		return "finsources";
	}

	private List<FinSourcePara> finSources;
	
	public List<FinSourcePara> getFinSources() {
		FinSourceComparator comparator = new FinSourceComparator();
		Collections.sort(finSources, comparator);
		return finSources;
	}
	public void setFinsource(FinSourcePara finSources) {
		if(this.finSources==null)
			this.finSources = new ArrayList<FinSourcePara>();
		this.finSources.add(finSources);
	}
	
	public FinSourcePara getFinSource(int index) {
		FinSourceComparator comparator = new FinSourceComparator();
		Collections.sort(finSources, comparator);
		FinSourcePara finPara = finSources.get(index);
		return finPara;
	}
	public void setFinsource(FinSourcePara logic,int index) {
		if(this.finSources==null)
			this.finSources = new ArrayList<FinSourcePara>();
		if(index>this.finSources.size())
			this.finSources.add(logic);
	}
	
	public int getFinSourceSize(){
		return this.finSources==null?0:this.finSources.size();
	}
	
	public FinSourcePara getNextFinSource(String index) {
		FinSourceComparator comparator = new FinSourceComparator();
		FinSourcePara currentFS = new FinSourcePara();
		currentFS.setPriority(index);
		Collections.sort(finSources, comparator);
		for (FinSourcePara finSourcePara : finSources) {
			if(comparator.compare(finSourcePara, currentFS)<0||"0".equalsIgnoreCase(finSourcePara.getPriority()))
				return finSourcePara;
		}
		currentFS.setPriority("0");
		currentFS.setId("");
		return currentFS;
	}
	
	public FinSourcePara getFirstFinSource() {
		FinSourceComparator comparator = new FinSourceComparator();
		Collections.sort(finSources, comparator);
		return finSources.get(0);
	}
	
	public FinSourcePara getDefaultFinSource() {
		return getNextFinSource(1+"");
	}
	
	
	private class FinSourceComparator implements Comparator<FinSourcePara>{

		@Override
		public int compare(FinSourcePara o1, FinSourcePara o2) {
			int fs1Priority = Integer.parseInt(o1.getPriority());
			int fs2Priority = Integer.parseInt(o2.getPriority());
			return fs2Priority-fs1Priority;
		}
		
	}
}
