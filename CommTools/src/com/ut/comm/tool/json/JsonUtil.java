package com.ut.comm.tool.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ut.comm.tool.BeanUtils;
import com.ut.comm.tool.string.StringUtil;
import com.ut.comm.tool.xml.XMLManager;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.xml.XMLSerializer;

public class JsonUtil {

	public final static String JSON_ATTRIBUTE = "json";
	public final static String JSON_ATTRIBUTE1 = "json1";
	public final static String JSON_ATTRIBUTE2 = "json2";
	public final static String JSON_ATTRIBUTE3 = "json3";
	public final static String JSON_ATTRIBUTE4 = "json4";

	/**
	 * "aBean" : {"aBeanId" : aBeanIdValue, ...}}
	 * 
	 * @param object
	 * @param clazz
	 * @return
	 */
	public static Object getDTO(String jsonString, Class clazz) {
		JSONObject jsonObject = null;
		try {
			setDataFormat2JAVA();
			jsonObject = JSONObject.fromObject(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toBean(jsonObject, clazz);
	}

	/**
	 * nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}, beansList:[{}, {},
	 * ...]}
	 * 
	 * @param jsonString
	 * @param clazz
	 * @param map
	 *            Bean.class)
	 * @return
	 */
	public static Object getDTO(String jsonString, Class clazz, Map map) {
		JSONObject jsonObject = null;
		try {
			setDataFormat2JAVA();
			jsonObject = JSONObject.fromObject(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toBean(jsonObject, clazz, map);
	}

	/**
	 * idValue, "name" : nameValue}, ...]
	 * 
	 * @param object
	 * @param clazz
	 * @return
	 */
	public static Object[] getDTOArray(String jsonString, Class clazz) {
		setDataFormat2JAVA();
		JSONArray array = JSONArray.fromObject(jsonString);
		Object[] obj = new Object[array.size()];
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			obj[i] = JSONObject.toBean(jsonObject, clazz);
		}
		return obj;
	}

	/**
	 * idValue, "name" : nameValue}, ...]
	 * 
	 * @param object
	 * @param clazz
	 * @param map
	 * @return
	 */
	public static Object[] getDTOArray(String jsonString, Class clazz, Map map) {
		setDataFormat2JAVA();
		JSONArray array = JSONArray.fromObject(jsonString);
		Object[] obj = new Object[array.size()];
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			obj[i] = JSONObject.toBean(jsonObject, clazz, map);
		}
		return obj;
	}

	/**
	 * 
	 * @param object
	 * @param clazz
	 * @return
	 */
	public static List getDTOList(String jsonString, Class clazz) {
		setDataFormat2JAVA();
		JSONArray array = JSONArray.fromObject(jsonString);
		List list = new ArrayList();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			JSONObject jsonObject = (JSONObject) iter.next();
			list.add(JSONObject.toBean(jsonObject, clazz));
		}
		return list;
	}

	/**
	 * 
	 * @param object
	 * @param clazz
	 * @param map
	 * @return
	 */
	public static List getDTOList(String jsonString, Class clazz, Map map) {
		setDataFormat2JAVA();
		JSONArray array = JSONArray.fromObject(jsonString);
		List list = new ArrayList();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			JSONObject jsonObject = (JSONObject) iter.next();
			list.add(JSONObject.toBean(jsonObject, clazz, map));
		}
		return list;
	}

	/**
	 * @param object
	 * @return
	 */
	public static Map getMapFromJson(String jsonString) {
		setDataFormat2JAVA();
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Map map = new HashMap();
		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}
	
	public static Map getMapFromJson(JSONObject jsonObject) {
		setDataFormat2JAVA();
//		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Map map = new HashMap();
		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}

