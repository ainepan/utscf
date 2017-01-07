package com.ut.comm.cache.impl;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ut.comm.tool.ErrorUtil;

public class ZooKeeperConnector implements Watcher{
	
	private Logger logger = LoggerFactory.getLogger("zkLogger");

	private static final int SESSION_TIMEOUT = 10000;
	private static final String CONNECTION_STRING = "127.0.0.1:2181";
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	private static Object zkLock = new Object();
	private int TRY_TIME = 3;
	
	private int state ;
	private ZooKeeper zk ;
	
	public final int connected = 0;
	public final int disconnected = 1;
	
	public final int using = 3;
	public final int free = 4;
	
	private int id = 0;
	
	public ZooKeeperConnector(){
		TRY_TIME = 3;
		state = free;
//		this.id = id;
	}
	
	public ZooKeeper getConnection() {
		try {
			connectedSemaphore = new CountDownLatch(1);
			ZooKeeper zk = new ZooKeeper(CONNECTION_STRING, SESSION_TIMEOUT, this);
			connectedSemaphore.await();
			if(States.CONNECTED==zk.getState()){
				logger.error(this.id+"真创建连接成功。。。");
			}else{
				logger.error(this.id+"假创建连接。。。");
				if(TRY_TIME<=0){
					logger.error(this.id+"超过假创建连接次数。。。");
					return null;
				}else{
					TRY_TIME--;
					return getConnection();
				}
			}
			this.state = connected;
			TRY_TIME = 3;
			this.id = zk.hashCode();
			return zk;
		} catch (InterruptedException e) {
			logger.error(this.id+"连接创建失败，发生 InterruptedException"+", exception:"+e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} catch (IOException e) {
			logger.error(this.id+"连接创建失败，发生 IOException"+", exception:"+e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		}finally{
		}
		return null;
	}
	
	
	public class ZooKeeperWatcher implements Watcher{
		private CountDownLatch countor;
		public ZooKeeperWatcher(CountDownLatch count){
			countor = count;
		}

		@Override
		public void process(WatchedEvent event) {
			logger.error("收到事件通知：" + event.getState());
			if (KeeperState.SyncConnected == event.getState()) {
				countor.countDown();
			}
			
			if (KeeperState.Disconnected == event.getState()) {
				logger.error("收到断开连接通知getPath：" + event.getPath());
				logger.error("收到断开连接通知getType：" + event.getType());
				logger.error("收到断开连接通知getWrapper：" + event.getWrapper());
			}
		}
		
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ZooKeeper getZk() {
		if(zk==null||state==disconnected||zk.getState() != States.CONNECTED)
			zk = getConnection();
		return zk;
	}
	
	 public boolean equals(Object obj) {
		 if(obj==null)
			 return false;
		 if(obj instanceof ZooKeeperConnector){
			 ZooKeeperConnector objZoo = (ZooKeeperConnector) obj;
			 return this.id == objZoo.getId();
		 }
	        return false;
	   }

	@Override
	public void process(WatchedEvent event) {
		logger.error(this.id+"收到事件通知：" + event.getState());
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
		
		if (KeeperState.Disconnected == event.getState()) {
			logger.error(this.id+"收到断开连接通知getPath：" + event.getPath());
			logger.error(this.id+"收到断开连接通知getType：" + event.getType());
			logger.error(this.id+"收到断开连接通知getWrapper：" + event.getWrapper());
		}		
	}
}
