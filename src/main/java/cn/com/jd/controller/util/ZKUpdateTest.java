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

public class ZKUpdateTest implements Watcher{
	
	private static CountDownLatch latch = new CountDownLatch(1);
	
	private static ZooKeeper zk;
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException{
		String path = "/zk-book-haabcm";
		zk = new ZooKeeper("172.25.47.133:2181,172.25.47.134:2181,172.25.47.135:2181", 5000, new ZKUpdateTest());
		System.out.println(zk.getState());
		latch.await();
		System.out.println("connect success");
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.getData(path, true, null);
		Stat stat = zk.setData(path, "456".getBytes(),-1);
		System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
		Stat stat1 = zk.setData(path, "456".getBytes(),stat.getVersion());
		System.out.println(stat1.getCzxid()+","+stat1.getMzxid()+","+stat1.getVersion());
		try{
			Stat stat2 = zk.setData(path, "456".getBytes(), stat1.getVersion());
		}catch(KeeperException e){
			System.out.println(e.getCode()+","+e.getMessage() );
		}
		
		Thread.sleep(Integer.MAX_VALUE);
	}

	public void process(WatchedEvent event) {
		if(KeeperState.SyncConnected == event.getState()){
			if(EventType.None == event.getType() && null == event.getPath()){
				latch.countDown();
			}
		}
	}

}
