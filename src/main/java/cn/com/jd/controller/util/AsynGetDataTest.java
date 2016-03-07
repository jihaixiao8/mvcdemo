package cn.com.jd.controller.util;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

/**
 * 异步获取节点数据
 * @author jihaixiao
 * @date 2016年1月4日 上午10:22:41
 */
public class AsynGetDataTest implements Watcher{
	
	private static CountDownLatch latch = new CountDownLatch(1);
	
	private static ZooKeeper zk = null;
	
	public static void main(String[] args) throws Exception{
		String path = "/zk-book-me";
		zk = new ZooKeeper("172.25.47.133:2181,172.25.47.134:2181,172.25.47.135:2181", 5000, new AsynGetDataTest());
		System.out.println(zk.getState());
		latch.await();
		System.out.println("connect success");
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.getData(path, true, new GetDataCallback(), null);
		zk.setData(path, "13".getBytes(), -1);
		Thread.sleep(Integer.MAX_VALUE);
	}

	public void process(WatchedEvent event) {
		if(KeeperState.SyncConnected == event.getState()){
			if(EventType.None == event.getType() && null == event.getPath()){
				latch.countDown();
			}else if(EventType.NodeDataChanged == event.getType()){
				zk.getData(event.getPath(), true, new GetDataCallback(), null);
			}
		}
		
	}

}

class GetDataCallback implements AsyncCallback.DataCallback{

	public void processResult(int rc, String path, Object ctx, byte[] data,
			Stat stat) {
		System.out.println(rc+","+path+","+new String(data));
		System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getCversion());
	}
	
}
