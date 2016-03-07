package cn.com.jd.controller.util;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 同步获取节点数据
 * @author jihaixiao
 * @date 2016年1月4日 上午8:56:07
 */
public class ZKGetDataTest implements Watcher{
	
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	private static ZooKeeper zk = null;
	
	private static Stat stat = new Stat();
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		String path = "/zk-book-haab";
		zk = new ZooKeeper("172.25.47.133:2181,172.25.47.134:2181,172.25.47.135:2181", 5000, new ZKGetDataTest());
		System.out.println(zk.getState());
		connectedSemaphore.await();
		System.out.println("connect success");
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println(new String(zk.getData(path, true, stat)));
		System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getAversion());
		zk.setData(path, "1234".getBytes(), -1);
		Thread.sleep(Integer.MAX_VALUE);
	}

	public void process(WatchedEvent event) {
		if(KeeperState.SyncConnected == event.getState()){
			
			if(EventType.None == event.getType() && null == event.getPath()){
				connectedSemaphore.countDown();
			} else if(event.getType() == EventType.NodeDataChanged){
				try {
					System.out.println(new String(zk.getData(event.getPath(), true, stat)));
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getAversion());
			}
		}
		
	}

}
