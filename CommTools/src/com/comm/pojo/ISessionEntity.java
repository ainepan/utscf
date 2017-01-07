package com.comm.pojo;

import java.io.Serializable;
import java.sql.Timestamp;


public interface ISessionEntity extends Serializable{
	public void initialize(Object reqDom);
	
	public void destroy();
	
	public void updateTrxData(Object reqDom);
	
	public void setAttribute(String name,Object value);
	
	public Object getAttribute(String name);
	
	public void removeAttribute(String name);
	
	public String getSysOpId() ;
	
	public String getUserId();
	
	public Timestamp getSysOpTm() ;

	public void setSysOpTm(Timestamp sysOpTm) ;

	public int getSysEventTimes() ;

	public void setSysEventTimes(int sysEventTimes) ;

	public String getSysFuncId() ;

	public void setSysFuncId(String sysFuncId);

	public String getSysFuncName() ;

	public void setSysFuncName(String sysFuncName);

	public String getSysOrgnFuncName() ;

	public void setSysOrgnFuncName(String sysOrgnFuncName) ;

	/**
	 * 原始Function Type
	 * 目前分为： PM，RE，MM，FP，EC，VH，VW，CA，RS
	 * @return
	 */
	public String getSysFuncType() ;

	public void setSysFuncType(String sysFuncType) ;

	/**
	 * 交易状态
	 * 目前分为： 申请（P） 复核（M） 保存（S） 逻辑删除（D）
	 * @return
	 */
	public String getStrTrxStatus() ;

	public void setStrTrxStatus(String strTrxStatus);
	/**
	 * 交易类型
	 * 目前分为： 查询（Query） 提交（Submit） 取消（Cancel） 上传（Download）
	 * ComponentConst.COMP_TRX_TYPE_*
	 * @return
	 */
	public String getStrTrxType() ;

	public void setStrTrxType(String strTrxType) ;

	public String getSysRefNo();

	public void setSysRefNo(String sysRefNo);

	public String getReqType() ;

	public int getSysPageIndex() ;

	public void setSysPageIndex(int sysPageIndex);

	public void setReqType(String reqType);

	public String getSysOrgnFuncId();

	public void setSysOrgnFuncId(String sysOrgnFuncId);

	public int getSysTotalPage() ;

	public void setSysTotalPage(int sysTotalPage);

	public int getSysTrxPageIndex();

	public void setSysTrxPageIndex(int sysTrxPageIndex);

	public int getSysTrxTotalPage();

	public void setSysTrxTotalPage(int sysTrxTotalPage) ;

	public String getIsAgree() ;

	public void setIsAgree(String isAgree) ;

	public String getSysBusiUnit();

	public void setSysBusiUnit(String sysBusiUnit);

	public String getSessId();

	public void setSessId(String sessId);

	public String getSysUserId();

	public void setSysUserId(String sysUserId);

	public String getDatasource() ;

	public void setDatasource(String datasource) ;

	public String getSysUserRef();

	public void setSysUserRef(String sysUserRef);
}
