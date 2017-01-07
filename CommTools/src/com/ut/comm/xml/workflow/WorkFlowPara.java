package com.ut.comm.xml.workflow;

import com.ut.comm.tool.consts.ASPathConst;
import com.ut.comm.tool.string.StringUtil;
import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class WorkFlowPara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String desc;
	
	private String jspath;
	
	private String areacode;
	
	private String workid;//平台中的流程定义编号
	
	private String workname;//参数工具中的流程名称

	private String status;//是否创建工作流
	
	private String dpworkname;//dp工作流名称

	public String getDpworkname() {
		return dpworkname;
	}

	public void setDpworkname(String dpworkname) {
		this.dpworkname = dpworkname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public String getJspath() {
		return jspath;
	}

	public void setJspath(String jsPath) {
		this.jspath = jsPath;
	}

	public String getAreacode() {
		if(StringUtil.isTrimEmpty(areacode))
			areacode = ASPathConst.getDefaultBuName();
		return areacode;
	}

	public void setAreacode(String areaCode) {
		this.areacode = areaCode;
	}

	public String getWorkid() {
		return workid;
	}

	public void setWorkid(String workid) {
		this.workid = workid;
	}

	public String getWorkname() {
		return workname;
	}

	public void setWorkname(String workname) {
		this.workname = workname;
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_WORKFLOW;
	}

}
