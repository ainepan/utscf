package com.ut.comm.tool.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLXpathParser {

	protected Document document=null;
	
	
	public XMLXpathParser(){
		//construction
	}
	
	public XMLXpathParser(String filepath)
	{
		DOMParser parser = new DOMParser();
		try
		{
			parser.parse(filepath);
			document=parser.getDocument();
		}catch(Exception e)
		{
		}
	}
	
	public XMLXpathParser(Document document)
	{
		this.document=document;
	}
	
	/**
	 * find the first node.
	 */
	private  Node findsingleNode(Node node,XpathParser xp,int pos) {
		
		if (node==null) return null;
		Map attmap=xp.currParAttributeList;
		Map valuemap=xp.currParValueList;
		String curNodeName=xp.currentParameterValue;
		int count=0;
		String curSeqValue=xp.curSequenceValue;
		int seqNum=-2;/*find validated first node*/
		
		if (node.getNodeName().equals(curNodeName))
		{
			if ("last()".equalsIgnoreCase(curSeqValue)){
				if (!isLastNode(node)){
					return null;
				}
			}else if (curSeqValue!=null){
				seqNum=Integer.parseInt(curSeqValue); /*the appointed node*/
				if (seqNum!=pos){
					return null;
				}
			}
			
			
			/**parse attribute and node value, if one of then is wrong ,will break parse.**/
			if ((!checkAttribute(node,attmap))||((!checkNodeValue(node,valuemap))))	return null;

			/** if parse is not finish ,contiue parse.**/
			
			if (xp.nextParameter()!=null)
			{
					Node child=node.getFirstChild();
					//Node lastChild=getLastChildNode(node);
					HashMap nodeCount=new HashMap();
					while(child!=null)
					{
						if (child.getNodeType()!=Element.ELEMENT_NODE){
							child=child.getNextSibling();
							continue;
						}
						String childNodeName=child.getNodeName();
						count=1;
						if (nodeCount.containsKey(childNodeName)){
							count=Integer.parseInt((String)nodeCount.get(childNodeName));
							count++;
							
						}
						nodeCount.put(childNodeName,String.valueOf(count));

						Node retNode=findsingleNode(child,xp,count);
						
						if (retNode!=null)	return retNode;
						
						child=child.getNextSibling();
					}
					nodeCount=null;
			}else
			{
				return node;
			}
			
		}
		return null;
	}
	
	private boolean isLastNode(Node node){
		String nodeName=node.getNodeName();
		Node brother=node.getNextSibling();
		while(brother!=null){
			if (nodeName.equals(brother.getNodeName())){
				return false;
			}
			brother=brother.getNextSibling();
		}
		return true;
	}
	
	private  void findNodeList(Node node,XpathParser xp,DomNodeList retNodeList,int pos) {
		
		if (node==null) return;

		XpathParser _xp=(XpathParser)xp.clone();
		
		Map attmap=_xp.currParAttributeList;
		Map valuemap=_xp.currParValueList;
		String curNodeName=_xp.currentParameterValue;
	
		if (node.getNodeName().equals(curNodeName))
		{
			int count=0;
			String curSeqValue=xp.curSequenceValue;
			int seqNum=-2;/*find validated first node*/
			if ("last()".equalsIgnoreCase(curSeqValue)){
				if (!isLastNode(node)){
					return ;
				}
			}else if (curSeqValue!=null){
				seqNum=Integer.parseInt(curSeqValue); /*the appointed node*/
				if (seqNum!=pos){
					return ;
				}
			}
			
			/**parse attribute and node value, if one of then is wrong ,will break parse.**/
			if ((!checkAttribute(node,attmap))||((!checkNodeValue(node,valuemap))))	return ;

			/** if parse is not finish ,contiue parse.**/
			if (_xp.nextParameter()!=null)
			{
					HashMap nodeCount=new HashMap();
					Node child=node.getFirstChild();
					while(child!=null)
					{
						if (child.getNodeType()!=Element.ELEMENT_NODE ){
							child=child.getNextSibling();
							continue;
						}
						String childNodeName=child.getNodeName();
						count=1;
						if (nodeCount.containsKey(childNodeName)){
							count=Integer.parseInt((String)nodeCount.get(childNodeName));
							count++;
							
						}
						nodeCount.put(childNodeName,String.valueOf(count));
						findNodeList(child,_xp,retNodeList,count);
						child=child.getNextSibling();
				}
					nodeCount=null;
			
			}else
			{
				retNodeList.addNode(node);
			}
			
		}
		
	}
	

	/**
	 * find node from node parameter,and put it into DomNodeList.
	 * @param node
	 * @param attmap
	 * @param valuemap
	 * @param currentNodeName
	 * @param retNodeList
	 */
	private void findNodeListFromGlobal(Node node,Map attmap,Map valuemap,String currentNodeName,DomNodeList retNodeList)
	{
		if (node==null) return;
		
		if (node.getNodeName().equals(currentNodeName))
		{
			if ((!checkAttribute(node,attmap))||((!checkNodeValue(node,valuemap))))	return ;
			
			retNodeList.addNode(node);
		
		}
		HashMap nodeCount=new HashMap();
		Node child=node.getFirstChild();
		int count=0;
		while (child!=null)
		{
			if (child.getNodeType()!=Element.ELEMENT_NODE){
				child=child.getNextSibling();
				continue;
			}
			String childNodeName=child.getNodeName();
			count=1;
			if (nodeCount.containsKey(childNodeName)){
				count=Integer.parseInt((String)nodeCount.get(childNodeName));
				count++;
				
			}
			nodeCount.put(childNodeName,String.valueOf(count));

			((Element)child).setAttribute("pos", String.valueOf(count));
			
			findNodeListFromGlobal(child,attmap,valuemap,currentNodeName,retNodeList);
			child=child.getNextSibling();
		}
		nodeCount=null;
		                                                                           
	}

	
	/**
	 * Get a Node from Global of node.
	 * @param node
	 * @param xp
	 * @return node
	 */
	
	private Node selectNodeFromGrobal(Node node,XpathParser xp)
	{
		
		if (node==null) return null;
		
		/** first ,get attributes and node value **/
		Map attmap=xp.currParAttributeList;
		Map valuemap=xp.currParValueList;
		String curNodeName=xp.currentParameterValue;
		
		DomNodeList globalNodelist=new DomNodeList();
		
		findNodeListFromGlobal(node,attmap,valuemap,curNodeName,globalNodelist);
		
		Node retNode=null;
		
		int count=globalNodelist.getLength();
		Node item;
		int pos=1;
		String _pos;
		for (int i=0;i<count;i++)
		{	
			item=globalNodelist.item(i);
			_pos=((Element)item).getAttribute("pos");
			if (_pos!=null && _pos.length()>0){
				pos=Integer.parseInt(_pos);
			}
			
			retNode=findsingleNode(globalNodelist.item(i),xp,pos);

			if (retNode!=null) return retNode;
		}
		return null;
	
	}
	
	/**
	 * get nodelist from grobal element of the node.
	 * @param node
	 * @param xp
	 * @param nodelist
	 */
	private void selectNodesFromGrobal(Node node,XpathParser xp,DomNodeList nodelist)
	{
		if (node==null) return ;
		
		Map attmap=xp.currParAttributeList;
		Map valuemap=xp.currParValueList;
		String curNodeName=xp.currentParameterValue;
		
		DomNodeList globalNodelist=new DomNodeList();
		
		findNodeListFromGlobal(node,attmap,valuemap,curNodeName,globalNodelist);
		
		int count=globalNodelist.getLength();
		Node item;
		int pos=1;
		String _pos;
		for (int i=0;i<count;i++){
			item=globalNodelist.item(i);
			_pos=((Element)item).getAttribute("pos");
			if (_pos!=null && _pos.length()>0){
				pos=Integer.parseInt(_pos);
			}
			findNodeList(item,xp,nodelist,pos);
		}
	}
	
	/**
	 *judge if attribute is exist or not.
	 * @param node
	 * @param attmap
	 * @return boolean
	 */
	private boolean checkAttribute(Node node,Map attmap)
	{
		if (attmap==null) return true;
		
		if (node.getNodeType()!=Element.ELEMENT_NODE) return false;
		
		Element element=(Element)node;
		
		for (Iterator iter = attmap.entrySet().iterator(); iter.hasNext();) {
		    Map.Entry entry = (Map.Entry) iter.next();
		   
		    String key = (String)entry.getKey();
		    String attributeValue=element.getAttribute( key);
		    
		    if (attributeValue==null) return false;
		    
		    String val = (String)entry.getValue();
		    
		    if (!attributeValue.equals(val)) return false;
		}
		
		return true;
	}

	/**
	 * 
	 * check node value
	 * @param node
	 * @param valuemap
	 * @return boolean
	 */
	private boolean checkNodeValue(Node node ,Map valuemap)
	{
		if (valuemap==null)return true;
		
		for (Iterator iter = valuemap.entrySet().iterator(); iter.hasNext();) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    
		    String key = (String)entry.getKey();
		    String val = (String)entry.getValue();
		    
		    if (!checkChild(node,key,val)) return false;
		}
		return true;
	}
	
	/**
	 *  judge if the node is exist or not.
	 * @param cNode
	 * @param nodeName
	 * @param nodeValue
	 * @return boolean
	 */
	private boolean checkChild(Node cNode ,String nodeName,String nodeValue)
	{
		
		if (cNode==null) return false;
		
		Node child=cNode.getFirstChild();
		
		while (child!=null)
		{
			if (child.getNodeName().equals(nodeName))
				if (checkChildNodeValue(child,nodeValue))
					return true;
			child=child.getNextSibling();
		}
		
		return false;
	}
	
	/**
	 * check node value
	 * @param childNode
	 * @param nodeValue
	 * @return boolean
	 */
	private boolean checkChildNodeValue(Node childNode,String nodeValue)
	{
		Node child=childNode.getFirstChild();
		
		while(child!=null)
		{
			if (child.getNodeType()==Element.TEXT_NODE)
				if (child.getNodeValue().equals(nodeValue))
					return true;
			
			child=child.getNextSibling();
		}
		
		return false;
	}
	
	/**private**/
	
	/**public**/

	/**
	 * Get node from document through xpath.
	 * @param xpath
	 * @return node
	 */
	public Node selectSingleNode(String xpath)
	{
		if (document==null){
			return null;
		}

		XpathParser xp=new XpathParser(xpath);

		if (xp.nextParameter()!=null)
	    {
		    Node rootNode=document.getDocumentElement();
		    Node retNode=null;
			if (xp.globalSelect)
				retNode=selectNodeFromGrobal(rootNode,xp);
			else
				retNode= findsingleNode(rootNode,xp,1);
			return retNode;
	    }
	    return null;
	  
	    
	}
	
	
	/**
	 * Get Node from node parameter through xpath.
	 * @param xpath
	 * @param innode
	 * @return node
	 */
	public Node selectSingleNode(String xpath,Node innode)
	{
		if (innode==null){
			return null;
		}
		XpathParser xp=new XpathParser(xpath);

		if (xp.nextParameter()!=null)
	    {
		    Node retNode=null;
		    
			if (xp.globalSelect)
				retNode=selectNodeFromGrobal(innode,xp);
			else
				retNode= findsingleNode(innode,xp,1);
		    
			return retNode;
	    }
	    return null;
	}
	
	/**
	 * Get nodelist from document through xpath.
	 * @param xpath
	 * @return nodelist
	 */
	public NodeList selectNodes(String xpath)
	{
		if (document==null){
			return null;
		}
		
		XpathParser xp=new XpathParser(xpath);

		if (xp.nextParameter()!=null)
	    {
			DomNodeList nodelist=new DomNodeList();
			
		    Node rootNode=document.getDocumentElement();
			if (xp.globalSelect)
				selectNodesFromGrobal(rootNode,xp,nodelist);
			else
				findNodeList(rootNode,xp,nodelist,1);

			return nodelist;
	    }
	    return null;
	}
	
	/**
	 * Get nodelist from node parameter through xpath.
	 * @param xpath
	 * @param innode
	 * @return nodelist
	 */
	public NodeList selectNodes(String xpath ,Node innode)
	{
		if (innode==null){
			return null;
		}
		XpathParser xp=new XpathParser(xpath);

		if (xp.nextParameter()!=null)
	    {
			DomNodeList nodelist=new DomNodeList();
		    
			if (xp.globalSelect)
				selectNodesFromGrobal(innode,xp,nodelist);
			else
				findNodeList(innode,xp,nodelist,1);

			return nodelist;
	    }
	    return null;
	}
	
	
	
	/**
	 */
	public Document getDocument() {
		return this.document;
	}
	/**public**/
}

