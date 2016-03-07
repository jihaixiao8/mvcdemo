package cn.com.jd.controller.util;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.swing.text.DefaultEditorKit.CutAction;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperTest implements Watcher{
	
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	public static void main(String[] args) throws KeeperException {
		
		try {
			ZooKeeper zookeeper = new ZooKeeper("172.25.47.133:2181,172.25.47.134:2181,172.25.47.135:2181", 5000, new ZookeeperTest());
			System.out.println(zookeeper.getState());
			connectedSemaphore.await();
			System.out.println("connect success");
			System.out.println("success create node : ");
			String path2 = zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			System.out.println("success create node : "+path2);
			
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
