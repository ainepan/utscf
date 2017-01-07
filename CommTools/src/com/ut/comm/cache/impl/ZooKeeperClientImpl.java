package com.ut.comm.cache.impl;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.ConnectionLossException;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ut.comm.cache.ICSCacheClient;
import com.ut.comm.tool.ErrorUtil;
import com.ut.comm.tool.SerializeUtil;

@Service("zkCache")
public class ZooKeeperClientImpl implements ICSCacheClient, Watcher {
	private Logger logger = LoggerFactory.getLogger("zkLogger");

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	private boolean checkInstence(ZooKeeper zk) {
		if (zk == null || States.CONNECTED != zk.getState()) {
			return false;
		}
		return true;
	}

	@Override
	public Object getData(String key) {
		ZooKeeper zk = ZooKeeperConnectionManager.getZK();
		if (!checkInstence(zk)){
			ZooKeeperConnectionManager.freeZK(zk);
			zk = ZooKeeperConnectionManager.getZK();
		}
			
		if (!key.startsWith("/")) {
			key = "/" + key;
		}

		try {
			if (existData(key, zk)) {
				Serializable so = (Serializable) SerializeUtil.unSerialize(zk.getData(key, false, null));
				logger.error("获取数据成功，path：" + key);
				return so;
			} else {
				logger.error("尝试获取不存在path：" + key);
				if(checkInstence(zk)){
					//真的没有节点
					logger.error("真不存在path：" + key);
					return null;
					
				}else{
					//由于zk连接问题导致没有取到数据，需要重新取
					logger.error("假不存在path：" + key);
					return getData(key);
				}
			}
		} catch (KeeperException e) {
			logger.error("读取数据失败，发生KeeperException，path: " + key + ", exception:" + e.toString());
			// createData(key, null);
			logger.error(ErrorUtil.getErrorStackTrace(e));
			if(e instanceof ConnectionLossException){
				//zk连接失败导致的异常，重新读取数据
				return getData(key);
			}else{
				//其他情况，返回空值
				return null;
			}
		} catch (InterruptedException e) {
			logger.error("读取数据失败，发生 InterruptedException，path: " + key + ", exception:" + e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
			return null;
		} finally {
			ZooKeeperConnectionManager.freeZK(zk);
		}

	}

	@Override
	public void createData(String key, Object data){
		ZooKeeper zk = ZooKeeperConnectionManager.getZK();
		if (!checkInstence(zk)) {
			ZooKeeperConnectionManager.freeZK(zk);
			zk = ZooKeeperConnectionManager.getZK();
		}
		if (!key.startsWith("/")) {
			key = "/" + key;
		}
		long times = System.currentTimeMillis();
		try {
			if (!existData(key, zk)) {
				logger.error("节点创建成功, Path: " + zk.create(key, //
						SerializeUtil.serialize(data), //
						Ids.OPEN_ACL_UNSAFE, //
						CreateMode.PERSISTENT) + ", content: ..." );

			} else {
				updateData(key, data, zk);
			}
		} catch (KeeperException e) {
			logger.error("节点创建失败，发生KeeperException：" + e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
			if(e instanceof NoNodeException){
//				throw e;
			}
		} catch (InterruptedException e) {
			logger.error("节点创建失败，发生 InterruptedException：" + e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} finally {
			long end = System.currentTimeMillis() - times;
			if (end > 1000) {
				logger.error("createData use time :" + end);
				Exception e = new Exception();
				logger.error(ErrorUtil.getErrorStackTrace(e));
			}
		}
		ZooKeeperConnectionManager.freeZK(zk);
	}
	
	public void createData(String key, Object data, ZooKeeper zk) throws Exception {
		if (!checkInstence(zk)) {
			ZooKeeperConnectionManager.freeZK(zk);
			zk = ZooKeeperConnectionManager.getZK();
		}
		if (!key.startsWith("/")) {
			key = "/" + key;
		}
		long times = System.currentTimeMillis();
		try {
			if (!existData(key, zk)) {
				logger.error("节点创建成功, Path: " + zk.create(key, //
						SerializeUtil.serialize(data), //
						Ids.OPEN_ACL_UNSAFE, //
						CreateMode.PERSISTENT) + ", content: ...");
			} else {
				updateData(key, data, zk);
			}
		} catch (KeeperException e) {
			logger.error("节点创建失败，发生KeeperException：" + e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} catch (InterruptedException e) {
			logger.error("节点创建失败，发生 InterruptedException：" + e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} finally {
			long end = System.currentTimeMillis() - times;
			if (end > 1000) {
				logger.error("createData use time :" + end);
				Exception e = new Exception();
				logger.error(ErrorUtil.getErrorStackTrace(e));
			}
		}
	}

	@Override
	public void updateData(String key, Object data) {
		ZooKeeper zk = ZooKeeperConnectionManager.getZK();
		updateData(key,data,zk);
		ZooKeeperConnectionManager.freeZK(zk);
	}
	
	public void updateData(String key, Object data,ZooKeeper zk) {
		if (!checkInstence(zk)){
			ZooKeeperConnectionManager.freeZK(zk);
			zk = ZooKeeperConnectionManager.getZK();
		}
		if (!key.startsWith("/")) {
			key = "/" + key;
		}
		if (!key.startsWith("/")) {
			key = "/" + key;
		}

		try {
			if (!existData(key,zk)) {
				createData(key,data);
			}else{
				long times  = System.currentTimeMillis();
				logger.debug("更新数据成功，path：" + key + ", stat: " + zk.setData(key, SerializeUtil.serialize(data), -1));
				long end = System.currentTimeMillis()-times;
				if(end>1000){
					logger.error("updateData use time :"+end);
					Exception e = new Exception();
					logger.error(ErrorUtil.getErrorStackTrace(e));
				}
			}
		} catch (KeeperException e) {
			logger.error("更新数据失败，发生KeeperException，path: " + key+", exception:"+e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} catch (InterruptedException e) {
			logger.error("更新数据失败，发生 InterruptedException，path: " + key+", exception:"+e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		}finally{
		}
	}

	@Override
	public void removeData(String key) {
		ZooKeeper zk = ZooKeeperConnectionManager.getZK();
		removeData(key,zk);
		ZooKeeperConnectionManager.freeZK(zk);
	}
	
	public void removeData(String key,ZooKeeper zk) {
		if (!key.startsWith("/")) {
			key = "/" + key;
		}

		if (!checkInstence(zk)){
			ZooKeeperConnectionManager.freeZK(zk);
			zk = ZooKeeperConnectionManager.getZK();
		}
		try {
			if (existData(key,zk)) {
				long times = System.currentTimeMillis();
				zk.delete(key, -1);
				logger.debug("删除数据节点成功，path：" + key);
				long end = System.currentTimeMillis()-times;
				if(end>1000){
					logger.error("removeData use time :"+end);
					Exception e = new Exception();
					logger.error(ErrorUtil.getErrorStackTrace(e));
				}
			}else{
				logger.debug("删除不存在删除数据节点，path：" + key);
			}
		} catch (KeeperException e) {
			logger.error("删除数据失败，发生KeeperException，path: " + key+", exception:"+e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} catch (InterruptedException e) {
			logger.error("删除数据失败，发生 InterruptedException，path: " + key+", exception:"+e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		}finally{
		}
	}

	public void deleteNode(String key,ZooKeeper zk) {
		try {
			if (existData(key,zk)) {
				List<String> childList = zk.getChildren(key, false);
				if (!childList.isEmpty()) {
					for (String childNode : childList) {
						deleteNode(key + "/" + childNode,zk);
					}
				}
				removeData(key,zk);
			}
		} catch (KeeperException e) {
			logger.error("删除节点失败，发生KeeperException，path: " + key+", exception:"+e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} catch (InterruptedException e) {
			logger.error("删除节点失败，发生 InterruptedException，path: " + key+", exception:"+e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}

	}

	@Override
	public void createConnection() {
//		try {
//			connectedSemaphore = new CountDownLatch(1);
//			zk = new ZooKeeper(CONNECTION_STRING, SESSION_TIMEOUT, this);
//			connectedSemaphore.await();
//			if(States.CONNECTED==zk.getState()){
//				logger.error("真创建连接成功。。。");
//			}else{
//				logger.error("假创建连接成功。。。");
//				connectedSemaphore = new CountDownLatch(1);
//				zk = new ZooKeeper(CONNECTION_STRING, SESSION_TIMEOUT, this);
//				connectedSemaphore.await();
//			}
//		} catch (InterruptedException e) {
//			logger.error("连接创建失败，发生 InterruptedException"+", exception:"+e.toString());
//			logger.error(ErrorUtil.getErrorStackTrace(e));
//		} catch (IOException e) {
//			logger.error("连接创建失败，发生 IOException"+", exception:"+e.toString());
//			logger.error(ErrorUtil.getErrorStackTrace(e));
//		}finally{
//		}
	}

	@Override
	public void connect() {
//		synchronized (zkLock) {
//			if (zk == null) {
//				createConnection();
//			}
//		}
	}

	@Override
	public void close() {
//		synchronized (zkLock) {
//			if (zk != null) {
//				try {
//					zk.close();
//					Exception e = new Exception();
//					logger.error(ErrorUtil.getErrorStackTrace(e));
//					logger.error("连接已关闭");
//				} catch (InterruptedException e) {
//					logger.error(ErrorUtil.getErrorStackTrace(e));
//				} finally {
//					zk = null;
//				}
//			}
//		}
//		ZooKeeperConnectionManager.destory();
	}

	@Override
	public Object getConnection() {
		return null;
	}

	@Override
	public boolean existData(String key) {
		ZooKeeper zk = ZooKeeperConnectionManager.getZK();
		if (!checkInstence(zk)){
			ZooKeeperConnectionManager.freeZK(zk);
			zk = ZooKeeperConnectionManager.getZK();
		}
		if (!key.startsWith("/")) {
			key = "/" + key;
		}
		long times = System.currentTimeMillis();
		try {
			return zk.exists(key, false) != null;
		} catch (KeeperException e) {
			//close();
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} catch (InterruptedException e) {
			logger.error(ErrorUtil.getErrorStackTrace(e));
		}finally{
			long end = System.currentTimeMillis()-times;
			if(end>1000){
				logger.error("existData use time :"+end);
				Exception e = new Exception();
				logger.error(ErrorUtil.getErrorStackTrace(e));
			}
			ZooKeeperConnectionManager.freeZK(zk);
		}
		return false;
	}
	
	public boolean existData(String key,ZooKeeper zk) throws InterruptedException, KeeperException {
		Stat stat = zk.exists(key, false);
		logger.error("ExistData Stat:"+stat);
		return stat!=null;
	}

	/**
	 * 监听服务器同步事件
	 */
	@Override
	public void process(WatchedEvent event) {
		logger.error("收到事件通知：" + event.getState());
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
		if (KeeperState.Disconnected == event.getState()) {
//			zk = null;
			logger.error("收到断开连接通知getPath：" + event.getPath());
			logger.error("收到断开连接通知getType：" + event.getType());
			logger.error("收到断开连接通知getWrapper：" + event.getWrapper());
		}
	}

	@Override
	public void removeDataContainsChild(String key) {
		ZooKeeper zk = ZooKeeperConnectionManager.getZK();
		if (!checkInstence(zk)){
			ZooKeeperConnectionManager.freeZK(zk);
			zk = ZooKeeperConnectionManager.getZK();
		}
		if (!key.startsWith("/")) {
			key = "/" + key;
		}
		deleteNode(key,zk);
		ZooKeeperConnectionManager.freeZK(zk);
	}

	@Override
	public List<String> getChildKeys(String key) {
		ZooKeeper zk = ZooKeeperConnectionManager.getZK();
		if (!checkInstence(zk)){
			ZooKeeperConnectionManager.freeZK(zk);
			zk = ZooKeeperConnectionManager.getZK();
		}
		try {
			List<String> childList = zk.getChildren(key, false);
			logger.debug("获取子节点成功，path：" + key);
			return childList;
		} catch (KeeperException e) {
			logger.error("获取子节点失败，发生KeeperException，path: " + key+", exception:"+e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		} catch (InterruptedException e) {
			logger.error("获取子节点失败，发生 InterruptedException，path: " + key+", exception:"+e.toString());
			logger.error(ErrorUtil.getErrorStackTrace(e));
		}
		finally{
			ZooKeeperConnectionManager.freeZK(zk);
		}
		return null;
	}
}
