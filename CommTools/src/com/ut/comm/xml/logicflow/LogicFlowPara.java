package com.ut.comm.xml.logicflow;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class LogicFlowPara extends AbsObject{

	public LogicFlowPara() {
		XMLParaHelper.registeObjectBean("lnode", LogicNode.class);
		XMLParaHelper.registeObjectBean("transfornode", TransforNode.class);
	}
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String desc;
	
	private String module;

	private List<LogicNode> lnodeList;
	
	private List<TransforNode> transforList;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public void setTransforList(List<TransforNode> transforList) {
		this.transforList = transforList;
	}
	
	public TransforNode getTransforNode(int index) {
		TransforNode queryNode = transforList.get(index);
		return queryNode;
	}
	public void setTransfornode(TransforNode logic,int index) {
		if(this.transforList==null)
			this.transforList = new ArrayList<TransforNode>();
		if(index>this.transforList.size())
			this.transforList.add(logic);
	}
	
	public void setTransfornode(TransforNode logic) {
		if(this.transforList==null)
			this.transforList = new ArrayList<TransforNode>();
		this.transforList.add(logic);
	}
	
	public int getTransforSize(){
		return this.transforList==null?0:this.transforList.size();
	}
	
	public LogicNode getLnode(int index) {
		LogicNode queryNode = lnodeList.get(index);
		return queryNode;
	}
	public void setLnode(LogicNode logic,int index) {
		if(this.lnodeList==null)
			this.lnodeList = new ArrayList<LogicNode>();
		if(index>this.lnodeList.size())
			this.lnodeList.add(logic);
	}
	
	public void setLnode(LogicNode logic) {
		if(this.lnodeList==null)
			this.lnodeList = new ArrayList<LogicNode>();
		this.lnodeList.add(logic);
	}
	
	public int getNodeSize(){
		return this.lnodeList==null?0:this.lnodeList.size();
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_LOGICFLOW;
	}

	public List<LogicNode> getLnodeList() {
		return lnodeList;
	}
}
