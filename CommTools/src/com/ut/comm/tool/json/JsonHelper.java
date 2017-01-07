package com.ut.comm.tool.json;

import net.sf.json.JSONObject;

import com.ut.comm.tool.DateTimeUtil;
import com.ut.comm.tool.string.StringUtil;

public class JsonHelper {
	private static final String JSON_NAME_TRX = "trxDom";
	private static final String JSON_NAME_FUNC = "funcInfo";
	private static final String JSON_NAME_USER = "userInfo";
	private static final String JSON_NAME_COMM = "comm";
	private static final String JSON_NAME_WORKFLOW = "workflowInfo";
	
	public static JSONObject getConnObject(JSONObject parent){
		if(parent.containsKey("_json_name")&&JSON_NAME_COMM.equalsIgnoreCase(parent.getString("_json_name"))){
			return parent;
		}
		return JsonUtil.getChildJson(parent,JSON_NAME_COMM);
	}
	
	public static JSONObject getUserObject(JSONObject parent){
		if(parent.containsKey("_json_name")&&JSON_NAME_USER.equalsIgnoreCase(parent.getString("_json_name"))){
			return parent;
		}
		return JsonUtil.getChildJson(parent,JSON_NAME_USER);
	}
	
	public static JSONObject getFuncObject(JSONObject parent){
		if(parent.containsKey("_json_name")&&JSON_NAME_FUNC.equalsIgnoreCase(parent.getString("_json_name"))){
			return parent;
		}
		return JsonUtil.getChildJson(parent,JSON_NAME_FUNC);
	}
	
	public static JSONObject getTrxObject(JSONObject parent){
		if(parent.containsKey("_json_name")&&JSON_NAME_TRX.equalsIgnoreCase(parent.getString("_json_name"))){
			return parent;
		}
		return JsonUtil.getChildJson(parent,JSON_NAME_TRX);
	}
	
	public static JSONObject getWorkflowObject(JSONObject parent){
		if(parent.containsKey("_json_name")&&JSON_NAME_WORKFLOW.equalsIgnoreCase(parent.getString("_json_name"))){
			return parent;
		}
		return JsonUtil.getChildJson(parent,JSON_NAME_WORKFLOW);
	}
	
	public static void setTrxObject(JSONObject parent,JSONObject trxObj){
		parent.put(JSON_NAME_TRX, trxObj);
	}
	
	public static JSONObject getTrxObject(JSONObject parent,String doName){
		JSONObject trxObj = getTrxObject(parent);
		if(StringUtil.isNull(doName))
			return trxObj;
		if(trxObj.containsKey(doName)){
			return JsonUtil.getChildJson(trxObj, doName);
		}
		return trxObj;
	}
	
	public static boolean hasWorkflowObject(JSONObject parent){
		return parent.containsKey(JSON_NAME_WORKFLOW);
	}
	
	public static JSONObject createReqJson(){
		JSONObject reqData  = JsonUtil.createJson(null);
		appendComm(reqData);
		appendUser(reqData);
		appendFunction(reqData);
		appendRequest(reqData);
		return reqData;
	}
	
	private static void appendComm(JSONObject reqDom){
		JSONObject commRoot = JsonUtil.createJson(JSON_NAME_COMM);
		
		commRoot.put("cnty", "CN");
		
		commRoot.put("bank", "BANK");
		
		commRoot.put("branch", "00001");
		
		commRoot.put("other", "test");
		JsonUtil.append(reqDom, commRoot);
	}
	
	private static void appendUser(JSONObject reqDom){
		JSONObject userRoot = JsonUtil.createJson(JSON_NAME_USER);
		userRoot.put("sysUserId", "system");
		userRoot.put("sysCurrentDate",DateTimeUtil.getTimestamp());
		userRoot.put("sysBusiUnit", "");
		
		JsonUtil.append(reqDom, userRoot);
	}
	
	private static void appendFunction(JSONObject reqDom){
		JSONObject funcRoot = JsonUtil.createJson(JSON_NAME_FUNC);
		funcRoot.put("sysFuncId","");
		funcRoot.put("name", "");
		funcRoot.put("funcType","");
		funcRoot.put("module","");
		funcRoot.put("sysEventTimes","");
		funcRoot.put("sysOrgnFuncId", "");
		funcRoot.put("sysRefNo","");
		JsonUtil.append(reqDom, funcRoot);
	}
	
	private static void appendRequest(JSONObject reqDom){
		JSONObject trxRoot = JsonUtil.createJson(JSON_NAME_TRX);
		trxRoot.put("sysRefNo","");
		JsonUtil.append(reqDom, trxRoot);
	}
	
	public static void mark2StpFunc(JSONObject reqDom,boolean isStpFunc){
		JSONObject funcObj = JsonHelper.getFuncObject(reqDom);
		funcObj.put("isStpFunc", isStpFunc);
	}
	
	public static boolean isStpFuncReq(JSONObject reqDom){
		JSONObject funcObj = JsonHelper.getFuncObject(reqDom);
		return funcObj.containsKey("isStpFunc")?funcObj.getBoolean("isStpFunc"):false;
	}
}
