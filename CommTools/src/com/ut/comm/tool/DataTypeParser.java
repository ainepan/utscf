package com.ut.comm.tool;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.security.Key;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ut.comm.tool.exception.SCFSException;
import com.ut.comm.tool.exception.TFSException;
import com.ut.comm.tool.string.StringUtil;

public class DataTypeParser {
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(DataTypeParser.class);

	private static String ZERO = ".000000000000000";
	private static DateFormat DATE_YYMMDD_FORMAT = new SimpleDateFormat(
			"yyyyMMdd");
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat DATETIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static DateFormat TIME_FORMAT = new SimpleDateFormat("HHmmss");
	public static String webInfPath = FileUtil.getRootPath();

	public static String formatDate(Date date) {
		return DATE_FORMAT.format(date);
	}

	public static String formatYYMMDDDate(Date date) {
		return DATE_YYMMDD_FORMAT.format(date);
	}

	public static String formatDateTime(Date date) {
		return DATETIME_FORMAT.format(date);
	}

	public static String formatTime(Date date) {
		return TIME_FORMAT.format(date);
	}

	public static int parseInt(Object value) throws SCFSException {
		int output = 0;
		try {
			String v = value.toString();
			if (v != null && v.trim().length() > 0)
				output = Integer.valueOf(v.replaceAll("[.][0-9]+$", ""));
			else
				return 0;
		} catch (NumberFormatException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new SCFSException("0x000000001", ex.getMessage(), value);
		}
		return output;
	}

	public static long parseLong(Object value) throws SCFSException {
		long output = 0;
		try {
			String v = value.toString();
			if (v != null && v.trim().length() > 0)
				output = Long.valueOf(v.replaceAll("[.][0-9]+$", ""));
			else
				return 0;
		} catch (NumberFormatException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new SCFSException("0x000000001", ex.getMessage(), value);
		}
		return output;
	}

	public static Date parseDate(Object value) throws SCFSException {
		if(!value.toString().contains("-")){
			value = "1912-01-01 "+value.toString();
		}
		return parseDate(value, "yyyy-MM-dd HH:mm:ss");
	}
	

	public static Date parseDate(Object value, String formatStr)
			throws SCFSException {
		DateFormat format = new SimpleDateFormat(formatStr);
		Date outdate = null;
		try {
			String v = value.toString();
			if ((v == null) || (v.trim().length() == 0)) {
				v = "1911-01-01";
			}
			v = v.replaceAll("T", " ");
			if (v.indexOf("/") != -1) {
				v = v.replaceAll("/", "-");
			}
			if (!v.endsWith(" 00:00:00")) {
				v = v + " 00:00:00";
			}
			outdate = format.parse(v);
		} catch (ParseException ex) {
			throw new SCFSException("","",null);
		} catch (Exception ex) {
			throw new SCFSException("0x000000002", ex.getMessage(),
					new Object[] { value });
		}
		return outdate;
	}

	public static boolean parseStringToDate(Object value) {
		SimpleDateFormat formatter;
		SimpleDateFormat formatter2;
		if (value.toString().indexOf("/") != -1) {
			value = value.toString().replace("/", "-");
		}
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter2 = new SimpleDateFormat("yyyy/MM/dd");
		try {
			formatter.parse(value.toString());
			return true;
		} catch (Exception ex) {
			try {
				formatter2.parse(value.toString());
				return true;
			} catch (ParseException e) {
				return false;
			}
		}
	}

	public static double parseDouble(Object value) throws SCFSException {
		double output = 0d;
		try {
			String v = value.toString();
			if (v != null && v.trim().length() > 0)
				output = Double.valueOf(v);
			else
				return 0d;
		} catch (NumberFormatException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new SCFSException("0x000000003", ex.getMessage(), value);
		}
		return output;
	}

	public static String wipeStringOffNull(Object value) {
		if (value == null || "null".equalsIgnoreCase(value.toString())) {
			return "";
		}
		return value.toString().trim();
	}

