package com.ut.comm.xml.accounting;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class AccountingNoPara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 0:实体账号，可以关联出账使用
	 * 1：虚拟账号，不可以关联出账使用
	 */
	private String type;
	
	private String desc;
	
	private String accountNo;
	
	/**
	 * 0:实体账户，直接使用Account的值
	 * 1：关联账户，使用映射交易中的栏位，任然使用account的值作为映射的名称
	 */
	private String acctp;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_ACCOUNT_NO;
	}

	public String getAcctp() {
		return acctp;
	}

	public void setAcctp(String acctp) {
		this.acctp = acctp;
	}

}