/***************************************inner class for parse xpath*************************************************
 * inner class for xpath parse.
 * @author Administrator
 */
class XpathParser implements Cloneable
{
	protected String xpath=null;
	protected String currentParameter=null;
	protected String currentParameterValue=null;
	protected Map currParAttributeList=null;
	protected Map currParValueList=null;
	protected String curSequenceValue=null;
	protected boolean globalSelect=false;
	

	public XpathParser(String xpath)
	{
		currParAttributeList = new HashMap();
		currParValueList=new HashMap();
		
		this.xpath=xpath;
		
		if (this.xpath.substring(0,2).equals("//"))
		{
			this.globalSelect=true;
			this.xpath=this.xpath.substring(2);
		}else if  (this.xpath.substring(0,1).equals("/"))
			this.xpath=this.xpath.substring(1);
		
	}
	
	/**
	 * get current parameter
	 * if there is  no character to parase,then return null.
	 * @return current parameter value
	 */
	public String nextParameter()
	{
		
		if (xpath==null)
			return null;

		//if (currentParameter!=null) globalSelect=false;


		/**init parameter**/
		currParAttributeList.clear();
		currParValueList.clear();
		curSequenceValue=null;
		String currentParameter=null;
		if (xpath.indexOf('/')!=-1)
		{
			currentParameter=xpath.substring(0,xpath.indexOf('/'));
			xpath=xpath.substring(xpath.indexOf('/')+1);
		}else
		{
			currentParameter=xpath;
			xpath=null;
		}

		if (currentParameter.indexOf('[')!=-1)
		{
			currentParameterValue=currentParameter.substring(0,currentParameter.indexOf('['));
			parseCurrParAttributeList(currentParameter);
		}else

			currentParameterValue=currentParameter;

		return currentParameterValue;

	}
	