	public static double wipeDobuleOffNull(Object value) throws SCFSException {
		if (value == null || "".equals(value.toString())) {
			return 0d;
		}
		if (value.toString().length() > 1
				&& !(value.toString().substring(0, 1)).matches("[0-9]*")
				&& !"-".equals(value.toString().substring(0, 1))) {
			value = value.toString().replaceAll(",", "");
			return Double.parseDouble(value.toString().substring(1));
		}
		return Double.parseDouble(value.toString());
	}

	public static int wipeIntOffNull(Object value) throws SCFSException {
		if (value == null || "".equals(value.toString())) {
			return 0;
		}
		return Integer.parseInt(value.toString());
	}

	public static long wipeLongOffNull(Object value) throws SCFSException {
		if (value == null || "".equals(value.toString())) {
			return 0;
		}
		return Long.parseLong(value.toString());
	}

	public static Date wipeDateOffNull(Object value) throws SCFSException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (value != null && !"".equals(value.toString())) {
			if ("java.util.Date".equalsIgnoreCase(value.getClass().getName())
					|| "java.sql.Date".equalsIgnoreCase(value.getClass()
							.getName())
					|| "java.sql.Timestamp".equalsIgnoreCase(value.getClass()
							.getName())
					|| "com.sybase.jdbc3.tds.SybTimestamp"
							.equalsIgnoreCase(value.getClass().getName())) {
				return (Date) value;
			}
			try {
				return formatter.parse(wipeStringOffNull(value));
			} catch (ParseException ex) {
				throw new SCFSException("0x000000003", ex.getMessage(), value);
			}
		} else {
			return null;
		}
	}

	public static Date wipeTimeOffNull(Object value) throws SCFSException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (value != null && !"".equals(value.toString())) {
			if ("java.util.Date".equalsIgnoreCase(value.getClass().getName())
					|| "java.sql.Date".equalsIgnoreCase(value.getClass()
							.getName())
					|| "java.sql.Timestamp".equalsIgnoreCase(value.getClass()
							.getName())
					|| "com.sybase.jdbc3.tds.SybTimestamp"
							.equalsIgnoreCase(value.getClass().getName())) {
				return (Date) value;
			}
			try {
				return formatter.parse(wipeStringOffNull(value));
			} catch (ParseException ex) {
				throw new SCFSException("0x000000003", ex.getMessage(), value);
			}
		} else {
			return null;
		}
	}

	public static double calculateDouble(Object left, OperatorType type,
			Object right) throws SCFSException {
		BigDecimal bdleft = new BigDecimal(wipeDobuleOffNull(left) + "");
		BigDecimal bdright = new BigDecimal(wipeDobuleOffNull(right) + "");
		switch (type) {
		default:
			return 0d;
		case add:
			return bdleft.add(bdright).doubleValue();
		case subtract:
			return bdleft.subtract(bdright).doubleValue();
		case multiply:
			return bdleft.multiply(bdright).doubleValue();
		case divide:
			try {
				return bdleft.divide(bdright).doubleValue();
			} catch (Exception e) {
				bdleft = bdleft.divide(bdright, 15, BigDecimal.ROUND_DOWN);
				double value = bdleft.doubleValue();
				return value;
			}
		}
	}

	public static double calculateDoubleArray(Object[] values, OperatorType type)
			throws SCFSException {
		Object value = values[0];
		for (int i = 1; i < values.length; i++) {
			value = calculateDouble(value, type, values[i]);
		}
		return wipeDobuleOffNull(value);
	}

	public static String byteArr2HexStr(byte[] arrB) throws SCFSException {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static byte[] hexStr2ByteArr(String strIn) throws SCFSException {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public static Key getKey(byte[] arrBTmp) throws SCFSException {
		byte[] arrB = new byte[8];
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}

	public static Double round(Long sacle, Double inputValue)
			throws SCFSException {
		BigDecimal tmp = new BigDecimal(inputValue).setScale(
				wipeIntOffNull(sacle), BigDecimal.ROUND_HALF_UP);
		return tmp.doubleValue();
	}

	public static Date caculateDate(Date inDate, Long day) throws SCFSException {
		Calendar star = Calendar.getInstance();
		star.setTime(inDate);
		star.add(Calendar.DATE, wipeIntOffNull(day));
		return star.getTime();
	}

	public static int indexOf(String[] ids, String id) throws SCFSException {
		if (ids == null || id == null) {
			return -1;
		}

		for (int i = 0; i < ids.length; i++) {
			if (id.equals(ids[i])) {
				return i;
			}
		}
		return -1;
	}

	public static String getAbsolutePath(String ralativePath) {
		String path = DataTypeParser.class.getClassLoader().getResource(ralativePath)
				.toString();
		return path.substring(6);
	}

	public static File getClasspath() {
		return new File(getAbsolutePath(""));
	}

	public static String getClasspathParent() {
		return new File(getAbsolutePath("")).getParent();
	}

	public static InputStream getInputStream(String ralativePath)
			throws FileNotFoundException {
		return new FileInputStream(new File(getAbsolutePath(ralativePath)));
	}

	public static void release(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				throw new IllegalStateException("realse the resource["
						+ resource + "] failed!");
			}
			resource = null;
		}
	}

	public synchronized static String generateID() {
		long curentTime = System.currentTimeMillis();
		StringBuilder ID = new StringBuilder("" + (curentTime)
				+ Math.round(Math.random() * 10000000));
		if (ID.length() < 20) {
			for (int i = 0; i <= 20 - ID.length(); i++) {
				ID.append("0");
			}
		}
		return ID.toString();
	}

	public static Object blobToObject(java.sql.Blob desblob) throws Exception {

		if (desblob == null)
			return null;
		ObjectInputStream in = null;
		Object obj = null;
		try {
			long l1 = System.currentTimeMillis();
			in = new ObjectInputStream(desblob.getBinaryStream());
			obj = in.readObject();
			long l2 = System.currentTimeMillis();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
		}
		return obj;
	}

	public static Double times(Double d1, Double d2) {
		return BaseTypeHelper.getInstance().calculateDouble(d1,
				OperatorType.multiply, d2);
	}

	public static Double multiply(Double d1, Double d2) {
		return BaseTypeHelper.getInstance().calculateDouble(d1,
				OperatorType.multiply, d2);
	}

	public static Double add(Double d1, Double d2) {
		return BaseTypeHelper.getInstance().calculateDouble(d1,
				OperatorType.add, d2);
	}

	public static Double add(Long sacle, Double d1, Double d2) throws SCFSException {
		return DataTypeParser.round(sacle, BaseTypeHelper.getInstance().calculateDouble(
				d1, OperatorType.add, d2));
	}

	public static Double add(Double... values) {
		Double sum = 0D;
		for (Double value : values) {
			sum = BaseTypeHelper.getInstance().calculateDouble(sum,
					OperatorType.add, value);
		}
		return sum;
	}

	public static Double add(Long sacle, Double... values) throws SCFSException {
		Double sum = 0D;
		for (Double value : values) {
			sum = BaseTypeHelper.getInstance().calculateDouble(sum,
					OperatorType.add, value);
		}
		return DataTypeParser.round(sacle, sum);
	}

	public static Double subtract(Double d1, Double d2) {
		return BaseTypeHelper.getInstance().calculateDouble(d1,
				OperatorType.subtract, d2);
	}

	public static Double subtract(Long sacle, Double d1, Double d2) throws SCFSException {
		return DataTypeParser.round(sacle, BaseTypeHelper.getInstance().calculateDouble(
				d1, OperatorType.subtract, d2));
	}

	public static Double divide(Double d1, Double d2) {
		return BaseTypeHelper.getInstance().calculateDouble(d1,
				OperatorType.divide, d2);
	}

	public static boolean isZero(Double value) {
		return Math.abs(value) < 0.00000001;
	}

	public static long daysBetween(Date startDay, Date endDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDay);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endDay);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return between_days;
	}

	public static Double toDouble(Object value) {
		return BaseTypeHelper.getInstance().wipeDobuleOffNull(value);
	}

	public static String toString(Object value) {
		return BaseTypeHelper.getInstance().wipeStringOffNull(value);
	}

	public static Integer toInteger(Object value) {
		return BaseTypeHelper.getInstance().wipeIntOffNull(value);
	}

	public static Double toMoney(Object value) {
		if (value != null) {
			value = value.toString().replaceAll("[^-.0-9]", "");
		}
		return BaseTypeHelper.getInstance().wipeDobuleOffNull(value);
	}

	public static String formatDouble(double d, int len) {
		String suffix = "";
		if (len > 0) {
			suffix = ZERO.substring(0, len + 1);
		}
		DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0" + suffix);

		String outStr = null;

		try {
			outStr = fmt.format(d);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			outStr = String.valueOf(d);
		}
		return outStr;
	}

	public static String autoFormatDouble(double d) {
		return formatDouble(d, ZERO.length() - 1).replaceAll("[.]0+$", "");
	}
	public static String double2str(double d) {

		DecimalFormat fmt = new DecimalFormat("################0.00000000");

		String outStr = null;

		try {
			outStr = fmt.format(d);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			outStr = String.valueOf(d);
		}
		if (outStr.indexOf(".") > 0) {
			outStr = outStr.replaceAll("0+?$", "");// ȥ�������0
			outStr = outStr.replaceAll("[.]$", "");// �����һλ��.��ȥ��
		}
		return outStr;
	}

	@SuppressWarnings("unchecked")
	public static List putList(Object... objs) {
		List list = new ArrayList();
		for (Object o : objs) {
			if (o == null) {
				continue;
			}
			if (o instanceof Collection) {
				list.addAll((Collection) o);
			} else {
				list.add(o);
			}
		}
		return list;
	}

	public static void sort(List list, final String sortBy) {

		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				return compareObject(o1, o2, sortBy);
			}
		});
	}

	private static int compareObject(Object o1, Object o2, String compareRule) {

		String[] rule = compareRule.trim().split("[,]", -1);
		String[] field = rule[0].trim().split(" ", -1);
		final String column = field[0].trim();
		final String dir = field.length > 1 ? field[1].trim() : "asc";
		boolean isASC = !"desc".equalsIgnoreCase(dir);
		compareRule = compareRule.indexOf(",") > 0 ? compareRule
				.substring(compareRule.indexOf(",") + 1) : null;

		Object v1 = BeanUtils.setBean(o1).get(column);
		Object v2 = BeanUtils.setBean(o2).get(column);

		if (v1 == null && v1 == null) {
			return compareRule != null ? compareObject(o1, o2, compareRule) : 0;
		} else if (v1 == null) {
			return isASC ? -1 : 1;
		} else if (v2 == null) {
			return isASC ? 1 : -1;
		} else {
			try {
				Object o = BeanUtils.invokeMethod(v1, "compareTo",
						new Object[] { v2 });
				int result = ((Integer) o).intValue();
				if (result == 0) {
					// System.out.println(v1 + "-" + v2 + " : " + compareRule);
					return compareRule != null ? compareObject(o1, o2,
							compareRule) : 0;
				} else if (result > 0) {
					// System.out.println(v1 + " > " + v2 + " : " + column + " "
					// + dir );
					return isASC ? 1 : -1;
				} else {
					// System.out.println(v1 + " < " + v2 + " : " + column + " "
					// + dir );
					return isASC ? -1 : 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}
}
