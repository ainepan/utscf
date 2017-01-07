package com.ut.comm.xml.accounting.sys;

import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.accounting.AccountingNoPara;

public class AccountSysPara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6483452549164077966L;

	@Override
	public String getNodeName() {
		return null;
	}
	
	private String name;
	private String desc;
	
	/**
	 * ？ : 标记为账务体系
	 */
	private String label;
	
	private String type;
	
	private List<AccountingNoPara> accountNos;

}
