package com.ut.comm.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtil {
	
	public Document LoadXmlByStr(String xmlstr) throws DocumentException {
		return DocumentHelper.parseText(xmlstr);
	}

	public Document LoadXmlByFile(File xmlfile) throws DocumentException {
		SAXReader reader = new SAXReader();
		return reader.read(xmlfile);
	}

	public Map<String, Object> getElements(String xmlStr) throws DocumentException {
		return getElements(DocumentHelper.parseText(xmlStr).getRootElement());
	}
	
	public Map<String, Object> getElements(File xmlfile) throws DocumentException {
		return getElements(new SAXReader().read(xmlfile).getRootElement());
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getElements(Element element) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Element> children = element.elements();
		if (null == children) return result;
		for (Element e : children) {
			//如果当前节点的子节点为空时，则向map中添加节点
			if (e.elements().isEmpty()) {
				//如果返回的Map中已经包含了当前节点，分为两种情况
				//1.如果value类型是List，则向list中增加当前节点的text
				//2.如果value类型不是List，也就是单值，则将value的值塞入新增的List中，再将当前节点的text也塞入其中
				if (result.containsKey(e.getName())) {
					if (result.get(e.getName()) instanceof List) {
						((List)result.get(e.getName())).add(!"NULL".equals(e.getText()) ? e.getText() : null);
					} else {
						List list = new ArrayList();
						list.add(result.get(e.getName()));
						list.add(!"NULL".equals(e.getText()) ? e.getText() : null);
						result.put(e.getName(), list);
					}
				} else {
					result.put(e.getName(), !"NULL".equals(e.getText()) ? e.getText() : null);
				}
			} else {
				//说明类似如上，不同点是取的当前节点的不是text，而是递归出来的Map
				if (result.containsKey(e.getName())) {
					if (result.get(e.getName()) instanceof List) {
						((List)result.get(e.getName())).add(getElements(e));
					} else {
						List list = new ArrayList();
						list.add(result.get(e.getName()));
						list.add(getElements(e));
						result.put(e.getName(), list);
					}
				} else {
					result.put(e.getName(), getElements(e));
				}
			}
		}
		return result;
	}
	
	public String getMessage(Map<String, Object> map, String rootName) {
		return this.getMessage(map, rootName, "UTF-8");
	}
	
	public String getMessage(Map<String, Object> map, String rootName, String charaset) {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding(charaset);
		doc.addElement(rootName);
		this.getMessage(doc.getRootElement(), map);
		return doc.asXML();
	}

	/**
	 * @param root 根节点 
	 * @param map 节点信息
	 */
	public void getMessage(Element root, Map<String, Object> map) {
		this.addElementByMap(root, map);
	}

	/**
	 * @param parent 父节点
	 * @param map 当前节点信息
	 */
	private void addElementByMap(Element parent, Map<String, Object> map) {
		for (String key : map.keySet()) {
			this.addElementByString(parent, key, map.get(key));
		}
	}

	/**
	 * @param parent 父节点
	 * @param listNode 当前节点名
	 * @param value 当前节点的LIST信息
	 */
	@SuppressWarnings("unchecked")
	private void addElementByList(Element parent, String listNode, List value) {
		int size = value.size();
		if (size == 0) {
			Element node = parent.addElement(listNode);
			node.setText("");
			return;
		}
		for (int i = 0; i < size; i++) {
			Element node = parent.addElement(listNode);
			Object nodeValue = value.get(i);
			// List 中只能有String和Map类型，再有List类型就没有意义了
			// 所以这里只分析了两种类型
			if (nodeValue instanceof String) {
				node.setText((String) (nodeValue == null ? "NULL" : nodeValue));
			} else if (nodeValue instanceof Map) {
				addElementByMap(node, (Map) nodeValue);
			}
		}
	}

	/**
	 * @param parent 父节点
	 * @param nodePath 节点名信息 如：head/toll/value
	 * @param value 节点值
	 */
	@SuppressWarnings("unchecked")
	private void addElementByString(Element parent, String nodePath, Object value) {
		String[] nodes = nodePath.split("/");
		int size = nodes.length;
		for (int i = 0; i < size - 1; i++) {
			if (parent.element(nodes[i]) == null) {
				parent = parent.addElement(nodes[i]);
			} else {
				parent = parent.element(nodes[i]);
			}
		}
		//list的情况比较特殊
		if (value instanceof List) {
			addElementByList(parent, nodes[size - 1], (List) value);
		} else {
			if (parent.element(nodes[size - 1]) == null) {
				parent = parent.addElement(nodes[size - 1]);
			} else {
				parent = parent.element(nodes[size - 1]);
			}
			if (value instanceof String) {
				parent.setText((String)value);
			} else if (value instanceof Map) {
				addElementByMap(parent, (Map) value);
			} else if (value == null) {
				parent.setText("NULL");
			} else {
				parent.setText(value.toString());
			}
		}
	}
}
