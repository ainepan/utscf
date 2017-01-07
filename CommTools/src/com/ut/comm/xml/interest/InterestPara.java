package com.ut.comm.xml.interest;

import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.method.MethodDefinePara;

public class InterestPara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7496426779344621393L;
	
	public InterestPara() {
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_METHOD_DEFINE, MethodDefinePara.class);
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_INTEREST;
	}
	
	private String name;
	private String desc;
	
	/**
	 * 2 : 标记为利息
	 */
	private String label;
	/**
	 * 0:利随本清（后收费）
	 * 1：先收费
	 * 2：计提
	 */
	private String module;
	
	/**
	 * D:日结
	 * W：周结
	 * M：月结
	 * S：季结
	 * HY：半年结
	 * Y：年结
	 */
	private String frequency;
	
	/**
	 * 结算周期内的具体天数，如：
	 * W：存周几
	 * M：存具体哪天
	 */
	private String freqday;
	
	private String subject;
	/**
	 * 0:单利
	 * 1：复利
	 */
	private String type;
	
	/**
	 * 定额值
	 * others:其他依赖客户等级的报价
	 */
	private String rate;
	
	/**
	 * trx:根据交易获取
	 * others:固定币别代码
	 */
	private String ccy;
	private String discount;
	/**
	 * 折扣计算逻辑
	 * non：不支持折扣
	 * any：页面输入折扣
	 * others:其他依赖客户等级的报价
	 */
	private String discondition;
	
	/**
	 * 结算账号类型
	 * any:不固定
	 * others:从账号体系中选择类型
	 */
	private String settleaccount;
	
	/**
	 * 360 
	 * 365
	 */
	private String basedays;
	
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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFreqday() {
		return freqday;
	}

	public void setFreqday(String freqday) {
		this.freqday = freqday;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDiscondition() {
		return discondition;
	}

	public void setDiscondition(String discondition) {
		this.discondition = discondition;
	}

	public String getSettleaccount() {
		return settleaccount;
	}

	public void setSettleaccount(String settleaccount) {
		this.settleaccount = settleaccount;
	}

	public String getBasedays() {
		return basedays;
	}

	public void setBasedays(String basedays) {
		this.basedays = basedays;
	}

	public List<MethodDefinePara> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodDefinePara> methods) {
		this.methods = methods;
	}

}
