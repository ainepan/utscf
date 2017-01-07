package com.ut.comm.tool.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.processors.PropertyNameProcessor;

public class JsonDateBeanProcessor implements PropertyNameProcessor {
	private String format = "yyyy-MM-dd HH:mm:ss";

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
//	@Override
//	public JSONObject processBean(Object arg0, JsonConfig arg1) {
////		if (value instanceof Date) {
////			String str = new SimpleDateFormat(format).format((Date) value);
////			return str;
////		}
////		return value == null ? null : value.toString();
//		System.out.println(arg0);
//		System.out.println(arg1);
//		return null;
//	}

	@Override
	public String processPropertyName(Class arg0, String arg1) {
//		// TODO Auto-generated method stub
//		return null;
		System.out.println(arg0);
		System.out.println(arg1);
		return null;
	}

	
}
