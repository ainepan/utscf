package com.ut.comm.tool;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.w3c.dom.Element;

import com.ut.comm.tool.exception.SCFSException;
import com.ut.comm.tool.json.JsonUtil;
import com.ut.comm.tool.string.StringUtil;
import com.ut.comm.tool.xml.XMLManager;

public class BeanUtils {

	private static final Log LOG = LogFactory.getLog(BeanUtils.class);
	private static ThreadLocal<BeanMap> BEAN_MAP = new ThreadLocal<BeanMap>();

	public static boolean isDateType(Class clazz) {
//		return clazz != null ? clazz.getName().equals("java.util.Date") : false;
		return clazz != null ?Date.class.isAssignableFrom(clazz):false;
	}
	
	public static boolean isOracleDateType(Class clazz) {
//		return clazz != null ? clazz.getName().equals("java.util.Date") : false;
		return clazz != null ?oracle.sql.Datum.class.isAssignableFrom(clazz):false;
	}
	
	public static boolean isBigDecimalType(Class clazz){
		return clazz != null ?BigDecimal.class.isAssignableFrom(clazz):false;
	}

	public static boolean isBaseDataType(Class clazz) {
		return clazz != null ? (clazz.isPrimitive() || clazz.getName()
				.startsWith("java.lang")) : false;
	}

	public static boolean isArray(Class clazz) {
		return clazz != null ? clazz.isArray() : false;
	}

	public static boolean isCollection(Class clazz) {
		return clazz != null ? java.util.Collection.class
				.isAssignableFrom(clazz) : false;
	}

	// public static Class<?> getEntityClassByName(Tpfentyset entity) throws
	// Exception{
	// Class<?> clazz = null;
	// if (ConstantValues.isDevelopmentEvn) {
	// clazz = Class.forName(entity.getClassname());
	// } else {
	// clazz = (Class<?>)(new
	// LogicClassLoader().loadClass(entity.getClassname(),
	// entity.getContent()));
	// }
	// return clazz;
	// }

