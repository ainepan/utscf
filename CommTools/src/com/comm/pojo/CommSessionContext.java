package com.comm.pojo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import com.comm.pojo.ISessionEntity;
import com.ut.comm.tool.BeanUtils;
import com.ut.comm.tool.json.JsonUtil;

public class CommSessionContext implements ISessionEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5068637124567473764L;
	private Timestamp sysOpTm;
	private int sysEventTimes;
	private String sysFuncId;
	private String sysFuncName;
	private String sysOrgnFuncId;
	private String sysOrgnFuncName;
	private String sysBusiUnit;
	
	private String sessId;
	private String sysUserId;
	private String datasource;
	private String sysUserRef;
	/**
	 * 交易状态
	 * 目前分为： 申请（P） 复核（M） 保存（S） 逻辑删除（D）临时数据（T）
	 */
	private String strTrxStatus;
	/**
	 * 交易类型
	 * 目前分为： 查询（Query） 提交（Submit） 取消（Cancel） 上传（Download）
	 * ComponentConst.COMP_TRX_TYPE_*
	 */
	private String strTrxType;
	private String sysRefNo;
	/**
	 * 请求类型
	 * 暂时未划分
	 *
	 */
	private String reqType;
	/**
	 * 所有页面中，当前页面 index
	 */
	private int sysPageIndex;
	/**
	 * 所有页面总和
	 */
	private int sysTotalPage;
	/**
	 * 所有交易页面中，当前页面 index
	 */
	private int sysTrxPageIndex;
	/**
	 * 所有交易页面总和
	 */
	private int sysTrxTotalPage;
	
	private String isAgree;
	
	private String sysFuncType;
	
	private static final String commName = "comm";
	private static final String userInfoName = "userInfo";
	private static final String funcInfoName = "funcInfo";
	private static final String trxDomName = "trxDom";
	
	private static List<String> requestNode = new ArrayList<String>();
	
	static{
		requestNode.add(commName);
		requestNode.add(userInfoName);
		requestNode.add(funcInfoName);
		requestNode.add(trxDomName);
	}
	
	private Map<String,Object> attributes = null;
	
	public void setAttribute(String name,Object value){
		attributes.put(name, value);
	}
	
	public Object getAttribute(String name){
		return attributes.get(name);
	}
	public void removeAttribute(String name)
	{
		attributes.remove(name);
	}
	
	@Override
	public void initialize(Object reqDom) {
		attributes = new ConcurrentHashMap<String,Object>();
		if(reqDom == null){
			return;
		}
		
		for(String nodeName : requestNode){
			JSONObject data = JsonUtil.getChildJson((JSONObject) reqDom, nodeName);
			try {
				Map dataMap = JsonUtil.getMapFromJson(data);
				BeanUtils.setBeanProperty(this,dataMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void updateTrxData(Object reqDom) {
		if(reqDom == null){
			return;
		}
		
		for(String nodeName : requestNode){
			JSONObject data = JsonUtil.getChildJson((JSONObject) reqDom, nodeName);
			try {
				Map dataMap = JsonUtil.getMapFromJson(data);
				BeanUtils.setBeanProperty(this,dataMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void destroy() {
		if(attributes!=null)
			attributes.clear();
	}
	
	public String getSysOpId() {
		return sysUserId;
	}
	
	public String getUserId() {
		return sysUserId;
	}
	
	public Timestamp getSysOpTm() {
		return sysOpTm;
	}

	public void setSysOpTm(Timestamp sysOpTm) {
		this.sysOpTm = sysOpTm;
	}

	public int getSysEventTimes() {
		return sysEventTimes;
	}

	public void setSysEventTimes(int sysEventTimes) {
		this.sysEventTimes = sysEventTimes;
	}

	public String getSysFuncId() {
		return sysFuncId;
	}

	public void setSysFuncId(String sysFuncId) {
		this.sysFuncId = sysFuncId;
	}

	public String getSysFuncName() {
		return sysFuncName;
	}

	public void setSysFuncName(String sysFuncName) {
		this.sysFuncName = sysFuncName;
	}

	public String getSysOrgnFuncName() {
		return sysOrgnFuncName;
	}

	public void setSysOrgnFuncName(String sysOrgnFuncName) {
		this.sysOrgnFuncName = sysOrgnFuncName;
	}

	/**
	 * 原始Function Type
	 * 目前分为： PM，RE，MM，FP，EC，VH，VW，CA，RS
	 * @return
	 */
	public String getSysFuncType() {
		return sysFuncType;
	}

	public void setSysFuncType(String sysFuncType) {
		this.sysFuncType = sysFuncType;
	}

	/**
	 * 交易状态
	 * 目前分为： 申请（P） 复核（M） 保存（S） 逻辑删除（D）
	 * @return
	 */
	public String getStrTrxStatus() {
		return strTrxStatus;
	}

	public void setStrTrxStatus(String strTrxStatus) {
		this.strTrxStatus = strTrxStatus;
	}
	/**
	 * 交易类型
	 * 目前分为： 查询（Query） 提交（Submit） 取消（Cancel） 上传（Download）
	 * ComponentConst.COMP_TRX_TYPE_*
	 * @return
	 */
	public String getStrTrxType() {
		return strTrxType;
	}

	public void setStrTrxType(String strTrxType) {
		this.strTrxType = strTrxType;
	}

	public String getSysRefNo() {
		return sysRefNo;
	}

	public void setSysRefNo(String sysRefNo) {
		this.sysRefNo = sysRefNo;
	}

	public String getReqType() {
		return reqType;
	}

	public int getSysPageIndex() {
		return sysPageIndex;
	}

	public void setSysPageIndex(int sysPageIndex) {
		this.sysPageIndex = sysPageIndex;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getSysOrgnFuncId() {
		return sysOrgnFuncId;
	}

	public void setSysOrgnFuncId(String sysOrgnFuncId) {
		this.sysOrgnFuncId = sysOrgnFuncId;
	}

	public int getSysTotalPage() {
		return sysTotalPage;
	}

	public void setSysTotalPage(int sysTotalPage) {
		this.sysTotalPage = sysTotalPage;
	}

	public int getSysTrxPageIndex() {
		return sysTrxPageIndex;
	}

	public void setSysTrxPageIndex(int sysTrxPageIndex) {
		this.sysTrxPageIndex = sysTrxPageIndex;
	}

	public int getSysTrxTotalPage() {
		return sysTrxTotalPage;
	}

	public void setSysTrxTotalPage(int sysTrxTotalPage) {
		this.sysTrxTotalPage = sysTrxTotalPage;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public String getSysBusiUnit() {
		return sysBusiUnit;
	}

	public void setSysBusiUnit(String sysBusiUnit) {
		this.sysBusiUnit = sysBusiUnit;
	}

	public String getSessId() {
		return sessId;
	}

	public void setSessId(String sessId) {
		this.sessId = sessId;
	}

	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getSysUserRef() {
		return sysUserRef;
	}

	public void setSysUserRef(String sysUserRef) {
		this.sysUserRef = sysUserRef;
	}
}