package com.ut.comm.xml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.cglib.beans.BeanMap;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ut.comm.tool.BeanUtils;
import com.ut.comm.tool.json.JsonUtil;
import com.ut.comm.tool.string.StringUtil;
import com.ut.comm.tool.xml.XMLManager;

public class XMLParaHelper {
	
	private static Logger logger = LoggerFactory.getLogger(XMLParaHelper.class);

	public final static String NOTE_NAME_SERVICE = "service";
	
	public final static String NOTE_NAME_BEFORE_SERVICE = "bfservice";

	public final static String NOTE_NAME_LOGICFLOW = "logicflow";
	
	public final static String NOTE_NAME_LOGIC_NODE = "lnode";
	
	public final static String NOTE_NAME_LOGIC_TRANS_NODE = "transfornode";

	public final static String NOTE_NAME_FUNCTION = "function";

	public final static String NOTE_NAME_CATALOG = "catalog";

	public final static String NOTE_NAME_FUNC = "func";

	public final static String NOTE_NAME_PAGE = "page";

	public final static String NOTE_NAME_QUERY = "query";
	
	public final static String NOTE_NAME_QUERY_NODE = "qnode";

	public final static String NOTE_NAME_BATCHS_ROOT = "batchs";

	public final static String NOTE_NAME_BATCH = "batch";

	public final static String NOTE_NAME_SCHEDULE = "schedule";

	public final static String NOTE_NAME_TASK = "task";
	
	public final static String NOTE_NAME_TRX_DATA_OBJ = "trxdatapara";

	public final static String NOTE_NAME_REPORT = "report";

	public final static String NOTE_NAME_WORKFLOW = "workflow";

	public final static String NOTE_NAME_ACCOUNTING = "accounting";
	
	public final static String NOTE_NAME_ACCOUNTING_ROOT = "accounting";

	public final static String NOTE_NAME_INQDATA = "inqdata";
	
	public final static String NOTE_NAME_GAPI = "gapi";

	public final static String NOTE_NAME_EDI = "edi";
	
	public final static String NOTE_NAME_ADVICE = "advice";
	
	public final static String NOTE_NAME_ESB_SERVICE = "esbservice";
	
	public final static String NOTE_NAME_ESB_SERVICES = "esbservices";
	
	public final static String NOTE_NAME_SYST = "syst";
	
	public final static String NOTE_NAME_ASSIGNFUNC = "autoassign";
	
	public final static String NOTE_NAME_PRODUCT = "product";
	
	public final static String NOTE_NAME_SYS_ROOT = "root";
	
	public final static String NOTE_NAME_FEE = "fee";
	
	public final static String NOTE_NAME_LIMIT = "limit";
	
	public final static String NOTE_NAME_INTEREST = "interest";
	
	public final static String NOTE_NAME_RISK = "risk";
	
	public final static String NOTE_NAME_METHOD_DEFINE = "methoddefine";
	
	public final static String NOTE_NAME_APP = "app";
	
	public final static String NOTE_NAME_COMM = "comm";
	
	public final static String NOTE_NAME_ACCOUNT_NO = "account";
	
	public final static String NOTE_NAME_ACCOUNT_RULE = "accounting";
	
	public final static String NOTE_NAME_PARTY = "party";
	
	public final static String PARA_PATH_SERVICE = "service";

	public final static String PARA_PATH_LOGICFLOW = "logicflow";
	
	public final static String PARA_PATH_CATALOG = "cata";

	public final static String PARA_PATH_FUNC = "func";

	public final static String PARA_PATH_PAGE = "page";

	public final static String PARA_PATH_QUERY = "query";
	
	public final static String PARA_PATH_BATCHS_ROOT = "apsyst/batchs";

	public final static String PARA_PATH_BATCH = "batch";

	public final static String PARA_PATH_TASK = "task";
	
	public final static String PARA_PATH_REPORT = "report";

	public final static String PARA_PATH_WORKFLOW = "workflow";

	public final static String PARA_PATH_ACCOUNTING = "accounting";
	
	public final static String PARA_PATH_ACCOUNTING_ROOT = "apsyst/accounting";

	public final static String PARA_PATH_INQDATA = "inqdata";
	
	public final static String PARA_PATH_GAPI = "gapi";

	public final static String PARA_PATH_EDI = "edi";
	
	public final static String PARA_PATH_ADVICE = "advice";
	
	public final static String PARA_PATH_TEMPLATE = "template";
	
