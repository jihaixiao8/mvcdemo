package cn.com.jd.controller.util;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 分布式锁
 * @author jihaixiao
 * @date 2015年12月31日 下午4:54:06
 */
public class ZookeeperLock implements Lock,Watcher{
	
	//zk服务器地址
	private static String server = "172.25.47.133:2181,172.25.47.134:2181,172.25.47.135:2181";
	
	private static String groupNode  = "/server";
	
	private static String subgroupNode = "/groupNode";
	
	//客户端与zk服务端会话超时时间
	private static int sessionTimeout = 30000;
	
	//zookeeper连接
	private ZooKeeper zk;
	
	//锁节点
	private  String lockNode;
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	
	public ZookeeperLock(String lockName) throws Exception{
		//构造锁路径
		this.lockNode = groupNode + subgroupNode +"/" + lockName;
		init();
	}
	
	private void init() throws Exception{
		zk = new ZooKeeper(server, sessionTimeout, this);
		latch.await();
		
		if(null == zk.exists(groupNode, false)){
			zk.create(groupNode, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		if(null == zk.exists(groupNode+subgroupNode, false)){
			zk.create(groupNode+subgroupNode, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
	}
	
	

	public void process(WatchedEvent event) {
		KeeperState state = event.getState();
		//成功建立连接
		if(KeeperState.SyncConnected == state){
			System.out.println("connect zookeeper server successful");
			latch.countDown();
		}
		//若节点被删除，则取消阻塞
		if(EventType.NodeDeleted == event.getType() && event.getPath().equals(lockNode)){
			latch.countDown();
		}
	}

	public void lock() {
		if(this.tryLock()) return;
		throw new RuntimeException("获取锁失败,lockNode= "+lockNode);
	}

	public void lockInterruptibly() throws InterruptedException {
		this.lock();
	}

	public boolean tryLock() {
		try {
			zk.create(lockNode, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("获取锁异常");
			return false;
		} 
		
	}

	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	public void unlock() {
		System.out.println("开始删除锁 lockNode = "+lockNode);
		try {
			zk.delete(lockNode, -1);
			System.out.println("删除锁成功");
		} catch (Exception e) {
			System.out.println("删除锁异常");
		} 
		try {
			zk.close();
		} catch (InterruptedException e) {
			System.out.println("zk 连接关闭失败");
		}
	}

	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
