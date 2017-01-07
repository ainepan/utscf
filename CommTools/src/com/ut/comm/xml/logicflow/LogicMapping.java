package com.ut.comm.xml.logicflow;

import java.util.HashMap;
import java.util.Map;

import com.ut.comm.xml.AbsObject;

@Deprecated
public class LogicMapping extends AbsObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String,String> propreties = new HashMap<String, String>();
	
	public String getProperty(String key){
		return propreties.get(key);
	}
	
	public boolean containsKey(String key){
		return propreties.containsKey(key);
	}
	public Map<String, String> getMappings() {
		return propreties;
	}
	@Override
	public String getNodeName() {
		return  "mapping";
	}
	
}
