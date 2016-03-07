package cn.com.jd.test.concurrent;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @description 这个例子描述了生产者线程生产的频率大于消费者消费频率时，使用
 * 取消标志位来中断任务不靠谱，一旦队列满了，put方法将被阻塞，即使标志位修改掉，依旧
 * 走不到那儿了，而导致线程一直阻塞下去
 * @author jihaixiao
 * @date 2016年3月2日 下午4:59:54
 */
public class TaskQueueTest {
	
	public static void main(String[] args) {
		BlockingQueue<BigInteger> queue = new LinkedBlockingDeque<BigInteger>(10);
		PrimeProducer producer = new PrimeProducer(queue);
		producer.start();
		try {
			System.out.println(queue.take());
//			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			producer.cancel();
		}
	}
	
	
	static class PrimeProducer extends Thread{
		
		private final BlockingQueue<BigInteger> queue;
		
		private volatile boolean cancelled = false;
		
		public PrimeProducer(BlockingQueue<BigInteger> queue) {
			this.queue = queue;
		}
		
		public void run(){
			BigInteger p = BigInteger.ONE;
			while(!cancelled){
				try {
					queue.put(p = p.nextProbablePrime());
					System.out.println("在此位置是否阻塞");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public void cancel(){
			cancelled = true;
		}		
	}
}