	/**
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArrayFromJson(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public static String getJSONString(Object object) {
		String jsonString = null;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		if (object != null) {
			if (object instanceof JSONObject) {
				jsonString = object.toString();
			}else if (object instanceof Collection || object instanceof Object[]) {
				jsonString = JSONArray.fromObject(object, jsonConfig).toString();
			} else {
				jsonString = JSONObject.fromObject(object, jsonConfig).toString();
			}
		}
		return jsonString == null ? "{}" : jsonString;
	}
	
	public static JSONObject getJSON(Object object) {
		JSONObject jsonString = null;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		if (object != null) {
			if (object instanceof Collection || object instanceof Object[]) {
				jsonString = (JSONObject) JSONArray.fromObject(object, jsonConfig).get(0);
			} else {
				jsonString = JSONObject.fromObject(object, jsonConfig);
			}
		}
		return jsonString;
	}

	private static void setDataFormat2JAVA() {
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"}));
	}

	public static JSONObject createJson() {
		return createJson(null);
	}

	public static JSONObject createJson(String name) {
		JSONObject obj = new JSONObject();
		if(StringUtil.isTrimNotEmpty(name))
			obj.put("_json_name", name);
		return obj;
	}
	
	public static JSONObject clone(JSONObject obj){
		String json;
		try {
			json = JsonUtil.getJSONString(obj);
			JSONObject cloneObj = JsonUtil.getJsonObj(json);
			return cloneObj;
		} catch (Exception e) {
			e.printStackTrace();
			return obj;
		}
		
	}
	
	public static void append(JSONObject parent,JSONObject child){
		Assert.notNull(parent,"Parent json must not be null.");
		if(child==null)
			return;
		String childName = child.getString("_json_name");
		Assert.isTrue(StringUtil.isTrimNotEmpty(childName),"Child json must has name attribute.");
		parent.put(childName, child);
	}
	public static boolean hasChild(JSONObject parent,String childName){
		Assert.notNull(parent,"Parent json must not be null.");
		if(StringUtil.isTrimEmpty(childName)){
			return false;
		}
		
		return parent.containsKey(childName);
	}
	
	public static JSONObject getChildJson(JSONObject parent,String childName){
		Assert.notNull(parent,"Parent json must not be null.");
	
		if(StringUtil.isTrimEmpty(childName)){
			return null;
		}
		
		if(hasChild(parent,childName)){
			Object childObj = parent.get(childName);
			if (childObj instanceof JSONObject) {
				return (JSONObject) childObj;
			}else if(childObj instanceof JSONArray){
				return (JSONObject) childObj;
			}else {
				JSONObject obj = getJsonObj(childObj.toString());
				return 	obj;
			}
		}else{
			JSONObject childObj = createJson(childName);
			append(parent, childObj);
			return childObj;
		}
	}
	
	public static JSONObject findJson(JSONObject parent, String path) {
		Assert.notNull(parent, "Parent json must not be null.");

		if (StringUtil.isTrimEmpty(path)) {
			return parent;
		}

		String[] nodeNames = path.split("/");
		JSONObject returnJson = parent;
		for (String string : nodeNames) {
			returnJson = getChildJson(returnJson,string);
		}
		return returnJson;
	}
	
	public static JSONObject getChildJsonObj(JSONObject parent,String childName){
		Assert.notNull(parent,"Parent json must not be null.");
	
		if(StringUtil.isTrimEmpty(childName)){
			return null;
		}
		
		if(hasChild(parent,childName)){
			return (JSONObject) parent.get(childName);
		}else{
			return null;
		}
	}
	
	public static Object getBean(JSONObject json,Class<?> clazz){
		setDataFormat2JAVA();
		return JSONObject.toBean(json, clazz);
	}
	
	public static void setBeanProperties(JSONObject json,Object obj){
		Object tempObj = getBean(json,obj.getClass());
		BeanUtils.copy(tempObj, obj);
	}
	
	public static Object getProperty(JSONObject json,String propertyName){
		return json.get(propertyName);
	}
	
	public static JSONObject getJsonObj(String jsonStr){
		JSONObject jsonObject = null;
		try {
			setDataFormat2JAVA();
			jsonObject = JSONObject.fromObject(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static Object getObj(JSONObject parent,String childName){
		if(StringUtil.isTrimEmpty(childName))
			return null;
		if(parent.containsKey(childName))
			return parent.get(childName);
		
		for (Iterator iter = parent.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			Object value = parent.get(key);
			if(JSONObject.class.isAssignableFrom(value.getClass())){
				Object childValue = getObj((JSONObject)value,childName);
				if(childValue!=null)
					return childValue;
			}
		}
		return null;
	}
	
	public static Element convertToXmlElement(String jsonSource){
		JSONObject json = getJsonObj(jsonSource);
		return convertToXmlElement(json);
	}
	
	public static Element convertToXmlElement(JSONObject json){
		XMLSerializer xmlSerializer = new XMLSerializer();
		xmlSerializer.setObjectName("record");
		String xml = xmlSerializer.write(json);
		Document xmlRoot;
		try {
			xmlRoot = XMLManager.xmlStrToDom(xml);
			return (Element) xmlRoot.getFirstChild();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String convertToXmlStr(String jsonSource){
		JSONObject json = getJsonObj(jsonSource);
		XMLSerializer xmlSerializer = new XMLSerializer();
		xmlSerializer.setObjectName("root");
		String xml = xmlSerializer.write(json);
		return xml;
	}
	
	public static String convertToJsonStr(String xmlSource){
	    JSONObject json = (JSONObject) new XMLSerializer().read(xmlSource);
	    String strJson = json.toString();
	    return strJson;
	}
	
	public static boolean isGridJson(JSONObject jsonObj){
		if(jsonObj==null){
			return false;
		}
		return jsonObj.containsKey("_total_rows");
	}
	
	public static boolean isEmptyJson(JSONObject jsonObj){
		if(jsonObj==null){
			return true;
		}
		JSONObject cloneJson = JsonUtil.clone(jsonObj);
		if(cloneJson.containsKey("_json_name"))
			cloneJson.remove("_json_name");
		return cloneJson.size()<=0;
	}
	
	public static JSONObject getTrxGridData(List<? extends Object> dataList){
		JSONObject root  = JsonUtil.createJson();
		for (int i = 0; i < dataList.size(); i++) {
			root.put("rows"+i, JsonUtil.getJSONString(dataList.get(i)));
		}
		root.put("_total_rows", dataList.size());
		return root;
	}
	
	public static JSONObject getTrxGridData(List<? extends Object> dataList,String jsonName){
		JSONObject root  = JsonUtil.createJson(jsonName);
		
		for (int i = 0; i < dataList.size(); i++) {
			root.put("rows"+i, JsonUtil.getJSONString(dataList.get(i)));
		}
		root.put("_total_rows", dataList.size());
		
		return root;
	}
	
	public static int getDoRecordCount(Object trxObj) {
		JSONObject jsonObj = (JSONObject) trxObj;
		if (jsonObj != null && jsonObj.containsKey("_total_rows")) {
			return jsonObj.getInt("_total_rows");
		}
		return 0;
	}

	public static JSONObject getDoRecordData(Object rows, int recodIndex) {
		JSONObject jsonObj = (JSONObject) rows;
		String key = "rows" + recodIndex;
		if (jsonObj.containsKey(key)) {
			return jsonObj.getJSONObject(key);
		}
		return null;
	}
	
	public static JSONObject appendDoData(JSONObject rows1, JSONObject rows2) {
		int count1 = getDoRecordCount(rows1);
		int count2 = getDoRecordCount(rows2);
		List<JSONObject> records = new ArrayList<JSONObject>();
		for (int i = 0; i < count1; i++) {
			records.add(getDoRecordData(rows1, i));
		}
		
		for (int i = 0; i < count2; i++) {
			records.add(getDoRecordData(rows2, i));
		}
		return getTrxGridData(records);
	}
	
	public static JSONObject appendDoData(JSONObject rows1, JSONObject rows2,String rootNm) {
		int count1 = getDoRecordCount(rows1);
		int count2 = getDoRecordCount(rows2);
		List<JSONObject> records = new ArrayList<JSONObject>();
		for (int i = 0; i < count1; i++) {
			records.add(getDoRecordData(rows1, i));
		}
		
		for (int i = 0; i < count2; i++) {
			records.add(getDoRecordData(rows2, i));
		}
		return getTrxGridData(records,rootNm);
	}
}