	public final static String PARA_PATH_ESB_SERVICE = "esb/services";
	
	public final static String PARA_PATH_ESB_SERVICES = "syst/esb";
	
	public final static String PARA_PATH_AP_SYS = "apsyst";
	
	public final static String PARA_PATH_SYS = "syst";
	
	public final static String PARA_PATH_BU_CONFIG = "syst/bu";
	
	public final static String PARA_PATH_LOG_CONFIG = "syst/log";
	
	public final static String PARA_PATH_FEE = "fee";
	
	public final static String PARA_PATH_LIMIT = "limit";
	
	public final static String PARA_PATH_INTEREST = "interest";
	
	public final static String PARA_PATH_RISK = "risk";
	
	public final static String PARA_PATH_PRODUCT = "product";
	
	public final static String PARA_PATH_METHOD_DEFINE = "method";
	
	public final static String PARA_PATH_APP = "app";
	
	public final static String PARA_PATH_COMM = "comm";
	
	public final static String PARA_PATH_ACCOUNT_NO = "account";
	
	public final static String PARA_PATH_ACCOUNT_RULE = "accounting";
	
	public final static String PARA_PATH_PARTY = "party";
	
	private static Map<String, String> ignoreUpdateMap = new HashMap<String, String>();
	static{
		ignoreUpdateMap.put("propertyBean", "propertyBean");
		ignoreUpdateMap.put("proterties", "proterties");
		ignoreUpdateMap.put("nodeName", "nodeName");
	}

	/**
	 * 用于注册参数对应的bean，如/para/platform/cata对应CatalogObj
	 */
	private static Map<String, Class<? extends AbsObject>> registedXmlObject = new ConcurrentHashMap<String, Class<? extends AbsObject>>();

	/**
	 * @see 用于注册参数对应的bean，如/para/platform/cata对应CatalogObj
	 * @param path
	 * @param T
	 */
	public static void registeObject(String path, Class<? extends AbsObject> T) {
		if (hasRegisted(path)) {
			return;
		}
		registedXmlObject.put(path, T);
	}

	private static boolean hasRegisted(String xmlParaPath) {
		return registedXmlObject.containsKey(xmlParaPath);
	}

	public static Class<? extends AbsObject> getRegistedObjectClass(String xmlPath) {
		return registedXmlObject.get(xmlPath);
	}

	/**
	 * 用于注册参数对应的bean，如select对应SelectObj
	 */
	private static Map<String, Class<? extends AbsObject>> registedObjects = new ConcurrentHashMap<String, Class<? extends AbsObject>>();

	public static boolean hasResgistedObject(String key) {
		return registedObjects.containsKey(key);
	}

	public static Class<? extends AbsObject> getResgistedObjectByKey(String key) {
		return registedObjects.get(key);
	}

	/**
	 * @see 用于注册参数对应的bean，如select对应SelectObj
	 * @param key
	 * @param t
	 */
	public static void registeObjectBean(String key, Class<? extends AbsObject> t) {
		if (hasResgistedObject(key)) {
			return;
		}
		registedObjects.put(key, t);
	}

	/**
	 * @see XML --> Bean
	 * @param entity
	 *            空对象
	 * @param index
	 *            对象在XML文件中定义的位置
	 * @return
	 * @throws Exception
	 */
	public static void parseXml2Bean(AbsObject entity, Node paraNode) throws Exception {
		parseXml2Bean(entity, paraNode, 1);
	}

