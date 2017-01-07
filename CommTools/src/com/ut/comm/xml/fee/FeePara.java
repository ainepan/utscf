package com.ut.comm.xml.fee;

import java.util.List;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;
import com.ut.comm.xml.method.MethodDefinePara;

public class FeePara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FeePara() {
		XMLParaHelper.registeObjectBean(XMLParaHelper.NOTE_NAME_METHOD_DEFINE, MethodDefinePara.class);
	}

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_FEE;
	}
	
	private String name;
	private String desc;
	
	/**
	 * 3 : 标记为费用
	 */
	private String label;
	/**
	 * 0:费随本清（后收费）
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
	 * 0:单笔定额
	 * 1：本金抽点
	 */
	private String type;
	
	/**
	 * 定额值
	 */
	private String fixamount;
	
	/**
	 * 抽点类型可以设置上下限
	 */
	private String minamount;
	private String maxamount;
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
	 * 利润分成
	 * non:不分
	 * others:选择分配方案
	 */
	private String profitshare;
	
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

	public String getFixamount() {
		return fixamount;
	}

	public void setFixamount(String fixamount) {
		this.fixamount = fixamount;
	}

	public String getMinamount() {
		return minamount;
	}

	public void setMinamount(String minamount) {
		this.minamount = minamount;
	}

	public String getMaxamount() {
		return maxamount;
	}

	public void setMaxamount(String maxamount) {
		this.maxamount = maxamount;
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

	public String getProfitshare() {
		return profitshare;
	}

	public void setProfitshare(String profitshare) {
		this.profitshare = profitshare;
	}

	public List<MethodDefinePara> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodDefinePara> methods) {
		this.methods = methods;
	}

}
