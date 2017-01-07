package com.ut.comm.tool;

import java.math.BigDecimal;
import java.security.Key;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ut.comm.tool.exception.TFSException;

public class BaseTypeHelper {
	
	private static BaseTypeHelper instance = new BaseTypeHelper();
	
	public static BaseTypeHelper getInstance(){
		return instance;
	}
	/**
	 * 将对象转化为整数类型，如无法转化则抛出异常
	 * @param value 需转化的对象
	 * @return
	 * @throws TFSException
	 */
	public int parseInt(Object value) throws TFSException{
		int output = 0;
		try {
			String v = value.toString();
			if (v != null && v.trim().length() > 0)
				output = Integer.valueOf(v);
			else
				return 0;
		} catch (NumberFormatException ex) {
			throw new TFSException("0x000000001", "不能将参数" + value + "转化为Integer类型",value);
		} catch (Exception ex) {
			throw new TFSException("0x000000001", ex.getMessage(),value);
		}
		return output;
	}
	
	/**
	 * 将对象转化为长整数类型，如无法转化则抛出异常
	 * @param value 需转化的对象
	 * @return
	 * @throws TFSException
	 */
	public long parseLong(Object value) throws TFSException{
		long output = 0;
		try {
			String v = value.toString();
			if (v != null && v.trim().length() > 0)
				output = Long.valueOf(v);
			else
				return 0;
		} catch (NumberFormatException ex) {
			throw new TFSException("0x000000001", "不能将参数" + value + "转化为Integer类型",value);
		} catch (Exception ex) {
			throw new TFSException("0x000000001", ex.getMessage(),value);
		}
		return output;
	}
	
	/**
	 * 将对象转化为日期类型，如无法转化则抛出异常
	 * @param value 需转化的对象
	 * @return
	 * @throws TFSException
	 */
	public Date parseDate(Object value) throws TFSException{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date outdate = null;
		try {
			String v = value.toString();
			if (v == null || v.trim().length() == 0)
				v = "1911-01-01";
			if(v.indexOf("/")!=-1)
			{
				v = v.replace("/", "-");
			}
			outdate = format.parse(v + " 00:00:00");
		} catch (ParseException ex) {
			throw new TFSException("0x000000002", "不能参数" + value + "转化为Date类型",value);
		} catch (Exception ex) {
			throw new TFSException("0x000000002", ex.getMessage(),value);
		}
		return outdate;
	}
	
