package cn.com.jd.controller.util;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 节点是否存在
 * @author jihaixiao
 * @date 2016年1月5日 上午9:43:58
 */
public class NodeExistsTest implements Watcher{
	
	private static CountDownLatch latch = new CountDownLatch(1);
	
	private static ZooKeeper zk;
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException{
		String path = "/rpns";
		zk = new ZooKeeper("172.25.47.133:2181,172.25.47.134:2181,172.25.47.135:2181", 5000, new NodeExistsTest());
		System.out.println(zk.getState());
		latch.await();
		System.out.println("connect success");
		
		zk.exists(path, true);
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.setData(path, "456".getBytes(), -1);
		zk.create(path+"/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.delete(path+"/c1", -1);
		zk.delete(path, -1);
		
		Thread.sleep(Integer.MAX_VALUE);
	}

	public void process(WatchedEvent event) {
		try{
			if(KeeperState.SyncConnected == event.getState()){
				if(EventType.None == event.getType() && null == event.getPath()){
					latch.countDown();
				}else if(EventType.NodeCreated == event.getType()){
					System.out.println("(Node : "+event.getPath()+"create )");
					zk.exists(event.getPath(), true);
				}else if(EventType.NodeDeleted == event.getType()){
					System.out.println("(Node : "+event.getPath()+"delete )");
					zk.exists(event.getPath(), true);
				}else if(EventType.NodeDataChanged== event.getType()){
					System.out.println("(Node : "+event.getPath()+"change )");
					zk.exists(event.getPath(), true);
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}

	}

}