	/**
	 * parse current parameter for attribute.
	 */
	 private void parseCurrParAttributeList(String aParasevalue)
	{
		/**
		 * first clear currParAttributeList
		 */
		 int idx = aParasevalue.indexOf('[');
		if (idx!=-1)
		{
			String tempvalue= aParasevalue.substring(idx,aParasevalue.indexOf(']')+1);
			aParasevalue= aParasevalue.substring(aParasevalue.indexOf(']')+1);
			if (tempvalue.indexOf('@')!=-1)
			{
				/**
				 * attribute parameter
				 */
				String attributename=tempvalue.substring(2,tempvalue.indexOf('='));
				String attributevalue=tempvalue.substring(tempvalue.indexOf('=')+1,tempvalue.indexOf(']'));
				attributevalue=attributevalue.replaceAll("'","");

				currParAttributeList.put(attributename,attributevalue);
			}else
			{
				/**
				 * value parameter
				 */
				if (tempvalue.indexOf('=')==-1){
					curSequenceValue=tempvalue.substring(tempvalue.indexOf('[')+1,tempvalue.indexOf(']'));
					
				}else{
					String nodename=tempvalue.substring(1,tempvalue.indexOf('='));
					String nodevalue=tempvalue.substring(tempvalue.indexOf('=')+1,tempvalue.indexOf(']'));
					nodevalue=nodevalue.replaceAll("'","");
					currParValueList.put(nodename,nodevalue);
				}
			}
			parseCurrParAttributeList(aParasevalue);
		}
	}
	
	 
	/**
	 */
	public String getXpath() {
		return this.xpath;
	}
	/**
	 */
	public String getCurrentParameterValue() {
		return currentParameterValue;
	}
	/**
	 */
	public Map getCurrParAttributeList() {
		return currParAttributeList;
	}
	/**
	 */
	public Map getCurrParValueList() {
		return currParValueList;
	}
	/**
	 */
	public boolean isGlobalSelect() {
		return globalSelect;
	}
	
	public Object clone(){
		Object obj=null;
		try {
			obj=(Object)super.clone();
		} catch (CloneNotSupportedException e) {
		}
		return obj;
	}
}



