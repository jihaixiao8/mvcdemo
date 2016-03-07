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
 * �ֲ�ʽ��
 * @author jihaixiao
 * @date 2015��12��31�� ����4:54:06
 */
public class ZookeeperLock implements Lock,Watcher{
	
	//zk��������ַ
	private static String server = "172.25.47.133:2181,172.25.47.134:2181,172.25.47.135:2181";
	
	private static String groupNode  = "/server";
	
	private static String subgroupNode = "/groupNode";
	
	//�ͻ�����zk����˻Ự��ʱʱ��
	private static int sessionTimeout = 30000;
	
	//zookeeper����
	private ZooKeeper zk;
	
	//���ڵ�
	private  String lockNode;
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	
	public ZookeeperLock(String lockName) throws Exception{
		//������·��
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
		//�ɹ���������
		if(KeeperState.SyncConnected == state){
			System.out.println("connect zookeeper server successful");
			latch.countDown();
		}
		//���ڵ㱻ɾ������ȡ������
		if(EventType.NodeDeleted == event.getType() && event.getPath().equals(lockNode)){
			latch.countDown();
		}
	}

	public void lock() {
		if(this.tryLock()) return;
		throw new RuntimeException("��ȡ��ʧ��,lockNode= "+lockNode);
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
			System.out.println("��ȡ���쳣");
			return false;
		} 
		
	}

	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	public void unlock() {
		System.out.println("��ʼɾ���� lockNode = "+lockNode);
		try {
			zk.delete(lockNode, -1);
			System.out.println("ɾ�����ɹ�");
		} catch (Exception e) {
			System.out.println("ɾ�����쳣");
		} 
		try {
			zk.close();
		} catch (InterruptedException e) {
			System.out.println("zk ���ӹر�ʧ��");
		}
	}

	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
