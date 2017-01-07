package com.ut.comm.xml.template;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.mapping.HeadPara;
import com.ut.comm.xml.mapping.MappingPara;

public class TemplatePara extends AbsObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TemplatePara(){
		super();
		XMLParaHelper.registeObjectBean("head", HeadPara.class);
		XMLParaHelper.registeObjectBean("mapping", MappingPara.class);
	}

	private String name;
	private String desc;
	private String type;
	private String temptype;
	private String tempfile;
	private int startrow;
	private String separator;
	
	private MappingPara mapping;
	private HeadPara head;
	
	private String reformatclass;
	
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

	public String getTemptype() {
		return temptype;
	}

	public void setTemptype(String temptype) {
		this.temptype = temptype;
	}

	public String getTempfile() {
		return tempfile;
	}

	public void setTempfile(String tempfile) {
		this.tempfile = tempfile;
	}

	public int getStartrow() {
		return startrow;
	}

	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public MappingPara getMapping() {
//		this.mapping.parseXml(currentNode);
//		this.mapping = XMLParaParseHelper.parseFieldAttribute(this.mapping,this.mapping.getCurrentNode());
		return mapping;
	}

	public void setMapping(MappingPara mapping) {
		this.mapping = mapping;
	}

	public HeadPara getHead() {
//		this.head.parseXml(currentNode);
//		this.head = XMLParaParseHelper.parseFieldAttribute(this.head,this.head.getCurrentNode());
		return head;
	}

	public void setHead(HeadPara head) {
		this.head = head;
	}

	public String getReformatclass() {
		return reformatclass;
	}

	public void setReformatclass(String reformatclass) {
		this.reformatclass = reformatclass;
	}

	@Override
	public String getNodeName() {
		return "template";
	}
	
}
