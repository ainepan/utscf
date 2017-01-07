package com.ut.comm.tool.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.ut.comm.tool.consts.ASSignConst;
import com.ut.comm.tool.string.StringUtil;

public class XMLManager {

	/**
	 * Return the node of the first occurrence of the child nodes with a given tag name and is contained in the child nodes.
	 * if no such element exist ,this returns null. 
	 * @param strChildNodeName	The name of the tag to match on.
	 * @param nParent	The node to search.
	 * @return	 The matching element or null if there is none.
	 */
	public static Node findChildNode(String strChildNodeName, Node nParent) {
		    
		if (nParent==null) return null;
		    
		Node child = nParent.getFirstChild();
		while (child != null)
		{
				if (child.getNodeName().equals(strChildNodeName)) return child;
					
					child = child.getNextSibling();
		}
			
		return null;

	}
	
	public static Node findChildNode(String strChildNodeName, Node nParent, int matchIndex) {
		if (nParent == null)
			return null;
		
		if (nParent.getNodeName().equals(strChildNodeName))
			return nParent;
		
		int currentMatch = 0;
		Node child = nParent.getFirstChild();
		while (child != null)
		{
			if (child.getNodeName().equals(strChildNodeName)) {
				currentMatch++;
				if (matchIndex == currentMatch)
					return child;
			}
			child = child.getNextSibling();
		}
		return null;
	}
	
