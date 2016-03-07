package cn.com.jd.controller.util;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;


public class ZkChildrenTest implements Watcher{
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	private static ZooKeeper zk = null;
	
	
	public static void main(String[] args) {
		try {
			String path = "/zk-book1";
			zk = new ZooKeeper("172.25.47.133:2181,172.25.47.134:2181,172.25.47.135:2181", 5000, new ZkChildrenTest());
			System.out.println(zk.getState());
			connectedSemaphore.await();
			System.out.println("connect success");
			
			zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			zk.create(path+"/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			List<String> childrenList = zk.getChildren(path, true);
			System.out.println(childrenList);
			zk.create(path+"/c2", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			Thread.sleep(Integer.MAX_VALUE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void process(WatchedEvent event) {
		System.out.println("Receive watched event :"+event);
		if(KeeperState.SyncConnected == event.getState()){			
			if(EventType.None == event.getType() && event.getPath() == null){
				connectedSemaphore.countDown();
			}else if(event.getType() == EventType.NodeChildrenChanged){
				try {
					System.out.println("Reget:"+zk.getChildren(event.getPath(), true));
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
