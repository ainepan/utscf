package com.ut.comm.tool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.ut.comm.tool.string.StringUtil;

public class EncryptUtil {
	public static String encryptString(String input) {
		if (StringUtil.isTrimEmpty(input)) {
			return input;
		}
		byte[] inputByte = input.getBytes();
		return new sun.misc.BASE64Encoder().encodeBuffer(inputByte);
	}
	
	public static String encryptString(String input,boolean noWrap ) {
		String encode = encryptString(input);
		if(noWrap){
			return StringUtil.replaseWrap(encode);
		}
		return encode;
	}
	
	public static String decryptString(String input) throws IOException {
		if (StringUtil.isTrimEmpty(input)) {
			return input;
		}
		byte[] inputByte = new sun.misc.BASE64Decoder().decodeBuffer(input);
		return new String(inputByte);
	}
	
	public static String encryptString(byte[] input) {
		return new sun.misc.BASE64Encoder().encodeBuffer(input);
	}
	
	public static String encryptString(byte[] input,boolean noWrap ) {
		String encode = encryptString(input);
		if(noWrap){
			return encode.replaceAll("[\\s*\t\n\r]", "");
		}
		return encode;
	}

	public static byte[] decryptByte(String input) throws IOException {
		if (StringUtil.isTrimEmpty(input)) {
			return null;
		}
		byte[] inputByte = new sun.misc.BASE64Decoder().decodeBuffer(input);
		return inputByte;
	}

	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F" };

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	// 返回形式只为数字
	private static String byteToNum(byte bByte) {
		int iRet = bByte;
		System.out.println("iRet1=" + iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		return String.valueOf(iRet);
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	public static String getMD5Code(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		resultString =resultString.toLowerCase();
		return resultString;
	}

	
	public static String getURLDecodeString(String url) throws UnsupportedEncodingException{
		return getURLDecodeString(url,"UTF-8");
	}
	
	public static String getURLDecodeString(String url,String charset) throws UnsupportedEncodingException{
		return URLDecoder.decode(url,charset);
	}
	
	public static String getURLEncodeString(String url) throws UnsupportedEncodingException{
		return URLEncoder.encode(url,"UTF-8");
	}
	
	public static String getURLEncodeString(String url,String charset) throws UnsupportedEncodingException{
		return URLEncoder.encode(url,charset);
	}
	
	
}
