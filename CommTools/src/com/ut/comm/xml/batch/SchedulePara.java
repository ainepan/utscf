package com.ut.comm.xml.batch;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class SchedulePara extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7611788679392109710L;

	private String scheduletype;
	private String checkholiday;
	private String beginfrom;
	private String terminativedate;
	private String hour;
	private String minute;
	private String second;
	private String condition;
	private String week;
	private String date;
	private String month;
	private String express;
	private String isbybu;
	private String initialize;

	public String getScheduletype() {
		return scheduletype;
	}
	public void setScheduletype(String scheduletype) {
		this.scheduletype = scheduletype;
	}
	public String getCheckholiday() {
		return checkholiday;
	}
	public void setCheckholiday(String checkholiday) {
		this.checkholiday = checkholiday;
	}
	public String getBeginfrom() {
		return beginfrom;
	}
	public void setBeginfrom(String beginfrom) {
		this.beginfrom = beginfrom;
	}
	public String getTerminativedate() {
		return terminativedate;
	}
	public void setTerminativedate(String terminativedate) {
		this.terminativedate = terminativedate;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getIsbybu() {
		return isbybu;
	}
	public void setIsbybu(String isbybu) {
		this.isbybu = isbybu;
	}
	/**
	 * S: start/schedule E: End
	 * @return
	 */
	public String getInitialize() {
		return initialize;
	}
	public void setInitialize(String initialize) {
		this.initialize = initialize;
	}
	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_SCHEDULE;
	}
}
