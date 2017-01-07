package com.ut.comm.tool;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import oracle.sql.DATE;
import oracle.sql.TIMESTAMP;

public class DateTimeUtil {

	public static String getSysTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String str = df.format(now);
		return str;
	}

	public static Timestamp getTimestamp() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return now;
	}

	public static String getSysDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String str = df.format(now);
		return str;
	}

	public static String getSysDateTime(String format) {
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat(format);
		String str = df.format(now);
		return str;
	}

	public static Date getSysDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date now = null;
		try {
			now = df.parse(getSysDateTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return now;
	}

	public static Timestamp getDateTime(String str) throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = df.parse(str);
			Timestamp time = new Timestamp(now.getTime());
			return time;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static Timestamp getOracleDateTime(Object oracleObj) throws Exception {
		try {
			Class clazz = oracleObj.getClass();
			if(oracle.sql.DATE.class.isAssignableFrom(clazz)){
				oracle.sql.DATE reqDate = (DATE) oracleObj;
				Timestamp time =  reqDate.timestampValue();
				return time;
			}else if(oracle.sql.TIMESTAMP.class.isAssignableFrom(clazz)){
				oracle.sql.TIMESTAMP reqTime = (TIMESTAMP) oracleObj;
				Timestamp time = reqTime.timestampValue();
				return time;
			}else{
				throw new Exception("Unparseable oracle date type");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public static Date getDate(String str) throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date now = df.parse(str);
			return now;
		} catch (Exception e) {
			throw e;
		}
	}

	public static Date getDate(String str, String format) throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			Date now = df.parse(str);
			return now;
		} catch (Exception e) {
			throw e;
		}
	}

	public static String getSysTime(int timedifference) {
		Calendar cal = getCalendarByGMT();
		cal.add(Calendar.HOUR_OF_DAY, timedifference);
		String ac = "00";

		String hour = ac + String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		hour = hour.substring(hour.length() - 2);

		String minute = ac + String.valueOf(cal.get(Calendar.MINUTE));
		minute = minute.substring(minute.length() - 2);

		String seconds = ac + String.valueOf(cal.get(Calendar.SECOND));
		seconds = seconds.substring(seconds.length() - 2);

		return hour + ":" + minute + ":" + seconds;
	}

	public static String getDateTime() {
		return getDateTime(0);
	}

	public static String getDateTime(int timeDiff) {
		Calendar cal = getCalendarByGMT();
		int nHour = cal.get(Calendar.HOUR_OF_DAY);
		cal.set(Calendar.HOUR_OF_DAY, timeDiff + nHour);
		String ac = "0000";
		String year = ac + String.valueOf(cal.get(Calendar.YEAR));
		year = year.substring(year.length() - 2);

		String month = ac + String.valueOf(cal.get(Calendar.MONTH) + 1);
		month = month.substring(month.length() - 2);
		String day = ac + String.valueOf(cal.get(Calendar.DATE));
		day = day.substring(day.length() - 2);
		String date = year + month + day;

		String hour = ac + String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		hour = hour.substring(hour.length() - 2);

		String minute = ac + String.valueOf(cal.get(Calendar.MINUTE));
		minute = minute.substring(minute.length() - 2);

		String seconds = ac + String.valueOf(cal.get(Calendar.SECOND));
		seconds = seconds.substring(seconds.length() - 2);
		String milSeconds = ac + String.valueOf(cal.get(Calendar.MILLISECOND));
		milSeconds = milSeconds.substring(milSeconds.length() - 3);
		return date + " " + hour + ":" + minute + ":" + seconds + "."
				+ milSeconds;

	}

	public static Calendar getCalendarByGMT() {
		Calendar cal = null;
		// String svrTimeFmt =
		// SystemParameterLoader.getSecSysPara("SVR_TIME_FORMAT");
		String svrTimeFmt = "";
		if ("GMT".equals(svrTimeFmt)) {
			TimeZone timeZone = TimeZone.getTimeZone("GMT");
			cal = Calendar.getInstance(timeZone);
		} else {
			cal = Calendar.getInstance();
		}
		return cal;
	}

	public static Date dateAddDays(Date sDate, int days) {
		if (sDate == null)
			return sDate;
		long addtime = (long) days * 24 * 60 * 60 * 1000;
		java.sql.Date newdate = new java.sql.Date(addtime + sDate.getTime());
		return newdate;
	}

	public static Date dateMulDays(Date sDate, int days) {
		if (sDate == null)
			return sDate;
		long Multime = (long) days * 24 * 60 * 60 * 1000;
		java.sql.Date newdate = new java.sql.Date(sDate.getTime() - Multime);
		return newdate;
	}

	public static String parseDate(Date date) {
		return parseDate(date,"yyyy-MM-dd");
	}
	
	public static String parseDate(Date date,String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String str = df.format(date);
		return str;
	}

	// 计算两个日期之间的差值例如"1.1"到"1.5"算四天
	public static int getDays(Date fromDate, Date toDate) {
		if (fromDate == null)
			return 0;
		if (toDate == null)
			return 0;
		long dateTimes = (long) (toDate.getTime() - fromDate.getTime());
		int days = new Long(dateTimes / 1000 / 60 / 60 / 24).intValue();
		return days;
	}
	
	public static String dateAddYear(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, year);
		Date date = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String str = df.format(date);
		return str;
	}
	
	public static boolean isBefore(Date date1,Date date2){
		return date1.before(date2);
	}
	
	public static boolean isBeforeToday(Date date1){
		return date1.before(getSysDate());
	}
}
