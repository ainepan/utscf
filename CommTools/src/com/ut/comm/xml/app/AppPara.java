package com.ut.comm.xml.app;

import java.util.ArrayList;
import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.XMLParaParseHelper;
import com.ut.comm.xml.accounting.AccountingNoPara;
import com.ut.comm.xml.method.MethodDefinePara;

/**
 * 外围系统定义
 * @author PanHao
 *
 */
public class AppPara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4565793568553666547L;

	public AppPara(){
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_METHOD_DEFINE, MethodDefinePara.class);
	}
	
	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_APP;
	}
	
	private String name;
	private String desc;
	
	/**
	 * 12 : 标记为外围系统
	 */
	private String label;
	
	/**
	 * 系统开发商
	 */
	private String provider;
	
	private List<MethodDefinePara> methods;

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

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public List<MethodDefinePara> getMethoddefineList() {
		return methods;
	}

	public void setMethoddefine(MethodDefinePara account, int index) {
		if(this.methods==null)
			this.methods = new ArrayList<MethodDefinePara>();
		if(index>this.methods.size())
			this.methods.add(account);
	}
	
	public void setMethoddefine(MethodDefinePara account) {
		if(this.methods==null)
			this.methods = new ArrayList<MethodDefinePara>();
		this.methods.add(account);
	}
	
	public MethodDefinePara getMethoddefine(int index){
		MethodDefinePara accountPara = methods.get(index);
		XMLParaParseHelper.parseMethodDefinePara(accountPara,super.getBu());
		return accountPara;
	}
	
	public int getMethoddefineSize(){
		return this.methods==null?0:this.methods.size();
	}

}
