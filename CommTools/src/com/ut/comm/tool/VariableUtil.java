package com.ut.comm.tool;

public class VariableUtil {
	public static final int BEFORE_HEAD = 0;
	public static final int AFTER_TAIL = 1;
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static byte[] intTobyteArray(int value, int size) {
		byte[] result = new byte[size];
		for (int i = 0; i < size; i++) {
			int offset = (result.length - 1 - i) * 8;
			result[i] = (byte) ((value >>> offset) & 0xFF);
		}
		return result;
	}

	public static int byteArrayToInt(byte[] bytes) throws Exception {
		return VariableUtil.StringToInt(new String(bytes, "UTF-8").trim());
	}

	public static Short StringToShort(String s) {
		if (s == null || ("".equals(s) | s.toUpperCase().equals("NULL"))) {
			return 0;
		}

		try {
			return Short.valueOf(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static int StringToInt(String s) {
		if (s == null || ("".equals(s) | s.toUpperCase().equals("NULL"))) {
			return 0;
		}
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static Short IntegerToShort(int i) {
		try {
			return Short.valueOf(String.valueOf(i));
		} catch (Exception e) {
			return 0;
		}
	}

	public static int LongToInt(long i) {
		try {
			return Integer.valueOf("" + i);
		} catch (Exception e) {
			return 0;
		}
	}

	public static long IntToLong(int i) {
		if (i == 0)
			return 0;
		try {
			return Long.valueOf(i);
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean StringToBoolean(String str) {
		return ((str != null) && str.equalsIgnoreCase("true"));
	}

	public static long StringToLong(String s) {
		if (null == s) {
			return 0;
		}
		s = s.trim();
		if (s == null || (s.equals("") | s.toUpperCase().equals("NULL"))) {
			return 0;
		}
		try {
			return Long.valueOf(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static float StringToFloat(String s) {
		if (s == null || (s.equals("") | s.toUpperCase().equals("NULL"))) {
			return 0;
		}

		try {
			return Float.parseFloat(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static double StringToDouble(String s) {
		if (s == null || (s.equals("") | s.toUpperCase().equals("NULL"))) {
			return 0;
		}

		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @param source
	 *            目标字符串
	 * @param size
	 *            目标大小
	 * @param c
	 *            不够需要填补的字符
	 */
	public static String fill(String source, int size, char c) {
		return fill(source, size, c, AFTER_TAIL);
	}

	/**
	 * @param source
	 *            目标字符串
	 * @param size
	 *            目标大小
	 * @param c
	 *            不够需要填补的字符
	 * @param type
	 *            AFTER_TAIL:在字符串尾部填补字符,BEFORE_HEAD：在字符串头部填补字符
	 */
	public static String fill(String source, int size, char c, int type) {
		int length = source == null ? 0 : source.length();
		if (length >= size)
			return source;
		if (AFTER_TAIL == type)
			return source + getChars(size - length, c);
		if (BEFORE_HEAD == type)
			return getChars(size - length, c) + source;
		return source;
	}

	/**
	 * 获取由一定相同字符组成的字符串
	 */
	public static String getChars(int num, char c) {
		if (num < 1)
			return null;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; i++) {
			sb.append(c);
		}
		return sb.toString();
	}

	public static String encrpt(String s) throws Exception {
		return encrpt(s, "UTF-8");
	}

	public static String encrpt(String s, String charaset) throws Exception {
		byte[] b = s.getBytes(charaset);
		// b = AESEncrpt.newInstance().encrypt(b);
		return getHexString(b);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(encrpt("123456")); 
	}

	public static String decrpt(String s) throws Exception {
		return decrpt(s, "UTF-8");
	}

	public static String decrpt(String s, String charaset) throws Exception {
		s = s.trim();
		byte[] b = hexStringToByteArray(s);
		// b = AESEncrpt.newInstance().decrypt(b);
		return new String(b, charaset);
	}

	public static String getHexString(byte[] b) {
		int l = b.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & b[i]) >>> 4];
			out[j++] = DIGITS[0x0F & b[i]];
		}
		return new String(out);
	}

	public static byte[] hexStringToByteArray(String s) {
		char[] data = s.toCharArray();
		int len = data.length;
		if ((len & 0x01) != 0) {
			throw new IllegalArgumentException("Odd number of characters.");
		}
		byte[] out = new byte[len >> 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}
		return out;
	}

	private static int toDigit(char ch, int index)
			throws IllegalArgumentException {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new IllegalArgumentException("Illegal hexadecimal charcter "
					+ ch + " at index " + index);
		}
		return digit;
	}

}