	public static Object invokeMethod(Object methodObject, String methodName,
			Object[] args) throws Exception {
		Class<?>[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		return invokeMethod(methodObject, methodName, args, argsClass);
	}

	public static Object invokeMethod(Object methodObject, String methodName,
			Object[] args, Class[] argsClass) throws Exception {
		Class<?> ownerClass = methodObject.getClass();
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(methodObject, args);
	}

	public static Object invokeStaticMethod(Class<?> clazz, String methodName,
			Object[] args, Class<?>[] argsClass) throws Exception {
		Constructor con = clazz.getDeclaredConstructor();
		boolean conAccess = con.isAccessible();
		con.setAccessible(true);
		Object instence = con.newInstance();

		Method method = clazz.getMethod(methodName.trim(), argsClass);
		boolean accessible = method.isAccessible();
		method.setAccessible(true);

		Object result = null;
		try {
			result = method.invoke(instence, args);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		con.setAccessible(conAccess);
		method.setAccessible(accessible);
		return result;
	}
	
	public static boolean isBaseJavaType(String strType){
		if(StringUtil.isTrimEmpty(strType))
			return false;
		try{
			Class clazz = Class.forName(strType);
			return true;
		}catch(Exception e){
			LOG.error(e);
			return false;
		}
	}
	
	public static Object getBaseJavaObj(String strValue, String strType) {
		try{
			Class clazz = Class.forName(strType);
			if(BeanUtils.isDateType(clazz)){
				if(Timestamp.class.isAssignableFrom(clazz)){
					return  DateTimeUtil.getDateTime(strValue);
				}else{
					return  DateTimeUtil.getDate(strValue);
				}
			}else if(Integer.class.isAssignableFrom(clazz)){
				return  Integer.valueOf(strValue);
			}else if(BigDecimal.class.isAssignableFrom(clazz)){
				return  new BigDecimal(strValue);
			}
			return null;
		}catch(Exception e){
			LOG.error(e);
			return null;
		}
	}

	public static Object invokeStaticMethod(Class<?> clazz, String methodName,
			Object[] args) throws Exception {
		Class<?>[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}

		return invokeStaticMethod(clazz, methodName, args, argsClass);
	}

	@SuppressWarnings("unchecked")
	public static Object getInstance(Class clazz, Object param) {
		try {
			Constructor con = clazz.getConstructor(param.getClass());
			return con.newInstance(param);
		} catch (Exception e) {
			LOG.error(e);
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public static Object getInstance(Class clazz) {
		try {
			Constructor con = clazz.getConstructor();
			return con.newInstance();
		} catch (Exception e) {
			LOG.error(e);
			return null;
		}

	}

	public static String getClassName(Object o) {
		return o == null ? null : getClass(o).getName();
	}

	@SuppressWarnings("unchecked")
	public static Class getClass(Object o) {
		return o.getClass();
	}

	public static BeanMap getBean() {
		return BEAN_MAP.get();
	}

	public static BeanMap setBean(Object bean) {
		try {
			BeanMap beanMap = (BeanMap) BEAN_MAP.get();
			if (beanMap == null
					|| beanMap.getBean().getClass() != bean.getClass()) {
				beanMap = null;
				try {
					beanMap = BeanMap.create(bean);
					BEAN_MAP.set(beanMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				beanMap.setBean(bean);
			}
			return beanMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean hasProperty(Object bean, String property) {
		BeanMap beanMap = setBean(bean);
		return beanMap.containsKey(property);
	}

	public static Object getProperty(Object bean, String property) {
		return setBean(bean).get(property);
	}

	@SuppressWarnings("rawtypes")
	public static void setProperty(Object bean, Map propertyValue) {
		if (bean != null && propertyValue != null) {
			setBean(bean).putAll(propertyValue);
		}
	}
	
	public static void setBeanProperty(Object bean, String propertyName, Object propertyValue) throws SCFSException {
		if (bean != null && hasProperty(bean,propertyName)) {
			BeanMap beanMap = setBean(bean);
			Class clazz = beanMap.getPropertyType(propertyName);
			
			if (Timestamp.class.isAssignableFrom(clazz)) {
				if(propertyValue==null){
					return;
				}
				Date date = null;
				if(JSONObject.class.isAssignableFrom(propertyValue.getClass())){
					date = (Date) JsonUtil.getDTO(propertyValue.toString(), Date.class);
				}else{
					date = DataTypeParser.parseDate(propertyValue);
				}
				beanMap.put(propertyName, new Timestamp(date.getTime()));
			} else if (Integer.class.isAssignableFrom(clazz)
					|| int.class.isAssignableFrom(clazz)) {
				if(propertyValue==null){
					return;
				}
				Number date = DataTypeParser.parseInt(propertyValue);
				beanMap.put(propertyName, date);
			} else if (Long.class.isAssignableFrom(clazz)||long.class.isAssignableFrom(clazz)) {
				if(propertyValue==null){
					return;
				}
				Number date = DataTypeParser.parseLong(propertyValue);
				beanMap.put(propertyName, date);
			} else if (Short.class.isAssignableFrom(clazz)||short.class.isAssignableFrom(clazz)) {
				if(propertyValue==null){
					return;
				}
				Number date = DataTypeParser.parseLong(propertyValue);
				beanMap.put(propertyName, date);
			} else if (Double.class.isAssignableFrom(clazz)||double.class.isAssignableFrom(clazz)) {
				if(propertyValue==null){
					return;
				}
				
				Number date = DataTypeParser.parseDouble(propertyValue);
				beanMap.put(propertyName, date);
			} else if (BigDecimal.class.isAssignableFrom(clazz)) {
				if(propertyValue==null){
					return;
				}
				BigDecimal date = new BigDecimal(propertyValue.toString());
				beanMap.put(propertyName, date);
			}else if (String.class.isAssignableFrom(clazz)) {
				String strValue = "";
				if (propertyValue == null)
					strValue="";
				else{
					strValue= propertyValue.toString();	
				}
				if(StringUtil.isNull(strValue))
					strValue = "";
				beanMap.put(propertyName, strValue);
			}else {
				beanMap.put(propertyName, propertyValue);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void setBeanProperty(Object bean, Map propertyValue) {
		if (bean != null && propertyValue != null) {
			BeanMap beanMap = setBean(bean);
			Object[] keys = beanMap.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				String key = keys[i].toString();
				if (propertyValue.containsKey(key)) {
					try {
						Class clazz = beanMap.getPropertyType(key);
						if (Timestamp.class.isAssignableFrom(clazz)) {
							Object value = propertyValue.get(key);
							if(value==null){
								continue;
							}
							String strValue = value.toString();
							if(StringUtil.isNull(strValue)){
								continue;
							}
							Date date = null;
							if(JSONObject.class.isAssignableFrom(value.getClass())){
								date = (Date) JsonUtil.getDTO(strValue, Date.class);
							}else{
								date = DataTypeParser.parseDate(strValue);
							}
							beanMap.put(key, new Timestamp(date.getTime()));
						} else if (Integer.class.isAssignableFrom(clazz)
								|| int.class.isAssignableFrom(clazz)) {
							Object value = propertyValue.get(key);
							if (value == null||StringUtil.isNull(value.toString()))
								continue;
							Number date = DataTypeParser.parseInt(value);
							beanMap.put(key, date);
						} else if (Long.class.isAssignableFrom(clazz)||long.class.isAssignableFrom(clazz)) {
							Object value = propertyValue.get(key);
							if (value == null||StringUtil.isNull(value.toString()))
								continue;
							Number date = DataTypeParser.parseLong(value);
							beanMap.put(key, date);
						} else if (Short.class.isAssignableFrom(clazz)||short.class.isAssignableFrom(clazz)) {
							Object value = propertyValue.get(key);
							if (value == null||StringUtil.isNull(value.toString()))
								continue;
							Number date = DataTypeParser.parseLong(value);
							beanMap.put(key, date);
						} else if (Double.class.isAssignableFrom(clazz)||double.class.isAssignableFrom(clazz)) {
							Object value = propertyValue.get(key);
							if (value == null)
								continue;
							String strValue = value.toString();
							if(StringUtil.isNull(strValue)){
								continue;
							}
							Number date = DataTypeParser.parseDouble(strValue);
							beanMap.put(key, date);
						} else if (BigDecimal.class.isAssignableFrom(clazz)) {
							Object value = propertyValue.get(key);
							if (value == null||StringUtil.isNull(value.toString()))
								continue;
							BigDecimal date = new BigDecimal(value.toString());
							beanMap.put(key, date);
						}else if (String.class.isAssignableFrom(clazz)) {
							Object value = propertyValue.get(key);
							String strValue = "";
							if (value == null)
								strValue="";
							else{
								strValue= value.toString();	
							}
							if(StringUtil.isNull(strValue))
								strValue = "";
							beanMap.put(key, strValue);
						}else {
							Object value = propertyValue.get(key);
							beanMap.put(key, value);
						}

					} catch (Exception e) {
						System.err.println("Parse Bean proroty exception: "
								+ e.toString());
					}

				}
			}
		}
	}

	public static Map toMap(Object obj) {
		return setBean(obj);
	}

	public static void copyProperties(Object source, Object dest,
			String[] properties) {

		if (source != null && dest != null && properties != null) {
			BeanMap sourceMap = BeanMap.create(source);
			BeanMap destMap = BeanMap.create(dest);
			for (String p : properties) {
				destMap.put(p, sourceMap.get(p));
			}
		}
	}

	public static Object copy(Object from, Object to) {
		String beanKey = generateKey(from.getClass(), to.getClass());
		BeanCopier copier = null;

		if (!beanCopierMap.containsKey(beanKey)) {
			copier = BeanCopier.create(from.getClass(), to.getClass(), false);
			beanCopierMap.put(beanKey, copier);
		} else {
			copier = beanCopierMap.get(beanKey);
		}
		// Converter converter = new IgnoreNullConverter();
		copier.copy(from, to, null);
		return to;
	}

	private static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

	private static String generateKey(Class<?> class1, Class<?> class2) {
		return class1.toString() + class2.toString();
	}

	@SuppressWarnings("unchecked")
	public static List cloneList(List sourceList)
			throws InstantiationException, IllegalAccessException {
		List newList = null;
		if (sourceList != null) {
			newList = new ArrayList();
			for (Object o : sourceList) {
				newList.add(copy(o, o.getClass().newInstance()));
			}
		}
		return newList;
	}

	public static Object transReqestToEntity(Map request, Object entity)
			throws Exception {
		Class entityClass = entity.getClass();
		Method[] methods = entityClass.getDeclaredMethods();
		for (int j = 0; j < methods.length; j++) {
			Method method = methods[j];
			String methodName = method.getName();
			if (methodName.length() > 3
					&& "set".equals(methodName.substring(0, 3))) {
				String fixName = methodName.substring(3, methodName.length());
				String key = fixName;
				if ("".equals(request.get(key))) {
					continue;
				}
				Class parmType = method.getParameterTypes()[0];
				Object value = null;
				if (parmType.equals(String.class)) {
					value = request.get(key);
				} else if (parmType.equals(int.class)
						|| parmType.equals(Integer.class)) {
					value = request.get(key);
				} else if (parmType.equals(Double.class)
						|| parmType.equals(double.class)) {
					value = request.get(key);
				} else if (parmType.equals(Date.class)) {
					value = request.get(key);
				} else if (parmType.equals(Long.class)
						|| parmType.equals(long.class)) {
					value = request.get(key);
				} else if (parmType.equals(Boolean.class)
						|| parmType.equals(boolean.class)) {
					value = Boolean.parseBoolean((String) request.get(key));
				} else if (parmType.equals(BigDecimal.class)) {
					value = new BigDecimal((String) request.get(key));
				} else {
					value = request.get(key);
				}
				method.invoke(entity, value);
			}
		}
		return entity;
	}

	public static Object transReqestToEntity(Element request, Object entity)
			throws Exception {
		Class entityClass = entity.getClass();
		Method[] methods = entityClass.getDeclaredMethods();
		for (int j = 0; j < methods.length; j++) {
			Method method = methods[j];
			String methodName = method.getName();
			if (methodName.length() > 3
					&& "set".equals(methodName.substring(0, 3))) {
				String fixName = methodName.substring(3, methodName.length());
				String key = StringUtil.firstLowerName(fixName); // Winston
				if ("".equals(XMLManager.getChildNodeValue(request, key, true))) {
					continue;
				}
				Class parmType = method.getParameterTypes()[0];
				Object value;
				String strValue = XMLManager.getChildNodeValue(request, key,
						true);

				if (parmType.equals(String.class)) {
					value = XMLManager.getChildNodeValue(request, key, true);
				} else if (parmType.equals(int.class)
						|| parmType.equals(Integer.class)) {
					if (StringUtil.isTrimEmpty(strValue))
						continue;
					value = Integer.parseInt(strValue);
				} else if (parmType.equals(short.class)
						|| parmType.equals(Short.class)) {
					if (StringUtil.isTrimEmpty(strValue))
						continue;
					value = Short.parseShort(strValue);
				} else if (parmType.equals(Double.class)
						|| parmType.equals(double.class)) {
					if (StringUtil.isTrimEmpty(strValue))
						continue;
					value = Double.valueOf(strValue);
				} else if (Date.class.isAssignableFrom(parmType)) {

					if (StringUtil.isTrimEmpty(strValue)
							|| "null".equalsIgnoreCase(strValue))
						value = DateTimeUtil.getTimestamp();
					else
						value = DateTimeUtil.getDateTime(strValue);
				} else if (parmType.equals(Long.class)
						|| parmType.equals(long.class)) {
					if (StringUtil.isTrimEmpty(strValue))
						continue;
					value = XMLManager.getChildNodeValue(request, key, true);
				} else if (parmType.equals(Boolean.class)
						|| parmType.equals(boolean.class)) {
					value = Boolean.parseBoolean(strValue);
				} else if (parmType.equals(BigDecimal.class)) {
					if (StringUtil.isTrimEmpty(strValue))
						strValue = "0";
					value = new BigDecimal(strValue);
				} else {
					value = strValue;
				}
				try {
					method.invoke(entity, value);
				} catch (Exception e) {
					System.out
							.println(String
									.format("transReqestToEntity error :[%s] [%s]cannot be cast to [%s].",
											key, value, parmType));
				}

			}
		}
		return entity;
	}

	public static boolean isWrapClass(Class clz) {
		try {
			return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isBaseJavaType(Class<?> clz) {
		return clz != null && clz.getClassLoader() == null;
	}

	public static void populateData(Object entity, Map<String, Object> container)
			throws Exception, IllegalArgumentException,
			InvocationTargetException {
		populateData(entity, container, null);
	}

	public static void populateData(Object entity,
			Map<String, Object> container, List<String> fieldList)
			throws Exception {
		if (fieldList != null && !fieldList.isEmpty()) {
			for (String string : fieldList) {
				Method method = getGetterMethod(entity.getClass(), string);
				setProperty(entity, method, container, string);
			}
		} else {
			Class<?> entityClass = entity.getClass();
			Method[] methods = entityClass.getDeclaredMethods();
			for (int j = 0; j < methods.length; j++) {
				Method method = methods[j];
				try {
					String methodName = method.getName();
					if (methodName.length() > 3
							&& "get".equals(methodName.substring(0, 3))) {
						String fixName = methodName.substring(3,
								methodName.length());
						fixName = fixName.substring(0, 1).toLowerCase()
								+ fixName.substring(1);
						setProperty(entity, method, container, fixName);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}
		}
	}

	public static Object getDBFiledValue(Class<?> clazz, String inputStr)
			throws Exception {
		if (Date.class.isAssignableFrom(clazz)) {
			Object retObject = null;
			if(Date.class.equals(clazz)){
				if (StringUtil.isTrimEmpty(inputStr) || "null".equalsIgnoreCase(inputStr))
					retObject = DateTimeUtil.getSysDate();
				else
					retObject = DateTimeUtil.getDate(inputStr);
			}else if(Timestamp.class.equals(clazz)){
				if (StringUtil.isTrimEmpty(inputStr) || "null".equalsIgnoreCase(inputStr))
					retObject = DateTimeUtil.getTimestamp();
				else
					retObject = DateTimeUtil.getDateTime(inputStr);
			}
			
			return retObject;
		}
		Constructor<?> con = clazz.getConstructor(String.class);
		if (con != null)
			return con.newInstance(inputStr);

		Object retObject = null;
		if (clazz.equals(String.class)) {
			retObject = inputStr;
		} else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			retObject = Integer.parseInt(inputStr);
		} else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
			retObject = Short.parseShort(inputStr);
		} else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
			retObject = Double.valueOf(inputStr);
		} else if (Date.class.isAssignableFrom(clazz)) {

			if (StringUtil.isTrimEmpty(inputStr)
					|| "null".equalsIgnoreCase(inputStr))
				retObject = DateTimeUtil.getTimestamp();
			else
				retObject = DateTimeUtil.getDateTime(inputStr);
		} else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
			retObject = Long.parseLong(inputStr);
		} else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
			retObject = Boolean.parseBoolean(inputStr);
		} else if (clazz.equals(BigDecimal.class)) {
			retObject = new BigDecimal(inputStr);
		} else {
			retObject = inputStr;
		}
		return retObject;
	}

	private static void setProperty(Object entity, Method method,
			Map<String, Object> container, String fieldName) throws Exception {
		Object value = method.invoke(entity);
		Class<?> parmType = method.getReturnType();
		if (Timestamp.class.isAssignableFrom(parmType)) {
			if (value == null)
				return;
			container.put(fieldName,
					DataTypeParser.formatDateTime((Date) value));
		} else if (List.class.isAssignableFrom(parmType)) {
			List<Object> list = (List<Object>) value;
			for (Object obj : list) {
				if (obj == null)
					continue;
				Map<String, Object> newData = new HashMap<String, Object>();
				container.put(fieldName, newData);
				populateData(obj, newData);
			}
		} else if (BeanUtils.isBaseJavaType(parmType)) {
			try {
				container.put(fieldName,
						value == null ? "" : String.valueOf(value));
			} catch (Exception e) {

			}

		} else {
			if (value == null)
				return;

			if ("id".equalsIgnoreCase(fieldName)) {
				populateData(value, container);
			} else {
				Map<String, Object> childData = new HashMap<String, Object>();
				container.put(fieldName, childData);
				populateData(value, childData);
			}
		}
	}

	public static Method getGetterMethod(Class type, String fieldName) {
		try {
			return type.getMethod(getGetterName(type, fieldName));
		} catch (NoSuchMethodException e) {
			// logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String getGetterName(Class type, String fieldName) {
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");
		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}
}