package com.ut.comm.xml.accounting;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

/**
 * 账务规则定义
 * @author PanHao
 *
 */
public class AccountingPara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_ACCOUNTING;
	}

	private String name;
	private String desc;
	
	/**
	 * 4 : 标记为费用
	 */
	private String label;
}
