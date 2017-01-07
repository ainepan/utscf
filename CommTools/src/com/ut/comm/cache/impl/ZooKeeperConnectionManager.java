package com.ut.comm.cache.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

public class ZooKeeperConnectionManager {

	// private static Map<String, ZooKeeper > zkPool = new
	// ConcurrentHashMap<String, ZooKeeper >();

	private static List<ZooKeeperConnector> zkPool = new ArrayList<ZooKeeperConnector>();
	private static List<ZooKeeper> zksPool = new ArrayList<ZooKeeper>();

	public static ZooKeeper getZK() {
		// for (ZooKeeperConnector zooKeeperConnector : zkPool) {
		// if(zooKeeperConnector.free == zooKeeperConnector.getState()){
		// zooKeeperConnector.setState(zooKeeperConnector.using);
		// ZooKeeper zk = null;
		// if(zooKeeperConnector.getId()!=0){
		// zk = zooKeeperConnector.getZk();
		// }else{
		// zk = zooKeeperConnector.getConnection();
		// }
		// if(zk!=null&&States.CONNECTED == zk.getState())
		// return zk;
		// }
		// }
		// ZooKeeperConnector connector = new ZooKeeperConnector();
		// zkPool.add(connector);
		// ZooKeeper zk = connector.getConnection();
		// if(zk!=null&States.CONNECTED == zk.getState())
		// return zk;
		// return null;
		synchronized (zksPool) {
			// for (ZooKeeper zk : zksPool) {
			// if(zk!=null&&States.CONNECTED == zk.getState())
			// return zk;
			// }
			if (!zksPool.isEmpty()) {
				ZooKeeper zk = zksPool.remove(0);
				if (zk != null && States.CONNECTED == zk.getState())
					return zk;
				else {
					if (zk != null) {
						try {
							zk.close();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					return getZK();
				}

			} else {
				ZooKeeperConnector connector = new ZooKeeperConnector();
				return connector.getConnection();
			}
		}

	}

	public static void freeZK(ZooKeeper zk) {
		// if(zk==null)
		// return ;
		// ZooKeeperConnector connector = new ZooKeeperConnector();
		// connector.setId(zk.hashCode());
		// int index = zkPool.indexOf(connector);
		// connector = zkPool.get(index);
		// connector.setState(connector.free);
		// zkPool.set(index, connector);
		synchronized (zksPool) {
			zksPool.add(zk);
		}
	}

	public static void initilize() {
//		ZooKeeperConnector connector = new ZooKeeperConnector();
		// zkPool.add(connector);
		// connector = new ZooKeeperConnector();
		// zkPool.add(connector);
		// connector = new ZooKeeperConnector();
		// zkPool.add(connector);
		// connector = new ZooKeeperConnector();
		// zkPool.add(connector);
		// connector = new ZooKeeperConnector();
		// zkPool.add(connector);
	}

	public static void destory() {
		for (ZooKeeperConnector zooKeeperConnector : zkPool) {
			try {
				zooKeeperConnector.getConnection().close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
