package com.ut.comm.xml.party;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.accounting.AccountingNoPara;

/**
 * 参与方定义
 * @author aine
 *
 */
public class PartyPara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5621596271714314429L;

	public PartyPara(){
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_ACCOUNT_NO, AccountingNoPara.class);
	}
	
	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_PARTY;
	}
	
	private String name;
	private String desc;
	
	/**
	 * 13 : 标记为参与方
	 */
	private String label;
	
	/**
	 * 当前角色的有效性
	 * Y:有效
	 * N：无效
	 */
	
	private String valid;
	
	/**
	 * 资方最大额度
	 * 买方授信额度
	 * 卖方有效额度
	 */
	private String maxamt;
	
	/**
	 * M,S,Y,HY
	 * 统计额度的频率
	 */
	private String finfrec;
	
	/**
	 * 0:平台运营方
	 * 1：资方
	 * 2：买方（核心企业）
	 * 3：卖方（供应商）
	 * 4：仓储
	 * 5：其他
	 */
	private String type;
	
	private List<AccountingNoPara> accountList;

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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getMaxamt() {
		return maxamt;
	}

	public void setMaxamt(String maxamt) {
		this.maxamt = maxamt;
	}

	public String getFinfrec() {
		return finfrec;
	}

	public void setFinfrec(String finfrec) {
		this.finfrec = finfrec;
	}

	public List<AccountingNoPara> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AccountingNoPara> accountList) {
		this.accountList = accountList;
	}
	
	public void setAccount(AccountingNoPara account, int index) {
		if(this.accountList==null)
			this.accountList = new ArrayList<AccountingNoPara>();
		if(index>this.accountList.size())
			this.accountList.add(account);
	}
	
	public void setAccount(AccountingNoPara account) {
		if(this.accountList==null)
			this.accountList = new ArrayList<AccountingNoPara>();
		this.accountList.add(account);
	}
	
	public AccountingNoPara getAccount(int index){
		AccountingNoPara accountPara = accountList.get(index);
//		XMLParaParseHelper.parseAccountNoPara(accountPara,super.getBu());
		return accountPara;
	}
	
	public int getAccountSize(){
		return this.accountList==null?0:this.accountList.size();
	}
	
}
