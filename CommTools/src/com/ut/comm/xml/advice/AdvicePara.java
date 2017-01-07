package com.ut.comm.xml.advice;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class AdvicePara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_ADVICE;
	}

	private String name;
	private String desc;
	private String type;
	private String js;
	private String sendid;
	private String senddt;
	private String invaliddt;
	private String expdt; // 有效期

	public String getExpdt() {
		return expdt;
	}

	public void setExpdt(String expdt) {
		this.expdt = expdt;
	}

	/**
	 * 1 通知 2 紧急通知 3 警告
	 */
	private String remindtp;

	/**
	 * 详细推送方式： 0 ： 站内信（桌面提醒）默认方式 1 : Mail 2 : 短信 3：APP推送
	 */
	private String sendtp;

	/**
	 * 消息目标（接收者）分组： 1 点对点消息 2 点对多消息
	 */
	private String grouptp;
	private String title;
	private String content;
	private String receiveid;
	private String byBu;
	private String byOrg;
	private String byCust;
	private String receiveBu;
	private String receiveOrg;
	private String receiveCust;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public String getSendid() {
		return sendid;
	}

	public void setSendid(String sendid) {
		this.sendid = sendid;
	}

	public String getSenddt() {
		return senddt;
	}

	public void setSenddt(String senddt) {
		this.senddt = senddt;
	}

	public String getInvaliddt() {
		return invaliddt;
	}

	public void setInvaliddt(String invaliddt) {
		this.invaliddt = invaliddt;
	}

	/**
	 * 1 通知 2 紧急通知 3 警告
	 * 
	 * @return
	 */
	public String getRemindtp() {
		return remindtp;
	}

	public void setRemindtp(String remindtp) {
		this.remindtp = remindtp;
	}

	/**
	 * 详细推送方式： 0 ： 站内信（桌面提醒）默认方式 1 : Mail 2 : 短信 3：APP推送
	 * 
	 * @return
	 */
	public String getSendtp() {
		return sendtp;
	}

	public void setSendtp(String sendtp) {
		this.sendtp = sendtp;
	}

	/**
	 * 消息目标（接收者）分组： 1 点对点消息 2 点对多消息
	 * 
	 * @return
	 */
	public String getGrouptp() {
		return grouptp;
	}

	public void setGrouptp(String grouptp) {
		this.grouptp = grouptp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 一般由js动态赋值
	 * 
	 * @return
	 */
	public String getReceiveid() {
		return receiveid;
	}

	public void setReceiveid(String receiveid) {
		this.receiveid = receiveid;
	}

	public String getByBu() {
		return byBu;
	}

	public void setByBu(String byBu) {
		this.byBu = byBu;
	}

	public String getByOrg() {
		return byOrg;
	}

	public void setByOrg(String byOrg) {
		this.byOrg = byOrg;
	}

	public String getByCust() {
		return byCust;
	}

	public void setByCust(String byCust) {
		this.byCust = byCust;
	}

	public String getReceiveBu() {
		return receiveBu;
	}

	public void setReceiveBu(String receiveBu) {
		this.receiveBu = receiveBu;
	}

	public String getReceiveOrg() {
		return receiveOrg;
	}

	public void setReceiveOrg(String receiveOrg) {
		this.receiveOrg = receiveOrg;
	}

	public String getReceiveCust() {
		return receiveCust;
	}

	public void setReceiveCust(String receiveCust) {
		this.receiveCust = receiveCust;
	}

}
