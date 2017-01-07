package com.ut.comm.xml.accounting;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class AccountingDefine  extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 289508790432576709L;

	public AccountingDefine(){
		XMLParaHelper.registeObjectBean("account", AccountingNoPara.class);
	}
	
	private List<AccountingNoPara> accountList;

	public List<AccountingNoPara> getAccountList() {
		
		return accountList;
	}

	public void setAccountList(List<AccountingNoPara> accountList) {
		this.accountList = accountList;
	}
	
	public void setAccount(AccountingNoPara account) {
		if(this.accountList==null)
			this.accountList = new ArrayList<AccountingNoPara>();
		this.accountList.add(account);
	}
	
	public AccountingNoPara getAccount(int index){
		AccountingNoPara accountObj = accountList.get(index);
		return accountObj;
	}
	
	public int size(){
		return accountList.size();
	}

	@Override
	public String getNodeName() {
		return "accountdefine";
	}

}
