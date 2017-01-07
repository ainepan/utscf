package com.ut.comm.tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	/**
	 * 正常返回MD5字串
	 */
	public static final int NORMAL_CASE = 0;
	/**
	 * 以大写形式返回MD5字串
	 */
	public static final int UPPER_CASE = 1;
	/**
	 * 以小写形式返回MD5字串
	 */
	public static final int LOWER_CASE = 2;

	public static final String UTF8 = "UTF-8";
	public static final String UTF16 = "UTF-16";
	public static final String GBK = "GBK";
	public static final String GB2312 = "GB2312";
	public static final String ISO_8859_1 = "ISO-8859-1";

	/**
	 * 将传入的报文体进行md5加密后和传入的md5比对验证
	 * 
	 * @param md5 报文信息中提交的md5值
	 * @param messageBody 待驗證的報文體
	 * @return 验证结果
	 */
	public static boolean validate(String md5, byte[] messageBody, int start, int excursion) throws NoSuchAlgorithmException {
		if (md5 == null) {
			return false;
		}
		String md5_new = makeMd5(messageBody);
		return md5.equalsIgnoreCase(md5_new.substring(start, start + excursion));
	}
	
	public static boolean validate(String md5, byte[] messageBody) throws NoSuchAlgorithmException {
		return validate(md5, messageBody, 0, 32);
	}

	/**
	 * 根据str生成md5值进行校验
	 * 
	 * @param str 待生成md5值字符串
	 * @return md5值
	 */
	public static String makeMd5(byte[] bytes) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(bytes);
//		return getMD5String(messageDigest.digest());
		return VariableUtil.getHexString(messageDigest.digest());
	}

	/**
	 * get md5
	 * 
	 * @param str 为待生成md5值字符串
	 * @param charset 编码
	 * @return
	 */
	public static String makeMd5(String str, String charset) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return makeMd5(str.getBytes(charset));
	}

	/**
	 * @param bytes 源字节数组
	 * @param resultType 返回MD5字串大小类型
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String makeMd5(byte[] bytes, int resultType) throws NoSuchAlgorithmException {
		return to_case(makeMd5(bytes), resultType);
	}
	
	/**
	 * @param str 源字符串
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String makeMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return makeMd5(str, UTF8);
	}

	/**
	 * @param str 源字符串
	 * @param charset 字符编码
	 * @param resultType 返回字符大小写类型
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String makeMd5(String str, String charset, int resultType) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return to_case(makeMd5(str, charset), resultType);
	}

	/**
	 * @param str 源字符串
	 * @param resultType 返回字符大小写类型
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String makeMd5(String str, int resultType) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return to_case(makeMd5(str, UTF8), resultType);
	}

//	private static String getMD5String(byte[] b) {
//		int i;
//		StringBuffer buf = new StringBuffer("");
//		for (int offset = 0; offset < b.length; offset++) {
//			/*
//			 * i = b[offset]; if (i < 0) i += 256;
//			 */
//			i = b[offset] & 0xFF;
//			if (i < 16)
//				buf.append("0");
//			buf.append(Integer.toHexString(i));
//		}
//		return buf.toString();
//	}

	private static String to_case(String md5, int type) {
		switch (type) {
		case NORMAL_CASE:
			return md5;
		case UPPER_CASE:
			return md5.toUpperCase();
		case LOWER_CASE:
			return md5.toLowerCase();
		default:
			return md5;
		}
	}
	
}