	public static Node findChildNodeIgnoreSelf(String strChildNodeName, Node nParent, int matchIndex) {
		if (nParent == null)
			return null;
		
		int currentMatch = 0;
		Node child = nParent.getFirstChild();
		while (child != null)
		{
			if (child.getNodeName().equals(strChildNodeName)) {
				currentMatch++;
				if (matchIndex == currentMatch)
					return child;
			}
			child = child.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Return the node of the frist occurrence of the node list with give a tag name.
	 * if no such element exist,this returns null.
	 * @param strChildNodeName	The name of the tag to match on.
	 * @param nodelist to search.
	 * @return	 The matched element or null if there is none.
	 */
	public static Node findChildNode(String strChildNodeName, NodeList nodelist) {
	    
	if (nodelist==null) return null;
	    
	int count=nodelist.getLength();
	
	for (int i=0;i<count;i++)
	{
		Node nNode=nodelist.item(i);
		
		if (nNode.getNodeName().equals(strChildNodeName)) return nNode;
		
	}
	
	return null;

}
	
	/**
	 * Return the node of the first occurrence of the document with a given tag name and is contained in the document.
	 * @param nFromNode The node to search.
	 * @param strNodeName The name of the tag to match on.
	 * @return The matching element or null if there is none.
	 */	
	public static Node findNode(Node nFromNode,String strNodeName)
	{
		if (nFromNode == null)	return null;
		
		if(StringUtil.isTrimEmpty(strNodeName)) return nFromNode;
		
		if (nFromNode.getNodeName().equals(strNodeName)) return nFromNode;
		if (strNodeName != null && strNodeName.indexOf("/") > 0) {
			Node selectNode = selectNodes(nFromNode, strNodeName).item(0);
			return selectNode;
        }
		Node child=nFromNode.getFirstChild();
		
		while (child!=null)
		{
			Node retChild=findNode(child,strNodeName);
			
			if (retChild!=null) return retChild;
			
			child=child.getNextSibling();
			
		}
		return null;

	}
	
	public static Node findNode(Node nFromNode, String strNodeName, int matchIndex) {
		if (nFromNode == null)
			return null;
		
		if(StringUtil.isTrimEmpty(strNodeName))
			return nFromNode;

		if (nFromNode.getNodeName().equals(strNodeName))
			return nFromNode;
		if (strNodeName != null && strNodeName.indexOf("/") > 0) {
			Node selectNode = selectNodes(nFromNode, strNodeName).item(0);
			return selectNode;
		}
		Node child = nFromNode.getFirstChild();
		int currentMatch = 0;

		while (child != null)
		{
			
			Node retChild = findNode(child, strNodeName);

			if (retChild != null){
				currentMatch++;
				if(matchIndex==currentMatch)
					return retChild;
			}
				
		
			child = child.getNextSibling();

		}
//		Assert.isTrue(currentMatch>=matchIndex, "Match index is bigger than total match times.");
		return null;
	}
	
	/**
	 * Return the element that has an attribute with a give name and a give value.
	 * if no such element exists,this returns null.if more then one element has an attribute with that name and that value.
	 * what is returned is undefined.
	 * @param nFromNode to search.
	 * @param strNodeAtrrName of the attribute to match on.
	 * @param strNodeAtrrValue of the attribute to match on.
	 * @return The matching element or null if there is none.
	 */
	public static Node findChildNodeByAttribute(
			Node nFromNode,
			String strNodeAtrrName,
			String strNodeAtrrValue)
	{
		
		if (nFromNode==null)return null;
	
		Node child=nFromNode.getFirstChild();
			
		while (child!=null)
			{
				if (child.getNodeType()==Element.ELEMENT_NODE)
				{
					Element element=(Element)child;
					String retValue=element.getAttribute(strNodeAtrrName);
					if ((retValue!=null)&&(retValue.equals(strNodeAtrrValue))) return child;
				}
				child=child.getNextSibling();
		}
			
		return null;
	}
	
	/**
	 * Return a nodelist  of all the elements in node with a given attribute name and a given attribute value.
	 * @param nFromNode to search.
	 * @param strNodeAtrrName The name of the attribute to match on.
	 * @param strNodeAtrrValue The value of the attribute to match on.
	 * @return A new nodelist containing all the matched elements.
	 */
	public static NodeList findNodesByAttribute(
			Node nFromNode,
			String strNodeAtrrName,
			String strNodeAtrrValue)
	{
		if (nFromNode==null) return null;
		
			DomNodeList retNodeList= new DomNodeList();
			
			Node child=nFromNode.getFirstChild();
			
			while (child!=null)
			{
				if (child.getNodeType()==Element.ELEMENT_NODE)
				{
					Element element=(Element)child;
					String retValue=element.getAttribute(strNodeAtrrName);
					if ((retValue!=null)&&(retValue.equals(strNodeAtrrValue)))	retNodeList.addNode(child);
				}
				child=child.getNextSibling();
			}
			return retNodeList;

	}
	
	/**
	 * Return the first child element of parentNode with the given name and which has an attribute with a give name and a give value.
	 * if no such element exists,this returns null.if more then one element has an attribute with that name and that value.
	 * what is returned is undefined.
	 * @param parentNode to search the children.
	 * @param childNodeName that is used filter the children whose node name must be same as
	 * @param strNodeAtrrName of the attribute to match on.
	 * @param strNodeAtrrValue of the attribute to match on.
	 * @return The matching element or null if there is none.
	 */
	public static Node findChildNodeByAttribute(
			Node parentNode,
			String childNodeName,
			String strNodeAtrrName,
			String strNodeAtrrValue)
	{
		
		if (parentNode==null)return null;
	
		Node child=parentNode.getFirstChild();
			
		while (child!=null)
		{
			if(child.getNodeType()==Node.ELEMENT_NODE)
			{
				Element element=(Element)child;
				//String eleName = element.getNodeName();
				if(element.getNodeName().equals(childNodeName))
				{
					String retValue=element.getAttribute(strNodeAtrrName);
					if ((retValue!=null)&&(retValue.equals(strNodeAtrrValue))) 
						return child;
				}
			}
			child=child.getNextSibling();
		}
		return null;
	}
	
	public static ArrayList findChildNodesWithAttribute(
			Node parentNode,
			String childNodeName,
			String strNodeAtrrName,
			String strNodeAtrrValue)
	{
		if (parentNode==null) 
			return new ArrayList();;
		ArrayList retList=new ArrayList();
		Node child=parentNode.getFirstChild();
		while (child!=null)
		{
			if(child.getNodeType()==Node.ELEMENT_NODE)
			{
				Element element=(Element)child;
				if(element.getNodeName().equals(childNodeName))
				{
					String retValue=element.getAttribute(strNodeAtrrName);
					if ((retValue!=null)&&(retValue.equals(strNodeAtrrValue)))
						retList.add(child);
				}
			}
			child=child.getNextSibling();
		}
		return retList;
	}
	
	/**
	 * @param parentNode
	 * @param childNodeName
	 * @param strNodeAtrrName
	 * @param strNodeAtrrValue
	 * @return
	 */
	public static ArrayList findChildNodesWithAttribute(
			Node parentNode,
			String[] childNodeNames,
			String strNodeAtrrName,
			String strNodeAtrrValue)
	{
		if (parentNode==null || childNodeNames == null || childNodeNames.length < 1) 
			return new ArrayList();;
		Arrays.sort(childNodeNames);
		ArrayList retList=new ArrayList();
		Node child=parentNode.getFirstChild();
		while (child!=null)
		{
			if(child.getNodeType()==Node.ELEMENT_NODE)
			{
				Element element=(Element)child;
				String nodeName = element.getNodeName();
				int index = Arrays.binarySearch(childNodeNames, nodeName);
				if(index > -1)
				{
					String retValue=element.getAttribute(strNodeAtrrName);
					if ((retValue!=null)&&(retValue.equals(strNodeAtrrValue)))
						retList.add(child);
				}
			}
			child=child.getNextSibling();
		}
		return retList;
	}
	
	public static HashMap convertChildElementsToMap(HashMap initMap, Node parentEle)
	{
		if(initMap == null)
			initMap = new HashMap();
		if(parentEle == null || parentEle.getNodeType() != Node.ELEMENT_NODE)
			return initMap;
		
		Node child = parentEle.getFirstChild();
		while(child != null)
		{
			if(child.getNodeType() == Node.ELEMENT_NODE)
			{
				String name = child.getNodeName();
				String value = getNodeValue(child, false);
				initMap.put(name, value);
			}
			child = child.getNextSibling();
		}
		return initMap;
	}
	
	
	public static void renameElementName(Document doc, Node objectEle, String newName)
	{
		((DocumentImpl)doc).renameNode(objectEle, null, newName);
	}
	
	/**
	 * @param parent
	 * @param tagName
	 * @return
	 */
	public static Element[] getElementsByTagNameWithAttribute(Element parent, String tagName, String attribute, String attrValue)
	{
		Element[] e = new Element[0];
		if (parent == null)
			return e;
		if (tagName == null || tagName.length() <= 0)
			return e;
		ArrayList al = new ArrayList();
		
		Element ele = null;
		NodeList ndList = parent.getElementsByTagName(tagName);
		for(int i = 0, length = ndList.getLength(); i <length; i++)
		{
			ele = (Element)ndList.item(i);
			if(ele.getAttribute(attribute).equals(attrValue))
			{
				al.add(ele);
			}
		}
		ndList = null;
		int len = al.size();
		e = new Element[len];
		return (Element[])al.toArray(e);
	}
	/**
	 * Return a arraylist of all the elements in node with a given node name.
	 * @param nParent The node to search.
	 * @param strNodeName The name of the tag to match on.
	 * @return a new arrylist containing all the matched elements.
	 */
	public static ArrayList findChildNodes(Node nParent,String strNodeName)
	{
		if (nParent==null) return null;
		
			ArrayList retList=new ArrayList();
			
			Node child = nParent.getFirstChild();
			while (child != null)
				{
					if(child.getNodeType() == Node.ELEMENT_NODE)
					{
						if (child.getNodeName().equals(strNodeName)) 
							retList.add(child);
					}
					child = child.getNextSibling();
				}
			
			return retList;
	}
	
	/**
	 * Return the value of the element in the node with a given tag name. 
	 * if more than one element has an tag name with that value ,what is returned is undefined.
	 * @param nParent The node to search .
	 * @param strChildNodeName he name of the tag to match on.
	 * @param isTrim If leading and trailing whitespace omitted.
	 * @return if isTrim is true, then return the value of the element with a given tag name white space removed. otherwise ,return  the value
	 * of the element with a given tag name white space don't be removed. 
	 */
	public static String getChildNodeValue(
			Node nParent,
			String strChildNodeName,
			boolean isTrim)
	{
		if (nParent==null) return null;
		
		if (strChildNodeName != null && strChildNodeName.indexOf(".") > 0) {
			try {
				strChildNodeName = replaceAll(strChildNodeName, ".", ASSignConst.CHR_LEFT_SLASH);
			} catch (Exception e) {
			}
			strChildNodeName = new StringBuffer("domData").append(ASSignConst.CHR_LEFT_SLASH).append(strChildNodeName).toString();
			Node selectNode = selectNodes(nParent, strChildNodeName).item(0);
			return getNodeValue(selectNode,true);
        }
		Node aNode= findChildNode(strChildNodeName,nParent);
			
		if (aNode==null) return null;
			
		return getNodeValue(aNode,isTrim);

	
	}
	
	/**
	 * Return the attribute value of the element that has an attribute with a given attribute name.
	 * if no such attribute exists ,this returns  null.
	 * @param nNode The node to search.
	 * @param strNodeAttributeName The name of the attribute to match on.
	 * @return The matching attribute or null if there is none.
	 */
	public static String getNodeAttribute(Node nNode,String strNodeAttributeName)
	{
		if (nNode==null) return null;

			if (nNode.getNodeType()==Element.ELEMENT_NODE)
			{
				Element element=(Element)nNode;
				return element.getAttribute(strNodeAttributeName);
			}
			return null;

	}
	
	/**
	 * Return the value of the node with leading and trailing space removed when isTrim is true,or return the 
	 * value of the node with leading and trailing space don't be removed when isTrim is false.
	 * @param sNode The node to search .
	 * @param isTrim if True,Remove the space of the leading and trailing.
	 * @return the value of this node.
	 */
	public static String getNodeValue(Node sNode,boolean isTrim)
	{
		if (sNode==null) {
			return null;
		}
		
		try {
			String sv = null;
			Node nd = sNode.getFirstChild();
			if(nd != null && (nd.getNodeType() == Node.TEXT_NODE || nd.getNodeType() == Node.CDATA_SECTION_NODE))
			{
				sv = nd.getNodeValue();
				if(sv != null && isTrim)
					sv = sv.trim();
			}
			return sv;
		} catch (Exception e) {
			return null;
		}

	}
	
	/**
	 * Get the node from the Document with a given node name ,and return the value of that node.
	 * if no such node exists ,this returns null,if more than one node has tag name is an given name,
	 * what is returned is undefined.
	 * @param dmDom The Document to search.
	 * @param strNodeName The name of the tag to match on.
	 * @param isTrim if isTrim is true then leading and trailing of the value removed,otherwise ,don't be removed.
	 * @return the value of the that node.
	 */
	public static String getNodeValue(
			Document dmDom,
			String strNodeName,
			boolean isTrim)
	{
		if (dmDom==null) return null;
		
		Node node=findNode(dmDom.getDocumentElement(),strNodeName);
		
		if (node==null) return null;
		
		return getNodeValue(node,isTrim);

		
	}
	
	public static String getNodeValue(Node _dmDom, String strNodeName, boolean isTrim) {
		if (_dmDom==null) return null;

		Node toFind = _dmDom;
		if (_dmDom instanceof Document )toFind=((Document)_dmDom).getDocumentElement();
 		Node node=findNode(toFind,strNodeName);
		if (node==null) return null;
		return getNodeValue(node,isTrim);
	}

	/**
	 * get node immediately following node,if this node name is the given value then return the value of it ,otherwise,
	 * get next node immediately fllowing this node,and do that while found the node.if there no such node ,this returns null.
	 * @param nNode The node to search.
	 * @param strSilbingNodeName The name of the tag to match on.
	 * @param isTrim if true,Remove the leading and trailing white space.
	 * @return Value of the node or null if there no none.
	 */
	public static String getNodeSiblingVlaue(
			Node nNode,
			String strSilbingNodeName,
			boolean isTrim)
	{
		if (nNode==null) return null;
		
		Node aNode=nNode.getNextSibling();
		while(aNode!=null)
		{
			if (aNode.getNodeName().equals(strSilbingNodeName))
				return getNodeValue(aNode,isTrim);

			aNode=aNode.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Return the combination of the all elements in node,there is not any comma in this value.
	 * if node is null,then return null.
	 * @param nParent The node to parsed.
	 * @return combination of the all elements in node or null if node is null.
	 */
	public static String getMacValues(Node nParent)
	{
		if (nParent == null)return null;
		
		if (nParent.getNodeType() != Node.ELEMENT_NODE) 	return null;	
		
		StringBuffer strMac = new StringBuffer();
			
		String strNV = getNodeValue(nParent, true);
			
		if (strNV != null)strMac.append(strNV);
			
			
		Node child=nParent.getFirstChild();
			
		while (child!=null)
			{
				String strV=getMacValues(child);
				
				if (strV!=null) strMac.append(strV);
				
				child=child.getNextSibling();
			}

		return strMac.toString();
	}
	
	/**
	 * Load and return document from appointed file path ,if filepath is null or filepath isn't exist then return null. 
	 * @param strXmlFilePath The appointed file path.
	 * @return Document
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws Exception
	 */
	public static Document xmlFileToDom(String strXmlFilePath) throws SAXException, IOException
	{
		File file = new File(strXmlFilePath);
		if (!file.exists()) {
			throw new IOException(String.format("Can not load specify file:%s", strXmlFilePath));
		}
		DOMParser parser = new DOMParser();
		parser.parse(strXmlFilePath);
		Document document = parser.getDocument();
		return document;
	}
	
	/**
	 * Return a document through parsed strings.if strings is null or found some error when parsed it then return null.
	 * @param strXmlContent The strings to parse.
	 * @return the document or null if there parsed error.
	 * @throws Exception
	 */
	public static Document xmlStrToDom(String strXmlContent) throws Exception
	{
		try {
			String s=strXmlContent;
			s=s.trim();
			if (s.length()>0){
				if (s.charAt(0) != '<')
					return null;
			}else{
				return null;
			}
			
			StringReader sr = new StringReader(strXmlContent);
			InputSource is = new InputSource(sr);
			DOMParser parser = new DOMParser();
			parser.parse(is);
			sr.close();
			return parser.getDocument();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Adds the node newchild to the end of the list of the nAppendTo node.
	 * if the newchild is already in the tree,it is first removed,
	 * @param nAppendTo the parent node to append.
	 * @param nAppendNode the newchild to be appended.
	 */
	public static void appendUnderElement(Node nAppendTo,Node nAppendNode)
	{
		
		nAppendTo.appendChild(nAppendNode);
	}
	
	/**
	 * Adds the node newchild to the end of the list of the root element of the document.
	 * if the newchild is already in the tree,It is first removed.
	 * @param dmDom The document to add newchild under root element.
	 * @param nAppendNode The newchild to be appended.
	 */
	public static void appendUnderRoot(Document dmDom,Node nAppendNode)
	{
		dmDom.getDocumentElement().appendChild(nAppendNode);
	}
	
	/**
	 * Get all nodes of the document,create a new hashmap and covert these into it that used node name as the key of the map .
	 * The nodes of the same name will covert the last node to mapping of the hashmap .  
	 * @param dmDom The document to covert.
	 * @return the new hashmap cotain mapping of the node.
	 */
	public static HashMap convertToHashMap(Document dmDom)
	{
		HashMap hashMap = new HashMap();
		convertToHashMap(hashMap, dmDom);
		return hashMap;
	}
	
	
	/**
	 * Get all childnodes of the node , covert these into a hashmap that used node name as the key of the map.
	 * the  childnode of the same name will covert the last node to mapping of the hashmap. 
	 * @param hashMap The node to covert.
	 * @param nNode the new hashmap cotain mapping of the node.
	 */
	public static void convertToHashMap(HashMap hashMap ,Node nNode)
	{
		if (nNode == null) return ;
		
		switch (nNode.getNodeType()) {
			case Node.DOCUMENT_NODE :
				{
					convertToHashMap(hashMap,((Document) nNode).getDocumentElement());
					break;
				}
			case Node.ELEMENT_NODE :
				{
					Node child=nNode.getFirstChild();
					String value = null;
					while (child!=null)
					{
						if (child.getNodeType() == Node.ELEMENT_NODE)
							convertToHashMap(hashMap, child);
						else
							if (value == null)
								value = child.getNodeValue();
							
						child=child.getNextSibling();
					}
					
					
					if (value != null) {
						if (value.equals("null"))
							hashMap.put(nNode.getNodeName(), "");
						else
							hashMap.put(nNode.getNodeName(), value.trim());
						
					} else
						hashMap.put(nNode.getNodeName(), "");
					
					NamedNodeMap attrs = nNode.getAttributes();
					Attr attr;
					for (int i = 0; i < attrs.getLength(); i++) {
						attr = (Attr) attrs.item(i);
						if (attr.getValue() != null) {
							if (attr.getValue().equals("null"))
								hashMap.put(attr.getName(), "");
							else
								hashMap.put(attr.getName(), attr.getValue().trim());
						} else
							hashMap.put(attr.getName(), "");
					}
					break;
				}
		}
	}
	
	
	/**
	 * Get all childnodes of the node ,Create a hashmap and  covert these into it that used node name as the key of the map.
	 * the  childnode of the same name will covert the last node to mapping of the hashmap. 
	 * @param nNode The node to covert.
	 * @return the new hashmap cotain mapping of the node.
	 */
	public static HashMap convertToHashMap(Node nNode)
	{
		HashMap  hashMap=new HashMap();
		convertToHashMap(hashMap,nNode);
		return hashMap;
	}
	
	/**
	 * Covert the tree of the document to a string,and return it.
	 * @param dmDom The document to covert.
	 * @return the strings contain all elements of the document.
	 */
	public static String convertToString(Document dmDom)
	{
		try {

			StringWriter out = new StringWriter();
			OutputFormat outf = new OutputFormat();
			outf.setPreserveSpace(true);
			XMLSerializer xmls = new XMLSerializer(out, outf);
			xmls.serialize(dmDom);
			out.close();
			return out.toString();

		} catch (Exception e) {
			return convertDomToString(dmDom);
		}
	}
	
	public static String convertToString(Document dmDom,String charset)
	{
		try {
			StringWriter out = new StringWriter();
			OutputFormat outf = new OutputFormat();
			outf.setEncoding(charset);
			outf.setPreserveSpace(true);
			XMLSerializer xmls = new XMLSerializer(out, outf);
			xmls.serialize(dmDom);
			out.close();
			return out.toString();

		} catch (Exception e) {
			return convertDomToString(dmDom);
		}
	}
	
	@Deprecated
	public static String convertDomToString(Document dmDom) 
	{
		try 
		{
			Source source = new DOMSource(dmDom);
			StringWriter sw = new StringWriter();
			Result result = new StreamResult(sw);

			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(source, result);
			return sw.getBuffer().toString();

		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * Covert the tree of the node to a string,and return it.
	 * 
	 * @param nNode The node to covert
	 * @return the strings contain all elements of the node.
	 */

	public static String convertToString(Node nNode)
	{
	
		return convertToString((Document) nNode);
  		
	}
 	
	public static DocumentImpl createDocumentImpl()
	{
		return (DocumentImpl)createDocument("root");
	}

	public static Document createDocument(String rootName){
		Document doc = new DocumentImpl();
		Element root = doc.createElement(rootName);
		doc.appendChild(root);
		return doc;
	}
	public static Document createDocument(){
		Document doc = new DocumentImpl();
		return doc;
	}
	
	/**
	 * return a new Document that the name of the root node is root and the childnodes of the root come form mappings of the hashmap.
	 * if hashmap is null then return null.
	 * @param hashmap convert the hashmap to xml 
	 * @return the document or null if the hashmap is null.
	 */
	
	public static Document createXMLDocument(HashMap hashmap)
	{
		Document returnDoc = new DocumentImpl();
		Element root = returnDoc.createElement("root");
		returnDoc.appendChild(root);
		String key, value;
		Element node;
		if (hashmap == null)
			return null;
		Iterator iter = hashmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			key = (String) entry.getKey();
			node = returnDoc.createElement(key);
			if ((value = (String) entry.getValue()) != null)
				node.appendChild(returnDoc.createTextNode(value));
			root.appendChild(node);
		}
		return returnDoc;
	}
	
	/**
	 * return a new document that the name of the root element is root and the childnodes of the root come form elements of the HttpServletRequest.
	 * if httpservletrequest is null, this returns null.
	 * @param req HttpServletRequest
	 * @return the document or null if the httpservletrequest is null.
	 */
	
	public static Document createXMLDocument(HttpServletRequest req)
	{
		Document rtnDOM = new DocumentImpl();
		Element root = rtnDOM.createElement("root");
		rtnDOM.appendChild(root);
		String key = null;
		String[] paraValue = null;
		Element node = null;
		if (req == null)
			return null;
		Enumeration enumt = req.getParameterNames();
		while (enumt.hasMoreElements()) {
			key = (String) enumt.nextElement();
			paraValue = req.getParameterValues(key);
			for (int i = 0; i < paraValue.length; i++) {
				node = rtnDOM.createElement(key);
				if (paraValue[i] != null) {
					paraValue[i] = paraValue[i].trim();
					if (paraValue[i].equals("null"))
						paraValue[i] = "";
					node.appendChild(rtnDOM.createTextNode(paraValue[i]));
				} else {
					node.appendChild(rtnDOM.createTextNode(""));
				}
				root.appendChild(node);
			}
		}
		return rtnDOM;
	}
	
	/**
	 * create new element by appointed name ,add the newchilds that coverted by mappings of the hashmap into the it.and return it.
	 * it the hashmap is null ,this returns null.
	 * @param dmDom the document to create elements.
	 * @param strElementName the name of the new node.
	 * @param hmChildNodes child nodes
	 * @return the document or null if the hashmap is null.
	 */
	public static Element createXMLElement(
			Document dmDom,
			String strElementName,
			HashMap hmChildNodes)
	{

		if (hmChildNodes == null) return null;
		
		String key, value;
		Element element, node;
		element = dmDom.createElement(strElementName);
		Iterator iter = hmChildNodes.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			key = (String) entry.getKey();
			node = dmDom.createElement(key);
			if ((value = (String) entry.getValue()) != null)
				node.appendChild(dmDom.createTextNode(value));
			element.appendChild(node);
		}
		return element;
	}
	
	/**
	 * return a new node with a given tag name and a given value.
	 * @param dmDom The document to create new node.
	 * @param strElementName the name of the tag.
	 * @param strElementValue the value of the node.
	 * @return the node or null if tagname is null.
	 */
	public static Element createXMLElement(
			Document dmDom,
			String strElementName,
			String strElementValue)
	{
		Element element = dmDom.createElement(strElementName);
		if (strElementValue != null)
			element.appendChild(dmDom.createTextNode(strElementValue));
		return element;
	}
	
	/**
	 * return a new Document that the name of the root node is root and the childnodes of the root come form mappings of the hashmap.
	 * if hashmap is null then return null.
	 * @param hasmap convert hashmap to document. 
	 * @return the document or null if the hashmap is null.
	 */
	public static Document hashMapToDom(HashMap hasmap)
	{
		if (hasmap == null) 	return null;
		
		Document rtnDom = new DocumentImpl();
		
		Element root = rtnDom.createElement("root");
		rtnDom.appendChild(root);

		String key, value;
		Element node;
		Iterator iter = hasmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			key = (String) entry.getKey();
			node = rtnDom.createElement(key);
			if ((value = (String) entry.getValue()) != null)
				node.appendChild(rtnDom.createTextNode(value));
			root.appendChild(node);
		}
		return rtnDom;
	}
	
	/**
	 * Create a new document with the rootnode name is root, Create new node and add it to the end of the list of the root node with a given node name,
	 * Get all mappings of the hashmap and covert these to newchilds , add its into the node.
	 * if the hashmap is null ,this returns null.
	 * @param hasmap
	 * @param strNodeName the name of the new node.
	 * @return The document or null if the hashmap is null.
	 */
	public static Document hashMapToDom(HashMap hasmap,String strNodeName)
	{
		if (hasmap == null) 	return null;
		
		Document rtnDom = new DocumentImpl();
		
		Element root = rtnDom.createElement("root");
		rtnDom.appendChild(root);
		Element strNameElement = rtnDom.createElement(strNodeName);
		root.appendChild(strNameElement);

		String key, value;
		Element node;
		Iterator iter = hasmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			key = (String) entry.getKey();
			node = rtnDom.createElement(key);
			if ((value = (String) entry.getValue()) != null)
				node.appendChild(rtnDom.createTextNode(value));
			strNameElement.appendChild(node);
		}
		return rtnDom;
	}
	
	/**
	 * return the node of the first occurrence of the childnodes of the fromnode with the given mapping of the attributes,
	 * if the fromnaode is null then return null.
	 * @param nFromNode the node to search.
	 * @param attributeMap the mapping of the attributes to match on.
	 * @return the node or null.if there is no exist or fromnode is null then return null.
	 */
	public static Node findNodeByAttribute(Node nFromNode,HashMap attributeMap)
	{
		if (nFromNode==null) return null;
		
		Node child=nFromNode.getFirstChild();
		
		while(child!=null)
		{
			if (matchAttribute(child,attributeMap)) return child;
			
			child=child.getNextSibling();
		}

		return null;
	}
	
	/**
	 * match node through attributes
	 * @param nNode
	 * @param attributeMap
	 * @return boolean
	 */
	private static boolean matchAttribute(Node nNode,HashMap attributeMap)
	{
		if (nNode==null) return false;
		
		if (attributeMap==null) return true;
		
		if (nNode.getNodeType()==Element.ELEMENT_NODE)
		{
			Element element=(Element)nNode;
			
		    Iterator iterator=attributeMap.entrySet().iterator();
		    
		    while(iterator.hasNext())
		    {
		    	Map.Entry entry = (Map.Entry)iterator.next();
		    	String attributeName=(String)entry.getKey();
		    	
		    	String attributeValue=element.getAttribute(attributeName);
		    	if (attributeValue==null) return false;
		    	
		    	String attributeValueFromMap=(String)entry.getValue();
		    	
		    	if (!attributeValue.equals(attributeValueFromMap)) return false;
		    	
		    }
		    return true;
			
		}
		return false;
	}
	
	
	/**
	 * return the node of the first occurrence of the childnodes of the fromnode with the given mapping of the attributes and a given node name,
	 * if the fromnaode is null then return null.
	 * @param nFromNode
	 * @param nodeName
	 * @param attributeMap
	 * @return the node or null.if there is no exist then return null.
	 */
	public static Node findNodeByAttribute(Node nFromNode,String nodeName,HashMap attributeMap)
	{
		
		if (nFromNode==null) return null;
				
		Node child=nFromNode.getFirstChild();
		while(child!=null)
		{
			if (child.getNodeName().equals(nodeName))
				if (matchAttribute(child,attributeMap))
					return child;
			
			Node node = findNodeByAttribute(child,nodeName,attributeMap);
			if (node!=null)return node;
			child=child.getNextSibling();
		}

		return null;

	}
	
	/**
	 * return the  node of the first occurrence of the nodelist with a given attributes map.  
	 * @param nodeList to seach.
	 * @param attributeMap the map of the attributes to match on.
	 * @return the node or null if nodelist is null.
	 */
	public static Node findNodeByAttribute(NodeList nodeList,HashMap attributeMap)
	{
		if ((nodeList==null)||(nodeList.getLength()==0)) return null;
		
		int count=nodeList.getLength();
		
		for (int i=0;i<count;i++)
		{
			Node nNode=nodeList.item(i);
			if (matchAttribute(nNode,attributeMap)) return nNode;
		}

		return null;
	}
	
	/**
	 * return the node of the first occurrence of the nodelist with the given mapping of the attributese,
	 * if the parent node is null then return null.
	 * @param nParent parent node.
	 * @param strChildNodeName the name of the node.
	 * @param attributeMap
	 * @return the node or null if parent node is null.
	 */
	public static NodeList findNodesByAttribute(Node nParent,String strChildNodeName,HashMap attributeMap)
	{
		if (nParent==null) return null;
		
		DomNodeList nodelist=new DomNodeList();
		
		Node child=nParent.getFirstChild();
		
		while(child!=null)
		{
			if (child.getNodeName().equals(strChildNodeName))
				if (matchAttribute(child,attributeMap))
					 nodelist.addNode(child);
				
			child=child.getNextSibling();
		}
		
		return nodelist;
	}
	
	/**
	 * Return a nodelist of all the elements in node with a given hashmap of  attributes and are contained in the node.
	 * @param nParent the node to search.
	 * @param attributeMap the attributes mapping of the hashmap to match on.
	 * @return a new nodelist object containing all the matched elements.
	 */
	public static NodeList findNodesByAttribute(Node nParent,HashMap attributeMap)
	{
		if (nParent==null) return null;
		
		Node child=nParent.getFirstChild();
		DomNodeList nodelist=new DomNodeList();
		
		while(child!=null)
		{
			if (matchAttribute(child,attributeMap)) nodelist.addNode(child);
				
			child=child.getNextSibling();
		}

		return nodelist;
	}
	
	/**
	 * Return a new nodelist of all elements in the source nodelist with a given map of the attributes and are contained in the nodelist.
	 * @param nodeList the nodelist to search.
	 * @param attributeMap the map of the attributes to match on.
	 * @return a new nodelist object containing all the matched elements.
	 */
	public static NodeList findNodesByAttribute(NodeList nodeList,HashMap attributeMap)
	{
		if ((nodeList==null)||(nodeList.getLength()==0)) return null;
		
		DomNodeList retnodelist=new DomNodeList();
		
		int count=nodeList.getLength();

		for (int i=0;i<count;i++)
		{
			Node nNode=nodeList.item(i);
			if (matchAttribute(nNode,attributeMap)) retnodelist.addNode(nNode);
		}

		
		
		
		return retnodelist;
	}
	
	/**
	 * Evaluates an xpath expression in document and returns the result  as a single node instance.
	 * @param document the document to evaluate the xpath expression.
	 * @param  xpath expression to be evaluated.
	 * @return  the node matching the xpath expression.
	 */
	public static Node selectSingleNode(Document document,String xpath)
	{
		try
		{
			XMLXpathParser domparse=new XMLXpathParser(document);
			
			return domparse.selectSingleNode(xpath);
		}catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Evaluates an xpath expression in node and returns the result as a single node instance.
	 * @param node the node to evaluate the xpath expression.
	 * @param xpath expression to be evaluted.
	 * @return the node matching the xpath expression.
	 */
	public static Node selectSingleNode(Node node,String xpath)
	{
		try
		{
			XMLXpathParser domparse= new XMLXpathParser();
			
			return domparse.selectSingleNode(xpath,node);
		}catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Evaluates an xpath expression in document and returns the result as a list of the node instances.
	 * @param document the document to evaluate the xpath expression.
	 * @param xpath expression to be evaluted.
	 * @return the list of the node instances depending on the xpath expression.
	 */
	public static NodeList selectNodes(Document document, String xpath)
	{
		try
		{
			XMLXpathParser domparse=new XMLXpathParser(document);
			
			return domparse.selectNodes(xpath);
		}catch(Exception e)
		{
			return null;
		}
	}
	
	
	/**
	 * Evaluates an xpath experession in node and returns the result as a list of the node instances.
	 * @param nNode the node to evaluate the xpath expression.
	 * @param xpath expression to be evaluated.
	 * @return the list of the node instances depending on the xpath expression.
	 */
	public static NodeList selectNodes(Node nNode,String xpath)
	{
		try
		{
			XMLXpathParser domparse=new XMLXpathParser();
			
			return domparse.selectNodes(xpath,nNode);
		}catch(Exception e)
		{
			return null;
		}
	}
	
	
	/**
	 * Copy or remove the node to object node as childnode,if newElementName is null,
	 * then the node don't rename, otherwise rename by newElementName.
	 * if isKeeptheOrdNode is true then don't delete source node ,
	 * otherwise delete it.(importNode or adoptNode)
	 * @param dmToDom
	 * @param ndToNode
	 * @param ndFromNode
	 * @param strNewNodeName
	 * @param bIsKeeptheFromNode
	 */
	public static void importNode(
			Document dmToDom ,
			Node ndToNode,
			Node ndFromNode,
			String strNewNodeName,
			boolean bIsKeeptheFromNode)
	{
		if (ndToNode==null) return;
		
		if (ndFromNode==null) return;
		
		Node getNode=null;
		
		if (bIsKeeptheFromNode)
			getNode=dmToDom.importNode(ndFromNode,true);
		else
			getNode=((DocumentImpl)dmToDom).adoptNode(ndFromNode);
		
		if (getNode==null) return;
		
		if ((strNewNodeName!=null)&&(strNewNodeName.length()>0))
			((DocumentImpl)dmToDom).renameNode(getNode,null,strNewNodeName);
		
		ndToNode.appendChild(getNode);
		
	}
	
	/**
	 *  Copy or remove nodelist to object node as childnodes.
	 *  if isKeeptheOrdNode is trure then don't delete nodelist,otherwise delete it.
	 * @param dmToDom
	 * @param ndToNode
	 * @param nlFromList
	 * @param strNewNodeName
	 * @param bIsKeeptheFromList
	 */
	public static void importNode(
			Document dmToDom ,
			Node ndToNode,
			NodeList nlFromList,
			String strNewNodeName,
			boolean bIsKeeptheFromList)
	{
	
		if (ndToNode==null) return;
		
		if(nlFromList==null) return ;
		
		Node toNode=null;
		
		if ((strNewNodeName!=null)&&(strNewNodeName.length()>0))
		{
			toNode=dmToDom.createElement(strNewNodeName);
			ndToNode.appendChild(toNode);
		}else
			toNode=ndToNode;
		
		int count=nlFromList.getLength();
		
		for (int i=count-1;i>=0;i--)
			importNode(dmToDom,toNode,nlFromList.item(i),null,bIsKeeptheFromList);
	}

	public static Element hashMapToDOM(Map source, String name) {
		if (source == null)
			return null;
		Document rtnDom = new DocumentImpl();
		Element root = rtnDom.createElement("root");
		rtnDom.appendChild(root);
		Element strNameElement = rtnDom.createElement(name);
		root.appendChild(strNameElement);

		String key, value;
		Element node;

		Iterator iter = source.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			key = (String) entry.getKey();
			node = rtnDom.createElement(key);

			Object obj = entry.getValue();
			if (obj instanceof String) {
				if ((value = (String) obj) != null)
					node.appendChild(rtnDom.createTextNode(value));
			}
			strNameElement.appendChild(node);

		}
		return strNameElement;
	}
 
	public static String convertXMLDOMToString(Document doc) throws Exception
	{
		try
		{  
			StringWriter out = new StringWriter();
			OutputFormat outf = new OutputFormat();
			outf.setPreserveSpace(true);
			outf.setOmitXMLDeclaration(true);
			XMLSerializer xmls = new XMLSerializer(out, outf);
			xmls.serialize(doc);
			out.close();
			return out.toString();
	
		}
		catch (Exception e)
		{
			return convertDomToString(doc);
		}
	}
	public static String convertXMLNodeToString(Node node)
	{
		String s=null;
		try
		{
			if(node==null)
				return null;
			Document doc=new DocumentImpl();
			if(node.getNodeType()==Node.ELEMENT_NODE)
			{
				doc=new DocumentImpl();
				doc.appendChild(doc.importNode(node, true));
				s= convertXMLDOMToString(doc);
				doc=null;
			}
			else
			{
				Element root = doc.createElement("root");
				doc.appendChild(root);
				root.appendChild(doc.importNode(node, true));
				s= convertXMLDOMToString(doc);
				root=null;
				doc=null;
			}
		}
		catch(Exception e){}

		return s;
	}

	public static Node findChildNode(Node nParent, String childNodeName)
	{
		try
		{
			if(nParent == null)
				return null;
			NodeList nl = nParent.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++)
			{
				if (nl.item(i).getNodeName().equals(childNodeName))
					return nl.item(i);
			}
	
			return null;
	
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/**
	 * added by machera in order to convert Node to Element directly
	 * @param nParent
	 * @param childNodeName
	 * @return
	 */
	public static Element findChildElement(Node nParent, String childNodeName)
	{
		return (Element)findChildNode(nParent, childNodeName);
	}
 
	public static void writeXMLtoFile(String fileName, Document dom) throws Exception
	{
		writeXMLtoFile(fileName,dom,"UTF-8");
 	}

	public static void writeXMLtoFile(String fileName, Document dom,String charset) throws Exception
	{
		try
		{
			File f = new File(fileName);
			if (!f.exists()) {
				if (!f.createNewFile()) {
//					APLogExec.writeEEError(new StringBuffer(fileName).append(" make failed").toString());
				}
			}
	
			FileOutputStream out = new FileOutputStream(fileName);
			OutputStreamWriter write = new OutputStreamWriter(out, charset);
	
			OutputFormat format = new OutputFormat();
			format.setIndenting(true);
			format.setLineWidth(80);
			format.setLineSeparator("\r\n");
			format.setEncoding(charset);
			XMLSerializer xmls = new XMLSerializer(write, format);
			xmls.serialize(dom);
			write.close();
			out.close();
		}
		catch (Exception exp){
			exp.printStackTrace();
			throw exp;
		}
	
	}

	public static void setNodeValue(Document doc,String strNodeName,String strValue)
	{
		  if (doc==null||strNodeName == null||strNodeName.length()==0 )
			return ;
		  if (doc.getDocumentElement().getElementsByTagName(strNodeName) == null)return;
		  Node n = doc.getDocumentElement().getElementsByTagName(strNodeName).item(0); 
		  Node node = doc.createCDATASection(strValue);
		  n.removeChild(n.getFirstChild());
		  n.appendChild(node);	
	}

	public static void setChildNodeValue(Node node ,String name,String value){
		if (node==null)return;
		Node child = findChildNode(name,node);
		if (child ==null ){
			child = node.getOwnerDocument().createElement(name);
			node.appendChild(child);
			child.appendChild(node.getOwnerDocument().createCDATASection(value));
		}else{
			node.removeChild(child);
			populateCDATAElement(node.getOwnerDocument(), node, name, value);
		}
		
	}
 	
	public static Node findGivenNode(Node node, String strGivenName,String strSeparator,boolean bIngoreRoot)
	   {
		   //strGivenName a.b.c 
		   //strSeparator .
		
		   try
		   {			
			   if (node.getNodeName().equalsIgnoreCase(strGivenName))
				   return node;
			   int index = strGivenName.indexOf(strSeparator);
			   if (index>0) 
			   {
				   String strTempName = strGivenName.substring(0,index);
				   if ( bIngoreRoot && ((node.getNodeType()== Node.ELEMENT_NODE && node.getParentNode().getParentNode()==null)||
					   node.getNodeType()== Node.DOCUMENT_NODE && node.getParentNode()==null) ){
					   //root mode root.a.b.c   strGivenName a.b.c strSeparator .
					   if (node.getNodeType()== Node.ELEMENT_NODE)
					   {
						   NodeList nlTemp = node.getChildNodes();
						   for (int i=0;i<nlTemp.getLength();i++)
						   {
							   Node nd = nlTemp.item(i);
							   nd = findGivenNode(nd,strGivenName,strSeparator,false);
							   if (nd != null )return nd;  
						   }
					   }
					   else if(node.getNodeType()== Node.DOCUMENT_NODE)
					   {
						   Node newNode = ((Document)node).getDocumentElement();
						   return findGivenNode(newNode, strGivenName,strSeparator,true);
					   }
				   }
				   else
				   {
					   //no root mode a.b.c   strGivenName a.b.c strSeparator .
					   if ( node.getNodeName().equalsIgnoreCase(strTempName))
					   {
						   String sGivenName = strGivenName.substring(index+1);
						   NodeList nlTemp = node.getChildNodes();
						   for (int i=0;i<nlTemp.getLength();i++)
						   {
							   Node nd = nlTemp.item(i);
							   nd = findGivenNode(nd,sGivenName,strSeparator,false);
							   if (nd != null )return nd;  
						   } 
					   }
					
				   }
			   }	
			   return null;
		   }
		   catch (Exception e)
		   {
			   return null;
		   }
	   }

	public static String[] getDoXmlValue(Document document, String xpath)
	{
		try
		{
			
			String nValue = null;
			NodeList domList = XMLManager.selectNodes(document,xpath);
			if(domList==null){
				return null;
			}
			int list = domList.getLength();
			String[] aList = new String[list];
			for(int i=0;i<list;i++){
				Node nchild = domList.item(i);
				if (nchild.getNodeType() != Node.ELEMENT_NODE)
					continue;
				nValue =  XMLManager.getNodeValue(nchild,true);
				if(nValue!=null){
					aList[i]=nValue;
				}
			}
			return aList;
		}catch(Exception e)
		{
			return null;
		}
	}
	
	public static String getXpathValue(Document document, String xpath)
	{
		try
		{
			String nValue = null;
			NodeList domList = XMLManager.selectNodes(document,xpath);
			if(domList==null){
				return null;
			}
			int list = domList.getLength();
			for(int i=0;i<list;i++){
				Node nchild = domList.item(i);
				if (nchild.getNodeType() != Node.ELEMENT_NODE)
					continue;
				nValue =  XMLManager.getNodeValue(nchild,true);
			}
			return nValue;
		}catch(Exception e)
		{
			return null;
		}
	}
	
	public static void  replaceXpathNode(Document document, String oXpath,String nXpath)
	{
		try
		{
			NodeList odomList = XMLManager.selectNodes(document,oXpath);
			if(odomList==null){
				return;
			}
			int list = odomList.getLength();
			Node ochildNode = null;
			Element oldNode = null;
			for(int i=0;i<list;i++){
				ochildNode = odomList.item(i);
				oldNode = (Element)ochildNode.getParentNode();
				if (ochildNode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				ochildNode.getParentNode().removeChild(ochildNode);
			}
			NodeList ndomList = XMLManager.selectNodes(document,nXpath);
			if(ndomList==null){
				return;
			}
			int nlist = ndomList.getLength();
			Node nchildNode = null;			
			for(int j=0;j<nlist;j++){
				nchildNode = ndomList.item(j);
				if (nchildNode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				oldNode.appendChild(document.importNode(nchildNode,true));
			}
		}
		catch(Exception e)
		{
			return;
		}
	}
	
	public static void  replaceXpathNodesp(Document oldDom,String oXpath,Document newDom,String nXpath)
	{
		try
		{
			NodeList odomList = XMLManager.selectNodes(oldDom,oXpath);
			if(odomList==null){
				return;
			}
			int list = odomList.getLength();
			Node ochildNode = null;
			Element oldNode = null;
			for(int i=0;i<list;i++){
				ochildNode = odomList.item(i);
				oldNode = (Element)ochildNode.getParentNode();
				if (ochildNode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				ochildNode.getParentNode().removeChild(ochildNode);
			}
			NodeList ndomList = XMLManager.selectNodes(newDom,nXpath);
			if(ndomList==null){
				return;
			}
			int nlist = ndomList.getLength();
			Node nchildNode = null;			
			for(int j=0;j<nlist;j++){
				nchildNode = ndomList.item(j);
				if (nchildNode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				oldNode.appendChild(oldDom.importNode(nchildNode,true));
			}
		}
		catch(Exception e)
		{
			return;
		}
	}
	public static void delDoXmlValue(Document document, String xpath,String oldpath)
	{
		try
		{
			NodeList xdomList = XMLManager.selectNodes(document,xpath);
			NodeList odomList = XMLManager.selectNodes(document,oldpath);
			if(xdomList==null||odomList==null){
				return;
			}
			int xlist = xdomList.getLength();
			for(int i=0;i<xlist;i++){
				Node xchild = xdomList.item(i);
				Node ochild = odomList.item(i);
				if (xchild.getNodeType() != Node.ELEMENT_NODE)
					continue;
				if (ochild.getNodeType() != Node.ELEMENT_NODE)
					continue;
				ochild.removeChild(xchild);		
			}
		}catch(Exception e)
		{
			return;
		}
	}
	
	public static void setDoXmlValue(Document document, String xpath,String fieldName,String fieldValue,String oldPath)
	{
		try
		{
			NodeList domList = XMLManager.selectNodes(document,xpath);
			if(domList==null){
				return;
			}	 
			int list = domList.getLength();			
			if(list == 0){
				NodeList odomList = XMLManager.selectNodes(document,oldPath);
				for(int k=0;k<odomList.getLength();k++){
					Node onchild = odomList.item(k);
					if (onchild.getNodeType() != Node.ELEMENT_NODE)
						continue;
					XMLManager.setChildNodeValue(onchild,fieldName,fieldValue);
				}
			}
			Node ndParent = null;
			String strName = null;
			Element el = null;
			for(int i=0;i<list;i++){
				Node nchild = domList.item(i);
				if (nchild.getNodeType() != Node.ELEMENT_NODE)
					continue;
				ndParent = nchild.getParentNode();
				strName = nchild.getNodeName();
				ndParent.removeChild(nchild);
				el = XMLManager.createXMLElement(ndParent.getOwnerDocument(), strName, fieldValue);
				ndParent.appendChild(el);
			}
		}catch(Exception e)
		{
			return;
		}
	}
	
	public static void setDoXmlValue(Document document, String xpath,String fieldName,
			String fieldValue,String oldPath,String num)
	{
		try
		{
			NodeList domList = XMLManager.selectNodes(document,xpath);
			if(domList==null){
				return;
			}	 
			int list = domList.getLength();			
			if(list == 0){
				NodeList odomList = XMLManager.selectNodes(document,oldPath);
				for(int k=0;k<odomList.getLength();k++){
					Node onchild = odomList.item(k);
					if (onchild.getNodeType() != Node.ELEMENT_NODE)
						continue;
					if(k != Integer.parseInt(num)){
						continue;
					}
					XMLManager.setChildNodeValue(onchild,fieldName,fieldValue);
					if(k == Integer.parseInt(num)){
						break;
					}
				}
			}
			for(int i=0;i<list;i++){
				Node nchild = domList.item(i);
				if (nchild.getNodeType() != Node.ELEMENT_NODE)
					continue;
				if(i != Integer.parseInt(num)){
					continue;
				}
				//XMLManager.setChildNodeValue(nchild.getParentNode(),fieldName,fieldValue);
				//nchild.getParentNode().removeChild(nchild);
				Element stpNode = document.createElement(fieldName);
			    stpNode.appendChild(document.createTextNode(fieldValue));
				nchild.getParentNode().replaceChild(stpNode,nchild);
				if(i == Integer.parseInt(num)){
					break;
				}
			}
		}catch(Exception e)
		{
			return;
		}
	}
	
	public static void setOCBCXmlValue(Document document, String xpath,String keyName,
			String keyValue,String setName,String setValue)
	{
		try
		{
			NodeList domList = XMLManager.selectNodes(document,xpath);
			if(domList==null){
				return;
			}	 
			int list = domList.getLength();			
//			if(list == 0){
//				NodeList odomList = XMLManager.selectNodes(document,oldPath);
//				for(int k=0;k<odomList.getLength();k++){
//					Node onchild = odomList.item(k);
//					if (onchild.getNodeType() != Node.ELEMENT_NODE)
//						continue;
//					XMLManager.setChildNodeValue(onchild,fieldName,fieldValue);
//				}
//			}
			for(int i=0;i<list;i++){
				Node nchild = domList.item(i);
				if (nchild.getNodeType() != Node.ELEMENT_NODE)
					continue;
				if(nchild.getNodeName().equals(keyName)){
					if(nchild.getNodeValue().equals(keyValue)){
						Node child = nchild.getOwnerDocument().createElement(setName);
						nchild.appendChild(child);
						child.appendChild(nchild.getOwnerDocument().createTextNode(setValue));
						//nchild.appendChild(nchild.getOwnerDocument().createTextNode(setValue));
					}
				}
			}
		}catch(Exception e)
		{
			return;
		}
	}
	
	public static double getDoSumAndCount(Document document, String xpath,String type)
	{
		try
		{
			String nValue = null;
			double doSum = 0;
			NodeList domList = XMLManager.selectNodes(document,xpath);
			if(domList==null){
				return 0;
			}
			int list = domList.getLength();
			if(type.equals("C")){
				return list;
			}
			for(int i=0;i<list;i++){
				Node nchild = domList.item(i);
				if (nchild.getNodeType() != Node.ELEMENT_NODE)
					continue;
				nValue =  XMLManager.getNodeValue(nchild,true);
				doSum += Double.parseDouble(nValue);
			}
			return doSum;
		}catch(Exception e)
		{
			return 0;
		}
	}
	
	
	
	
	public static Node findGivenTsuNode(
			Node node,
			String strGivenName,
			String strSeparator,
			boolean bIngoreRoot,
			int k) {
			//strGivenName a.b.c 
			//strSeparator .
		
			try {
				if (node.getNodeName().equalsIgnoreCase(strGivenName.substring(1)))
					return node;
				int index = strGivenName.indexOf(strSeparator);
				if (index > 0) {
		
					String strTempName = strGivenName.substring(1, index);
					if (bIngoreRoot
						&& ((node.getNodeType() == Node.ELEMENT_NODE
							&& node.getParentNode().getParentNode() == null)
							|| node.getNodeType() == Node.DOCUMENT_NODE
							&& node.getParentNode() == null)) {
						//root mode root.a.b.c   strGivenName a.b.c strSeparator .
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							NodeList nlTemp = node.getChildNodes();
							for (int i = 0; i < nlTemp.getLength(); i++) {
								Node nd = nlTemp.item(i);
								nd =
									findGivenTsuNode(
										nd,
										strGivenName,
										strSeparator,
										false,
										Integer.parseInt(strGivenName.substring(0,1)));
								if (nd != null)
									return nd;
							}
						} else if (node.getNodeType() == Node.DOCUMENT_NODE) {
							node = ((Document) node).getDocumentElement();
							return findGivenTsuNode(
								node,
								strGivenName,
								strSeparator,
								true,
								Integer.parseInt(strGivenName.substring(0,1)));
						}
					} else {
						//no root mode a.b.c   strGivenName a.b.c strSeparator .
						String nodeValue = node.getNodeName();
						if (nodeValue.equalsIgnoreCase(strTempName)) {
							strGivenName = strGivenName.substring(index + 1);
							NodeList nlTemp = node.getChildNodes();
							for (int i = 0; i < nlTemp.getLength(); i++) {
								Node nd = nlTemp.item(i);
								nd =
									findGivenTsuNode(
										nd,
										strGivenName,
										strSeparator,
										false,
										Integer.parseInt(strGivenName.substring(0,1)));
								
								if (nd != null && k>0){
									k--;
									continue;
								}
								else if(nd != null)
									return nd;
							}
						}
					}
				}
				return null;
			} catch (Exception e) {
				return null;
			}
		}
	public static String getDMType(Document dom){
		Element root = dom.getDocumentElement();
		return root.getAttribute("DELETE_MASTER_TYPE");
	}

	public static void setNodeValue(Document doc,Node n,String strValue)
	{
		  if (n == null)
			return ;
		  Node node = doc.createCDATASection(strValue);
		  n.removeChild(n.getFirstChild());
		  n.appendChild(node);	
	}
	public static void setNodeValue(Document doc,Node n,String strValue, boolean isCData)
	{
		  if (n == null)
			return ;

		  Node node = null;
		  if(isCData)
			  node=doc.createCDATASection(strValue);
		  else
			  node= doc.createTextNode(strValue);
		  n.removeChild(n.getFirstChild());
		  n.appendChild(node);	
	}

	public static String SPE = File.separator;


	public static Element[] getChildElementByTagName(Element parent, String tagName)
	{
		Element[] e = new Element[0];
		if (parent == null)
			return e;
		if (tagName == null || tagName.length() <= 0)
			return e;
	
		ArrayList al = new ArrayList();
		Node child = parent.getFirstChild();
		while(child != null)
		{
			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(tagName))
				al.add(child);
			child = child.getNextSibling();
		}
		int len = al.size();
		e = new Element[len];
		return (Element[])al.toArray(e);
	}
		
	public static void convertTrxRef(Document trxDoc)
	{
		try
		{
	
			Element eroot = trxDoc.getDocumentElement();
			Element edata = XMLManager.getChildElementByTagName(eroot, "domData")[0];
 			Node ecomp = findChildNode("compAttrInfo",eroot);
			if (ecomp == null)
				return;
			convertTrxRef(eroot, edata, ecomp);
  		}
		catch (Exception e)
		{
			//
 		}
	}

	/**
	 * @param eroot
	 * @param edata
	 * @param ecomp
	 */
	public static void convertTrxRef(Element eroot, Element edata, Node ecomp) {
		String refMapName = getNodeValue(findChildNode(ecomp, "TRXREF"),true);
		if(refMapName==null||refMapName.trim().length()==0)return;
		String refMapValue = getNodeValue(findChildNode(edata, refMapName),true);
	 	if(refMapValue==null){
			Node noriginalData=findNode(eroot, "originalData");
			if(noriginalData!=null)
				refMapValue=getChildNodeValue(noriginalData, refMapName, true);
		}
	  	Element[] etrxRefs = XMLManager.getChildElementByTagName(edata, "C_TRX_REF");
		Element etrxRef = null;

		if (etrxRefs.length > 0) {
 			etrxRef = etrxRefs[0];
			edata.removeChild(etrxRef);
		}

		etrxRef = eroot.getOwnerDocument().createElement("C_TRX_REF");
		etrxRef.appendChild(eroot.getOwnerDocument().createTextNode(refMapValue));
		edata.appendChild(etrxRef);
	}
	public static String getMappingTrxRef(Document trxdoc, String refMapName) {
		if(refMapName==null||refMapName.trim().length()==0)return null;
		Element eroot = trxdoc.getDocumentElement();
		Element edata = (Element) XMLManager.findChildNode(eroot, "domData");
		String refMapValue = getNodeValue(findChildNode(edata, refMapName),true);
	 	if(refMapValue==null){
			Node noriginalData=findNode(eroot, "originalData");
			if(noriginalData!=null)
				refMapValue=getChildNodeValue(noriginalData, refMapName, true);
		}
		return refMapValue;
	}

	public static void mergerInqData(Document resultDoc, Document mergeDoc, Element eRecord)
	{
		try
		{
			Element eResultRoot = (Element) resultDoc.getDocumentElement().getFirstChild();
			if (eResultRoot == null) {
				return;
			}
			NodeList nl = eResultRoot.getChildNodes();
			Element eResultRecord = (Element) nl.item(0);
			if (eResultRecord == null) {
				return;
			}
	
			nl = eResultRecord.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++)
			{
				String name = nl.item(i).getNodeName();
				if (nl.item(i).getNodeType() != Node.ELEMENT_NODE)
					continue;
	
				if (eRecord.getElementsByTagName(name).getLength() > 0)
				   eRecord.removeChild(findChildNode(name,eRecord));
			    
				eRecord.appendChild(mergeDoc.importNode(nl.item(i), true));
	
			}
		}
		catch (Exception e)	{
			//
		}
	}


	/**
	 * Generate the error xml document
	 * @param errorMessage error Message
	 * @return Document
	 */
	
	   public static DocumentImpl generateErrorDom(String errorMessage){
	   	  
	   	   DocumentImpl dom = new DocumentImpl();
	   	   //<root>
	   	   Element eleRoot = dom.createElement("root");
	   	   eleRoot.setAttribute("errMsg",errorMessage);
	   	   //<pubInfo>
	   	   Element elePubInfo = dom.createElement("pubInfo");
	   	   //<userInfo>
	   	   Element eleUserInfo = dom.createElement("userInfo");
	   	   //<domData>
	   	   Element eleDomData = dom.createElement("domData");
	   	   
	   	   elePubInfo.appendChild(eleUserInfo);
	   	   eleRoot.appendChild(elePubInfo);
	   	   elePubInfo.appendChild(eleDomData);
	   	   dom.appendChild(eleRoot);
	   	   
	   	   return dom; 
	   }

	   /**
	   * Find Node(static)
	   * @param nl nodeList,
	   * @param nodeName node Name
	   * @return Node
	   * */  
	public static Node find(NodeList nl,String nodeName){
 
	  	return findChildNode(nodeName, nl);
	}

	public static String getNodeValueFromResultDom(Document resultDom, String NodeName)
	{
		if (resultDom == null)
			return null;
		try
		{
			NodeList nl = resultDom.getElementsByTagName(NodeName);
			if (nl == null)
				return null;
			Node n = nl.item(0);
			return getNodeValue(n, true);
		}
		catch (Exception e)
		{
			return null;
		}
	
	}

	//add over	
	public static DocumentImpl getSQLStatementNode(Document sqlDom) throws Exception
	{
		DocumentImpl returnDom = new DocumentImpl();
		Element root = returnDom.createElement("root");
		returnDom.appendChild(root);
		Node dsNode = findNode(sqlDom, "C_DS_ID");
		if (dsNode != null)
			root.appendChild(returnDom.importNode(dsNode, true));
		Node stmtNode = findNode(sqlDom, "SQLStatement");
		if (stmtNode != null)
			root.appendChild(returnDom.importNode(stmtNode, true));
		return returnDom;
	}
	
	public static Element populateElement(Document dom, Node parentNode, String eleName, String eleValue, boolean isCData)
	{
		Element ele = dom.createElement(eleName);
		parentNode.appendChild(ele);
		if(eleValue != null)
		{
			if(isCData)
			{
				ele.appendChild(dom.createCDATASection(eleValue));
			}
			else
			{
				ele.appendChild(dom.createTextNode(eleValue));
			}
		}
		return ele;
	}
	public static Element populateCDATAElement(Document dom, Node parentNode, String eleName, String eleValue)
	{
		Element ele = populateElement(dom, parentNode, eleName, eleValue, true);
		return ele;
	}
	public static Element populateTextElement(Document dom, Node parentNode, String eleName, String eleValue)
	{
		Element ele = populateElement(dom, parentNode, eleName, eleValue, false);
		return ele;
	}
	
	public static void removeChildNode(Node parentNode, String childName)
	{
		if(parentNode == null || childName == null)
			return;
		Element[] eles = getChildElementByTagName((Element)parentNode, childName);
		for(int i = 0; i < eles.length; i++)
		{
			parentNode.removeChild(eles[i]);
		}
	}
	public static void setNodeAttribute(Node node, String attriName, String value){
		Element el = (Element)node;
		el.removeAttribute(attriName);
		el.setAttribute(attriName, value);
	}

	public static String getNValue(Document docu, String nName) throws Exception {
		Node node = findNode(docu.getDocumentElement(), nName);
		return getNodeValue(node, true);
	}
	
	public static Document cloneNodeExceptDomData(Document src,boolean deep){
		
		Document doc = new DocumentImpl();
		Element elm = src.getDocumentElement();
		doc.appendChild(doc.importNode(elm, false));
		Element root = doc.getDocumentElement();
		Node node = elm.getFirstChild();
		while(node !=null) {
			if(node.getNodeType() != Node.ELEMENT_NODE){
 				node = node.getNextSibling();
				continue;
			}
			String nodeName=node.getNodeName();
			if ("domData".equalsIgnoreCase(nodeName)){
				node = node.getNextSibling();
				continue;
			}
			Node cloneNode = node.cloneNode(deep);
			root.appendChild(doc.importNode(cloneNode, true));
			node=node.getNextSibling();
		};
		
		return doc;
	}
	
	public static Document cloneNode(Document src,boolean deep){
		
		Document doc = new DocumentImpl();
		Element elm = src.getDocumentElement();
		String rootName=elm.getNodeName();
		Element root = doc.createElement(rootName);
		doc.appendChild(root);
		Node node = elm.getFirstChild();
		while(node !=null) {
			if(node.getNodeType() != Node.ELEMENT_NODE){
 				node = node.getNextSibling();
				continue;
			}
 			Node cloneNode = node.cloneNode(deep);
			root.appendChild(doc.importNode(cloneNode, true));
			node=node.getNextSibling();
		};
		
		return doc;
	}
	
	public static String replaceAll(String value, String oldStr, String newStr) throws Exception{
	    try{
	    	
	        int index = value.indexOf(oldStr);
	        StringBuffer buf = new StringBuffer();
	        if(index<0)
	            return value;
	        
	        while(index>0){
	            buf.append(value.substring(0, index)).append(newStr);
	            value = value.substring(index+1);
	            index = value.indexOf(oldStr);
	        }
	        buf.append(value);
	        return buf.toString();
	    }catch(Exception e){
	        throw e;
	    }
	}
	public static Element getFirstChildElement(Element parent)
	{
		if(parent==null)
			return null;
		Node nd = parent.getFirstChild();
		while(nd!=null)
		{
			if(nd.getNodeType()==Node.ELEMENT_NODE)
			{
				return (Element)nd;
			}
			else
				nd=nd.getNextSibling();
		}
		return null;
	}
	public static Element getNextSiblingElement(Element previous)
	{
		if(previous==null)
			return null;
		Node nd = previous.getNextSibling();;
		while(nd!=null)
		{
			if(nd.getNodeType()==Node.ELEMENT_NODE)
			{
				return (Element)nd;
			}
			else
				nd=nd.getNextSibling();
		}
		return null;
	}

	public static Document xmlFileInputSourceToDocument(InputSource inputSource){
		Document document = null;
		try {
			DOMParser parser = new DOMParser();
			parser.parse(inputSource);
			document = parser.getDocument();
			return document;
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
	public static String translateXML2String(Document docSource){
		try {
		    TransformerFactory transFact = TransformerFactory.newInstance();
		    Transformer trans = transFact.newTransformer();
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw); 
		    trans.transform(new DOMSource(docSource), result);
		    String xmlString = sw.toString();
		    return xmlString;
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return convertDomToString(docSource);
	}
}