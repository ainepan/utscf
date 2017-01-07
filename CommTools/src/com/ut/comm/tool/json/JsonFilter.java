package com.ut.comm.tool.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ut.comm.tool.json.filter.InvisibleFilter;
import com.ut.comm.tool.json.filter.NameFilter;
import com.ut.comm.tool.json.filter.VisibleFilter;

public class JsonFilter {

	private static final Log OA_LOG = LogFactory.getLog(JsonFilter.class);
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final List<String> HIBERNATE_JOIN_PROPERTY = new ArrayList<String>();
	private static final JsonValueProcessor DATE_CONVERT = new JsonDateValueProcessor(DATE_FORMAT);
	private static final Map<String, PropertyFilter> FILTER_MAP = new Hashtable<String, PropertyFilter>();
	private static final Map<String, JsonConfig> CONFIG_MAP = new Hashtable<String, JsonConfig>();

	static {

		HIBERNATE_JOIN_PROPERTY.add("handler");
		HIBERNATE_JOIN_PROPERTY.add("hibernateLazyInitializer");

		FILTER_MAP.put("exludeParent", new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				String clazz = value == null ? null : value.getClass().getName();
				return (clazz != null && clazz.startsWith("com"));
			}
		});

		FILTER_MAP.put("exludeSetAndSomeProperty", new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (value != null && value instanceof java.util.Set) {
					return true;
				} else {
					return false;
				}
			}
		});

		FILTER_MAP.put("onlyBaseProperty", new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				String clazz = value == null ? null : value.getClass().getName();
				if (clazz == null || clazz.startsWith("java") || clazz.startsWith("net.sf.json.JSONObject")) {
					return false;
				} else {
					OA_LOG.debug("JSON Filter class is :" + clazz);
					return true;
				}
			}
		});

	}

	public static boolean isRuntimeJoinProperty(String name) {
		return HIBERNATE_JOIN_PROPERTY.contains(name);
	}

	public static JsonConfig defaultFilter() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, DATE_CONVERT);
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, DATE_CONVERT);
		return jsonConfig;
	}

	public static JsonConfig defaultFilter(String dateFormat) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor(dateFormat));
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonDateValueProcessor(dateFormat));
		return jsonConfig;
	}

	public static JsonConfig annotationInvisible(String[] annotation) {

		JsonConfig config = defaultFilter();

		config.setJsonPropertyFilter(new InvisibleFilter(annotation)); // 标注了
																		// annotation
																		// 的属性才显示
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		return config;

	}

	public static JsonConfig annotationVisible(String[] annotation) {

		JsonConfig config = defaultFilter();

		config.setJsonPropertyFilter(new VisibleFilter(annotation)); // 标注了
																		// annotation
																		// 的属性将被过滤掉

		return config;

	}

	public static JsonConfig includeNames(String[] names) {

		JsonConfig config = defaultFilter();

		config.setJsonPropertyFilter(new NameFilter(names));

		return config;

	}

	public static JsonConfig exclude(String[] names) {

		ArrayList<String> array = new ArrayList<String>();

		if (names != null && names.length > 0) {
			List<String> list = java.util.Arrays.asList(names);
			array.addAll(list);
		}
		array.addAll(HIBERNATE_JOIN_PROPERTY);
		names = (String[]) array.toArray(new String[array.size()]);

		JsonConfig config = defaultFilter();
		config.setExcludes(names);

		return config;

	}

	/*
	 * 只列出基本属性(使用指定的日期转换类)
	 */
	@SuppressWarnings("unchecked")
	public static JsonConfig onlyBaseProperty(String dateFormat) {
		Map<Class, JsonValueProcessor> processor = new HashMap<Class, JsonValueProcessor>();
		JsonValueProcessor dateProcess = new JsonDateValueProcessor(dateFormat);
		processor.put(Date.class, dateProcess);
		processor.put(java.sql.Date.class, dateProcess);
		return onlyBaseProperty(processor);
	}

	/*
	 * 
	 * 只列出基本属性(使用指定的数据转换类)
	 * 
	 * @param processor Map类型，指定数据转换类
	 */
	@SuppressWarnings("unchecked")
	public static JsonConfig onlyBaseProperty(Map<Class, JsonValueProcessor> processor) {

		JsonConfig config = new JsonConfig();
		if (processor != null) {
			for (Map.Entry<Class, JsonValueProcessor> entry : processor.entrySet()) {
				config.registerJsonValueProcessor(entry.getKey(), entry.getValue());
			}
		}
		// 如果未指定日期转换
		if (processor == null || !processor.containsKey(Date.class)) {
			processor.put(Date.class, DATE_CONVERT);
			processor.put(java.sql.Date.class, DATE_CONVERT);
		}

		config.setExcludes((String[]) HIBERNATE_JOIN_PROPERTY.toArray(new String[HIBERNATE_JOIN_PROPERTY.size()]));
		config.setJsonPropertyFilter(FILTER_MAP.get("onlyBaseProperty"));

		return config;

	}

	public static JsonConfig exludeSetProperty() {
		return exludeSetAndSomeProperty(null);
	}

	public static JsonConfig exludeSetProperty(String dateFormat) {

		return exludeSetAndSomeProperty(null, dateFormat);

	}

	public static JsonConfig exludeSetAndSomeProperty(String[] names) {
		return exludeSetAndSomeProperty(names, null);
	}

	public static JsonConfig exludeSetAndSomeProperty(String[] names, String dateFormat) {

		JsonConfig config = new JsonConfig();
		JsonValueProcessor dateProcess = new JsonDateValueProcessor(dateFormat);
		ArrayList<String> array = new ArrayList<String>();

		if (names != null && names.length > 0) {
			List<String> list = java.util.Arrays.asList(names);
			array.addAll(list);
		}
		array.addAll(HIBERNATE_JOIN_PROPERTY);
		names = (String[]) array.toArray(new String[array.size()]);

		config.setExcludes(names);

		config.setIgnoreDefaultExcludes(false);
		config.registerJsonValueProcessor(Date.class, dateProcess);
		config.registerJsonValueProcessor(java.sql.Date.class, dateProcess);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setJsonPropertyFilter(FILTER_MAP.get("exludeSetAndSomeProperty"));

		return config;

	}

	// ////////////////////////////////////////////////////////////
	// ------------------静态存储: 不用每次实例化----------------------
	// ////////////////////////////////////////////////////////////

	/*
	 * 只列出基本属性(使用默认的日期转换类)
	 */
	@SuppressWarnings("unchecked")
	public static JsonConfig onlyBaseProperty() {
		JsonConfig config = CONFIG_MAP.get("onlyBaseProperty");
		if (config == null) {
			Map<Class, JsonValueProcessor> processor = new HashMap<Class, JsonValueProcessor>();
			processor.put(Date.class, DATE_CONVERT);
			processor.put(java.sql.Date.class, DATE_CONVERT);
			config = onlyBaseProperty(processor);
			CONFIG_MAP.put("onlyBaseProperty", config);
		}
		return config;
	}

	/**
	 * 去掉上级parent,避免json循环
	 * 
	 * @return
	 */
	public static JsonConfig exludeParent() {

		JsonConfig config = CONFIG_MAP.get("exludeParent");

		if (config == null) {
			config = defaultFilter();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			config.setJsonPropertyFilter(FILTER_MAP.get("exludeParent"));
			CONFIG_MAP.put("exludeParent", config);
		}

		return config;

	}

}
