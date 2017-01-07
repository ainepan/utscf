package com.ut.comm.cache.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ut.comm.cache.ICacheServer;
import com.ut.comm.tool.consts.ASPathConst;

@Service("zkCacheServer")
public class ZooKeeperServerImpl extends ZooKeeperServerMain implements ICacheServer,Runnable{
	final ServerConfig config = new ServerConfig();
	private boolean isRunning = false;
	
	static ThreadLocal<ZooKeeperServerImpl> threadMap = new ThreadLocal<ZooKeeperServerImpl>();
	
	public static Logger logger = LoggerFactory.getLogger("zkLogger");
	
	@Override
	public void start() {
		ZooKeeperServerImpl mainThread = new ZooKeeperServerImpl();
		mainThread.initilise();
		
		Thread thread = new Thread(mainThread); 
		thread.start(); 
		threadMap.set(mainThread);
		ZooKeeperConnectionManager.initilize();
	}

	@Override
	public void shutdown() {
		ZooKeeperServerImpl myServer = threadMap.get();
		if(myServer!=null&&myServer.isRunning())
			myServer.destory();
		
		ZooKeeperConnectionManager.destory();
	}

	public void initilise() {
		try {
			InputStream is = new FileInputStream(new File (getConfig()));
			Properties props = new Properties();
			try {
				props.load(is);
			} finally {
				is.close();
			}

			QuorumPeerConfig quorumConfig = new QuorumPeerConfig();
			quorumConfig.parseProperties(props);

			config.readFrom(quorumConfig);
		} catch (IOException e) {
			logger.error("加载服务配置文件异常："+e.toString());
			e.printStackTrace();
		} catch (ConfigException e) {
			logger.error("配置服务异常："+e.toString());
			e.printStackTrace();
		}
	}

	public String getConfig() {
//		String rootPath = this.getClass().getResource("/").getPath();
//		String configFile = rootPath+"../"+"cache/zoo.cfg";
		String path = ASPathConst.getUserDirPath();
		String configFile =	path+File.separator+"syst"+File.separator+"zoo.cfg";
		return configFile;
	}

	public String getName() {
		return "zoo keeper";
	}

	@Override
	public void run() {
		try {
			super.runFromConfig(config);
			isRunning = true;
			logger.debug("Zoo Keeper Server start success. start linstening port:"+config.getClientPortAddress());
		} catch (IOException e) {
			logger.error("服务启动异常："+e.toString());
			e.printStackTrace();
		}
	}
	
	public void destory(){
		super.shutdown();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
