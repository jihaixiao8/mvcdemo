package cn.com.jd.controller.util;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class AyscZNodeTest implements Watcher{
	
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	public static void main(String[] args) {
		try {
			ZooKeeper zookeeper = new ZooKeeper("172.25.47.133:2181,172.25.47.134:2181,172.25.47.135:2181", 5000, new AyscZNodeTest());
			System.out.println(zookeeper.getState());
			connectedSemaphore.await();
			System.out.println("connect success");
			zookeeper.create("/zk-test-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new MyStringCallback(), "I am context");
			zookeeper.create("/zk-test-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new MyStringCallback(), "I am context");
			zookeeper.create("/zk-test-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new MyStringCallback(), "I am context");
			Thread.sleep(Integer.MAX_VALUE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void process(WatchedEvent event) {
		System.out.println("Receive watched event :"+event);
		if(KeeperState.SyncConnected == event.getState()){
			connectedSemaphore.countDown();
		}
	}
	
}

	class MyStringCallback implements AsyncCallback.StringCallback{

		public void processResult(int rc, String path, Object ctx, String name) {
			System.out.println("create result: +["+rc+", "+path+", "+ctx+", real path name "+name+" ]");
		}
		
	}