	/**
	 * 将对象转化为日期类型，
	 * @param value 需转化的对象
	 * @return
	 * @throws TFSException
	 */
	public boolean parseStringToDate(Object value) {
		SimpleDateFormat formatter;
		SimpleDateFormat formatter2;
		if(value.toString().indexOf("/")!=-1)
		{
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
	/**
	 * 将对象转化为金额类型，如无法转化则抛出异常
	 * @param value 需转化的对象
	 * @return
	 * @throws TFSException
	 */
	public double parseDouble(Object value) throws TFSException {
		double output = 0d;
		try {
			String v = value.toString();
			if (v != null && v.trim().length() > 0)
				output = Double.valueOf(v);
			else
				return 0d;	
		} catch (NumberFormatException ex) {
			throw new TFSException("0x000000003", "不能将参数" + value + "转化为Double类型",value);
		} catch (Exception ex) {
			throw new TFSException("0x000000003", ex.getMessage(),value);
		}
		return output;
	}
	
	/**
	 * 将对象转化为字符串类型，如无法转化返回空字符串
	 * @param value 需转化的对象
	 * @return
	 * @throws Exception
	 */
	public String wipeStringOffNull(Object value) {
		if (value == null || "null".equalsIgnoreCase(value.toString())) {
			return "";
		}
		return value.toString().trim();
	}

	/**
	 * 将对象转化为金额类型，如无法转化返回0d
	 * @param value 需转化的对象
	 * @return
	 * @throws Exception
	 */
	public double wipeDobuleOffNull(Object value) {
		if (value == null || "".equals(value.toString())) {
			return 0d;
		}
		if (value.toString().length() > 1 && !(value.toString().substring(0,1)).matches("[0-9]*") && !"-".equals(value.toString().substring(0,1))) {
			value = value.toString().replaceAll(",", "");
			return Double.parseDouble(value.toString().substring(1));
		} 
		return Double.parseDouble(value.toString());	
	}
	

	/**
	 * 将对象转化为整数类型，如无法转化返回0
	 * @param value 需转化的对象
	 * @return
	 * @throws Exception
	 */
	public int wipeIntOffNull(Object value)  {
		if (value == null || "".equals(value.toString())) {
			return 0;
		}
		return Integer.parseInt(value.toString());
	}

	/**
	 * 将对象转化为长整数类型，如无法转化返回0
	 * @param value 需转化的对象
	 * @return
	 * @throws Exception
	 */
	public long wipeLongOffNull(Object value)  {
		if (value == null || "".equals(value.toString())) {
			return 0;
		}
		return Long.parseLong(value.toString());
	}	

	/**
	 * 将对象转化为日期类型，如无法转化返回空
	 * @param value 需转化的对象
	 * @return
	 * @throws Exception
	 */
	public Date wipeDateOffNull(Object value) throws Exception  {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (value != null && !"".equals(value.toString())) {
			if ("java.util.Date".equalsIgnoreCase(value.getClass().getName()) || "java.sql.Date".equalsIgnoreCase(value.getClass().getName()) ||  "java.sql.Timestamp".equalsIgnoreCase(value.getClass().getName()) || "com.sybase.jdbc3.tds.SybTimestamp".equalsIgnoreCase(value.getClass().getName())) {
				return (Date)value;
			}
			return formatter.parse(wipeStringOffNull(value));
		} else {
			return null;
		}
	} 
	
	/**
	 * 将对象转化为日期类型，如无法转化返回空
	 * @param value 需转化的对象
	 * @return
	 * @throws Exception
	 */
	public Date wipeTimeOffNull(Object value) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (value != null && !"".equals(value.toString())) {
			if ("java.util.Date".equalsIgnoreCase(value.getClass().getName()) || "java.sql.Date".equalsIgnoreCase(value.getClass().getName()) ||  "java.sql.Timestamp".equalsIgnoreCase(value.getClass().getName()) || "com.sybase.jdbc3.tds.SybTimestamp".equalsIgnoreCase(value.getClass().getName())) {
				return (Date)value;
			}
			return formatter.parse(wipeStringOffNull(value));
		} else {
			return null;
		}
	} 
	
	/**
	 * 金额计算的方法
	 * @param left 表达式左边元素
	 * @param operatorType 操作符类型
	 * @param right 表达式右边元素
	 * @return
	 * @throws Exception
	 */
	public double calculateDouble(Double left, OperatorType operatorType, Double right) {
		BigDecimal bdleft = new BigDecimal(wipeDobuleOffNull(left) + "");
		BigDecimal bdright = new BigDecimal(wipeDobuleOffNull(right) + "");
		switch(operatorType){
			default:return 0d;
			case add :return bdleft.add(bdright).doubleValue();
			case subtract:return bdleft.subtract(bdright).doubleValue();
			case multiply:return bdleft.multiply(bdright).doubleValue();
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

	/**
	 * 多金额连续计算的方法
	 * @param values 连续计算的数值
	 * @param type 操作符类型
	 * @return
	 * @throws Exception
	 */
	public double calculateDoubleArray(Object[] values,OperatorType type) {
		Object value = values[0];
		for (int i=1; i<values.length; i++) {
			value = calculateDouble((Double)value,type, (Double)values[i]);
		}
		return wipeDobuleOffNull(value);
	}
	
	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
	 */
	public byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	
	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	public Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}
	
	/**
	 * 四舍五入
	 * @param sacle 保留小数位数
	 * @param inputValue 数值
	 * @return
	 * @throws Exception
	 */
	public Double round(Long sacle,Double inputValue) throws Exception{
		if(inputValue == null) return null;
		BigDecimal tmp = new BigDecimal(inputValue).setScale(
				 this.wipeIntOffNull(sacle), BigDecimal.ROUND_HALF_UP);
		return tmp.doubleValue();
	}
	
	/**
	 * 计算日期
	 * @param inDate 初始日期
	 * @param day 相隔的天数，为＋：加day天，为-：减day天
	 * @return
	 * @throws Exception
	 */
	public Date caculateDate(Date inDate,Long day) throws Exception{
		Calendar star = Calendar.getInstance();
		star.setTime(inDate);
		star.add(Calendar.DATE, this.wipeIntOffNull(day) );
		return star.getTime();
	}
	
	/**
	 * 判断id是否存在数组中
	 * @param ids
	 * @param id
	 * @return
	 */
	public int indexOf(String[] ids, String id) {
		if (ids == null || id == null) {
			return -1;
		}
		
		for (int i = 0; i < ids.length; i ++) {
			if (id.equals(ids[i])) {
				return i;
			}
		}
		
		return -1;
	}
}
