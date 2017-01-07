package com.ut.comm.tool.string;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class StringUtil {
	public static String[] addStr(String str1, String str2) {
		String[] str = new String[2];
		str[0] = str1;
		str[1] = str2;
		return str;
	}

	public static String[] addStr(String[] str1, String str2) {
		if (str1 == null)
			return addStr(str2);

		String[] str = new String[str1.length + 1];
		for (int i = 0; i < str1.length; i++)
			str[i] = str1[i];
		str[str1.length] = str2;
		return str;
	}

	private static String[] addStr(String str) {
		String[] strA = new String[1];
		strA[0] = str;
		return strA;
	}

	/**
	 * to convert the src string to unicode and can be used in javascript but be
	 * sure the page code must be UTF-8 e.g. when using javascript to populate a
	 * field's value.
	 * 
	 * @param src
	 * @return
	 */
	public static String escapeJavaScript(String src) {
		return StringEscapeUtils.escapeJavaScript(src);
	}

	public static String escapeJava(String src) {
		return StringEscapeUtils.escapeJava(src);
	}

	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	public static boolean isNotEmpty(String s) {
		return s != null && s.length() > 0;
	}

	public static boolean isTrimEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static boolean isTrimNotEmpty(String s) {
		return s != null && s.trim().length() > 0;
	}

	public static String trim(String s) {
		return (s == null) ? null : s.trim();
	}

	public static String encode(String desc) {
		if (desc == null)
			return "";
		desc = desc.replaceAll(",", "\003");
		return desc;
	}

	public static String decode(String desc) {
		if (desc == null)
			return "";
		desc = desc.replaceAll("\003", ",");
		return desc;
	}
	 //首字母大写
    public static String firstUpName(String name) {
        return StringUtils.capitalize(name);
    }
    //首字母小写
    public static String firstLowerName(String name) {
        return StringUtils.uncapitalize(name);
    }
    
	public static boolean isNull(String str) {
		if(StringUtil.isTrimEmpty(str))
			return true;
		if("null".equalsIgnoreCase(str.trim())){
			return true;
		}
		return false;
	}
	public static boolean isNotNull(String str) {
		if(StringUtil.isTrimEmpty(str))
			return false;
		if("null".equalsIgnoreCase(str.trim())){
			return false;
		}
		return true;
	}
	
	public static String replaseWrap(String input){
		return input.replaceAll("[\\s*\t\n\r]", "");
	}

}
