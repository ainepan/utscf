package com.ut.comm.tool.xml;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class DomNodeList implements NodeList {

	
	protected ArrayList arraylist;
	
	public DomNodeList()
	{
		arraylist=new ArrayList();
	}
	
	public void addNode(Node node)
	{
		arraylist.add(node);
	}
	
	public Node item(int index) {
			
		if (index>=arraylist.size())
			return null;
	
		return (Node)arraylist.get(index);
	}

	
	public int getLength() {
		
		return arraylist.size();
	}

}