	public static void parseXml2Bean(AbsObject entity, Node paraNode, int index) throws Exception, Exception {
		String objTypeName = entity.getNodeName();
		Assert.notNull(objTypeName, "The node name is required; it must not be null");
		Node processNode = getProcessNode(objTypeName, paraNode, index);
		if (processNode == null) {
			logger.error(String.format("Current function doesn't have %s attribute.", objTypeName));
			return;
		}
		
		if (!processNode.hasChildNodes()) {
			if (processNode.hasAttributes()) {
				XMLParaParseHelper.parseFieldAttribute(entity, processNode);
			}
			return;
		}

		NodeList childs = processNode.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node node = childs.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			String nodeName = node.getNodeName();
			// 如果是本实例节点
			if (XMLParaHelper.hasResgistedObject(nodeName)) {
				Class<? extends AbsObject> clazz = XMLParaHelper.getResgistedObjectByKey(nodeName);
				AbsObject childEntity = clazz.newInstance();
				String methodName = converToProSetName(nodeName);
				MethodUtils.invokeMethod(entity, methodName, childEntity);
				// 需要处理index
				parseXml2Bean(childEntity, node, 1);
			} else {// 如果不是，作为属性注入
				
				try {
					String methodName = "";
					if(entity.isPropertyBean()){
						methodName = converToProSetName(nodeName);
						Method method =null;
						try{
							method = entity.getClass().getMethod(methodName, String.class);
							method.invoke(entity, XMLManager.getNodeValue(node, true));
						}catch(NoSuchMethodException e){
							methodName =  converToProSetName("proterty");
							method = entity.getClass().getMethod(methodName, String.class,Object.class);
							method.invoke(entity,nodeName, XMLManager.getNodeValue(node, true));
						}
					}else{
						methodName = converToProSetName(nodeName);
						Method method = entity.getClass().getMethod(methodName, String.class);
						method.invoke(entity, XMLManager.getNodeValue(node, true));
					}
					
				} catch (NoSuchMethodException e) {
					logger.error(
							String.format("Current Object [%s] doesn't contain attribute:%s", objTypeName, nodeName));
				} catch (IllegalAccessException e) {
					logger.error(
							String.format("Current Object [%s] doesn't contain attribute:%s", objTypeName, nodeName));
				} catch (InvocationTargetException e) {
					logger.error(
							String.format("Current Object [%s] doesn't contain attribute:%s", objTypeName, nodeName));
				}
			}
		}
		// 解析属性节点
		if (processNode.hasAttributes()) {
			XMLParaParseHelper.parseFieldAttribute(entity, processNode);
		}
	}

	/**
	 * @see Bean --> XML
	 * @param entity
	 *            参数实例
	 * @param paraNode
	 *            参数文件
	 * @param jsonObj
	 *            提交的json对象
	 * @param index
	 *            同名节点需要index区分具体值，如BatchPara中有beforetasks和aftertasks对应同一节点
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void parseBean2XML(AbsObject entity, Node paraNode) throws InstantiationException,
			IllegalAccessException {
		if(entity.isPropertyBean()){
			Map<String, Object> properties = entity.getProterties();
			if(properties!=null){
				Object[] keys = properties.keySet().toArray();
				for (int i = 0; i < keys.length; i++) {
					String key = keys[i].toString();
					Object value = properties.get(key);
					if (value == null) {
						continue;
					}
					Element ele = paraNode.getOwnerDocument().createElement(key);
					ele.appendChild(paraNode.getOwnerDocument().createTextNode(value.toString()));
					paraNode.appendChild(ele);
				}
				return ;
			}else{
				//继续解析其他属性，用常用方式解析
			}
		}
		BeanMap	beanMap= BeanUtils.setBean(entity);
		if (beanMap != null) {
			Object[] keys = beanMap.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				String key = keys[i].toString();
				Object value = beanMap.get(key);
				if (value == null) {
					continue;
				}
				String mappingKey = getParaMappingFieldname(key);
				
				if(ignoreUpdateMap.containsKey(mappingKey)){
					continue;
				}

				if (XMLParaHelper.hasResgistedObject(mappingKey)) {
					String methodName = converToProGetName(key);
					try {
						Object childInstances = MethodUtils.invokeMethod(entity, methodName, null);
						if(childInstances==null){
							continue;
						}
						if (List.class.isAssignableFrom(childInstances.getClass())) {
							List<AbsObject> childs = (List<AbsObject>) childInstances;
							for (AbsObject object : childs) {
								Element ele = paraNode.getOwnerDocument().createElement(mappingKey);
								paraNode.appendChild(ele);

								parseBean2XML(object,ele);
							}
						}else{
							AbsObject childObj = (AbsObject) childInstances;
							Element ele = paraNode.getOwnerDocument().createElement(mappingKey);
							paraNode.appendChild(ele);
							parseBean2XML(childObj,ele);
						}
					} catch (NoSuchMethodException e) {
						logger.error("parseBean2XML error :" + e.toString());
					} catch (InvocationTargetException e) {
						logger.error("parseBean2XML error :" + e.toString());
					}
				} else {
					//空属性不生成在当前文件中
					if(StringUtil.isNotNull(value.toString())){
						Element ele = paraNode.getOwnerDocument().createElement(key);
						ele.appendChild(paraNode.getOwnerDocument().createTextNode(value.toString()));
						paraNode.appendChild(ele);
					}
				}
			}
		}
	}

	/**
	 * @see JSON --> Bean
	 * @param entity
	 * @param jsonObj
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public static void parseJson2Bean(AbsObject entity, JSONObject jsonObj) throws Exception {
		BeanMap beanMap = BeanUtils.setBean(entity);
		if (beanMap != null) {
			Object[] keys = beanMap.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				String key = keys[i].toString();
				String mappingKey = getParaMappingFieldname(key);
				try {
					if (XMLParaHelper.hasResgistedObject(mappingKey)) {
						JSONObject childsJson = JsonUtil.getChildJson(jsonObj, key);
						
						if(JsonUtil.isEmptyJson(childsJson)){
							//不用set属性
						}else if(JsonUtil.isGridJson(childsJson)){
							int childCount = getRecords(childsJson);
							for (int j = 0; j < childCount; j++) {
								Class<? extends AbsObject> childClazz = XMLParaHelper.getResgistedObjectByKey(mappingKey);
								AbsObject childInstence = childClazz.newInstance();
								JSONObject childJson = getTrxDom(childsJson, j);
								String methodName = converToProSetName(mappingKey);
								MethodUtils.invokeMethod(entity, methodName, childInstence);// 调用set方法

								parseJson2Bean(childInstence, childJson);
							}
						}else{
							Class<? extends AbsObject> childClazz = XMLParaHelper.getResgistedObjectByKey(mappingKey);
							AbsObject childInstence = childClazz.newInstance();
							String methodName = converToProSetName(mappingKey);
							MethodUtils.invokeMethod(entity, methodName, childInstence);// 调用set方法

							parseJson2Bean(childInstence, childsJson);
						}
						
					} else {
						if(entity.isPropertyBean()){
							for (Iterator<String> iter = jsonObj.keys(); iter.hasNext();) {
								String jsonKey = (String) iter.next();
								String methodName =  converToProSetName("proterty");
								Object fieldValue = jsonObj.get(jsonKey);
								if(fieldValue!=null){
									Object[] params = new Object[]{jsonKey,fieldValue};
									MethodUtils.invokeMethod(entity, methodName, params);// 调用set方法
								}
							}
						}else{
							Object fieldValue = jsonObj.get(key);
							BeanUtils.setBeanProperty(entity, key, fieldValue);
						}
					}
				} catch (Exception e) {
					logger.error("parseJson2Bean error :" + e.toString());
					e.printStackTrace();
				}
			}
		}
	}

	private static String getParaMappingFieldname(String key) {
		if (key.endsWith("List")) {
			key = key.substring(0, key.length() - "List".length());
		}
		return key;
	}

	private static String converToProSetName(String nodeName) {
		return convertNodeNameToMethodName(nodeName, "");
	}

	private static String converToProGetName(String nodeName) {
		return convertNodeNameToGetMethod(nodeName, "");
	}

	private static String convertNodeNameToMethodName(String nodeName, String suffix) {
		Assert.isTrue(StringUtil.isTrimNotEmpty(nodeName));
		String name = nodeName;
		String prifix = name.substring(0, 1);
		StringBuffer strBuff = new StringBuffer("set");
		strBuff.append(prifix.toUpperCase()).append(name.substring(1)).append(suffix);
		return strBuff.toString();
	}

	private static String convertNodeNameToGetMethod(String nodeName, String suffix) {
		Assert.isTrue(StringUtil.isTrimNotEmpty(nodeName));
		String name = nodeName;
		String prifix = name.substring(0, 1);
		StringBuffer strBuff = new StringBuffer("get");
		strBuff.append(prifix.toUpperCase()).append(name.substring(1)).append(suffix);
		return strBuff.toString();
	}

	public static Node getProcessNode(String objTypeName, Node paraNod, int index) {
		String[] names = objTypeName.split(",");
		for (int i = 0; i < names.length; i++) {
			Node paraNode = XMLManager.findChildNode(names[i], paraNod, index);
			if (paraNode != null)
				return paraNode;
		}
		return null;
	}

	private static int getRecords(JSONObject trxObj) {
		if (trxObj != null && trxObj.containsKey("_total_rows")) {
			return trxObj.getInt("_total_rows");
		}
		return 0;
	}

	private static JSONObject getTrxDom(JSONObject rows, int recodIndex) {
		String key = "rows" + recodIndex;
		if (rows.containsKey(key)) {
			return rows.getJSONObject(key);
		}
		return null;
	}
}
