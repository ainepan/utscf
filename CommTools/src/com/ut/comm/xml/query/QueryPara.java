package com.ut.comm.xml.query;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class QueryPara  extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public QueryPara(){
		super();
		XMLParaHelper.registeObjectBean("qnode", QueryNode.class);
	}

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * Query 查询节点
	 */
	private List<QueryNode> qnodeList;

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

//	public void setNodeList(List<QueryNode> nodeList) {
//		this.qnodeList = nodeList;
//	}
	
	public QueryNode getNode(int index) {
		QueryNode queryNode = qnodeList.get(index);
		return queryNode;
	}
//	public void setQnode(QueryNode page,int index) {
//		if(this.qnodeList==null)
//			this.qnodeList = new ArrayList<QueryNode>();
//		if(index>this.qnodeList.size())
//			this.qnodeList.add(page);
//	}
	
	public void setQnode(QueryNode page) {
		if(this.qnodeList==null)
			this.qnodeList = new ArrayList<QueryNode>();
		this.qnodeList.add(page);
	}
	
	public int getNodeSize(){
		return this.qnodeList==null?0:this.qnodeList.size();
	}

	@Override
	public String getNodeName() {
		return  XMLParaHelper.NOTE_NAME_QUERY;
	}

	public List<QueryNode> getQnodeList() {
		return qnodeList;
	}
}
