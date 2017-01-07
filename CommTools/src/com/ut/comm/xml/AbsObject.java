package com.ut.comm.xml;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author PanHao
 * @see 优点说明
 *      逐层解析参数文件，转化为对应的Object对象，对下一层对象，只有在使用的时候才解析，每次解析只解析当前层下的子节点，子节点的子节点不解析。
 *      优化点考虑 1 每个对象都拥有一个node对象，是否太消耗性能 2 对象太多，是否太消耗性能 3
 *      特殊复杂的参数文件，解析起来会很复杂，考虑是否将基本数据类型，List等，转化到框架中解析，不用为List类型数据建新的对象。
 *      
 * @see 优化说明2
 * 		将父类完全抽象成标准的POJO，解析方法抽象到工具类中：
 * 		1 支持pojo直接传到前台使用，不许额外新建POJO
 * 		2 支持回写方法，形成解析-生成 工具方法，支持参数工具开发
 * 		3 不需要每个子类持有Node属性，节省开销
 * 		4 不再是逐层解析，而是单文件解析，每次解析一个完整文件，与写文件保持同步
 *		5 要求是有节点的名称唯一，如Node分为query node和logic node，但是两个参数实现对象不同，所有将节点名改成qnode和lnode
 */
public abstract class AbsObject implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract String getNodeName();
	
	public boolean isPropertyBean(){
		return false;
	}
	
	private String id;
	
	private String bu;
	
	public AbsObject() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}
	
	public AbsObject clone() throws CloneNotSupportedException {
		return (AbsObject) super.clone();
	}
	
	Map<String,Object> proterties ;

	public Object getProterty(String propertyKey) {
		if(isPropertyBean()&&hasProperty(propertyKey)){
			return proterties.get(propertyKey);
		}
		return null;
	}

	public void setProterty(String propertyKey,Object propertyValue) {
		if(isPropertyBean()){
			if(proterties==null){
				this.proterties = new HashMap<String, Object>();
			}
			this.proterties.put(propertyKey, propertyValue);
		}
	}
	
	public boolean hasProperty(String propertyKey){
		if(isPropertyBean()){
			if(proterties==null){
				return false;
			}
			this.proterties.containsKey(propertyKey);
		}
		return false;
	}

	public Map<String, Object> getProterties() {
		return proterties;
	}
}
