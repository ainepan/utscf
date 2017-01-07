package com.ut.comm.xml.accounting;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class AccountingObj extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public AccountingObj(){
		XMLParaHelper.registeObjectBean("accountdefine", AccountingDefine.class);
		this.setId("Accounting_Root");
	}
	
	private AccountingDefine accountDefine;

	public AccountingDefine getAccountDefine() {
		return accountDefine;
	}

	public void setAccountdefine(AccountingDefine accountDefine) {
		this.accountDefine = accountDefine;
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_ACCOUNTING;
	}
}
