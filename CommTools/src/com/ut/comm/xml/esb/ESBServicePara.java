package com.ut.comm.xml.esb;

import com.ut.comm.xml.AbsObject;
import com.ut.comm.xml.XMLParaHelper;

public class ESBServicePara extends AbsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeName() {
		return XMLParaHelper.NOTE_NAME_ESB_SERVICE;
	}

	private String name;
	/**
	 * 对内开放的服务类型，消费者凭此类型调用服务
	 * 
	 * @return
	 */
	private String type;
	/**
	 * RMI : ip+port WS: url+method MQ: jndi Class : className
	 * 
	 * @return
	 */
	private String protocol;
	private String postadapter;
	private String queryadapter;
	private String initlize;
	/**
	 * 协议类型
	 * 
	 * RMI WS MQ Class
	 * 
	 * @return
	 */
	private String protocoltp;
	private String address;
	private String port;
	private String method;
	private String inorout;
	private String characterset;
	private String timeout;
	private String recvmapping;
	private String user;
	private String pwd;
	private String homeDir;
	private String passsivePort;

	private String interfaceName;
	private String className;
	private String wsServiceName;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getWsServiceName() {
		return wsServiceName;
	}

	public void setWsServiceName(String wsServiceName) {
		this.wsServiceName = wsServiceName;
	}

	/**
	 * ftp用户名
	 * 
	 * @return
	 */
	public String getUser() {
		return user;
	}

	/**
	 * ftp用户名
	 * 
	 * @return
	 */
	public void setUser(String ftpUser) {
		this.user = ftpUser;
	}

	/**
	 * ftp密码
	 * 
	 * @return
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * ftp密码
	 * 
	 * @return
	 */
	public void setPwd(String ftpPwd) {
		this.pwd = ftpPwd;
	}

	/**
	 * ftp基础路径，仅服务端使用
	 * 
	 * @return
	 */
	public String getHomeDir() {
		return homeDir;
	}

	/**
	 * ftp基础路径，仅服务端使用
	 * 
	 * @return
	 */
	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}

	/**
	 * ftp被动传输端口，仅服务端且有防火墙端口限制时使用
	 * 
	 * @return
	 */
	public String getPasssivePort() {
		return passsivePort;
	}

	/**
	 * ftp被动传输端口，仅服务端且有防火墙端口限制时使用
	 * 
	 * @return
	 */
	public void setPasssivePort(String passsivePort) {
		this.passsivePort = passsivePort;
	}

	/**
	 * ESB中以此为key
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * RMI WS MQ Class
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * RMI : ip+port WS: url+method MQ: jndi Class : className
	 * 
	 * @return
	 */
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getInitlize() {
		return initlize;
	}

	public void setInitlize(String initlize) {
		this.initlize = initlize;
	}

	/**
	 * 服务对应的post adapter类名
	 * 
	 * @return
	 */

	public String getPostadapter() {
		return postadapter;
	}

	public void setPostadapter(String postadapter) {
		this.postadapter = postadapter;
	}

	/**
	 * 服务对应的query adapter类名
	 * 
	 * @return
	 */

	public String getQueryadapter() {
		return queryadapter;
	}

	public void setQueryadapter(String queryadapter) {
		this.queryadapter = queryadapter;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (obj instanceof ESBServicePara) {
			return (this.getId().equalsIgnoreCase(((ESBServicePara) obj).getId()));
		} else {
			return false;
		}
	}

	public String getProtocoltp() {
		return protocoltp;
	}

	public void setProtocoltp(String protocoltp) {
		this.protocoltp = protocoltp;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getInorout() {
		return inorout;
	}

	public void setInorout(String inorout) {
		this.inorout = inorout;
	}

	public String getCharacterset() {
		return characterset;
	}

	public void setCharacterset(String characterset) {
		this.characterset = characterset;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getRecvmapping() {
		return recvmapping;
	}

	public void setRecvmapping(String recvmapping) {
		this.recvmapping = recvmapping;
	}
	
	public String getWsUser() {
		return wsUser;
	}

	public void setWsUser(String wsUser) {
		this.wsUser = wsUser;
	}

	public String getWsPwd() {
		return wsPwd;
	}

	public void setWsPwd(String wsPwd) {
		this.wsPwd = wsPwd;
	}

	public String getWsInterceptorClass() {
		return wsInterceptorClass;
	}

	public void setWsInterceptorClass(String wsInterceptorClass) {
		this.wsInterceptorClass = wsInterceptorClass;
	}

	private String wsUser;
	private String wsPwd;
	private String wsInterceptorClass;
	
	public boolean isPropertyBean(){
		return true;
	}
}
