package cn.com.jd.test.concurrent;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class InterruptTaskQueueTest {
	
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<BigInteger> queue = new LinkedBlockingDeque<BigInteger>();
		PrimeProducer producer = new PrimeProducer(queue);
		producer.start();
		Thread.sleep(2000);
		PrimerCustomer customer = new PrimerCustomer(queue);
		customer.start();

//		try {
//			System.out.println(queue+"--"+queue.take());
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally{
//			producer.cancel();
//		}
	}
	
	
	static class PrimeProducer extends Thread{
		
		private final BlockingQueue<BigInteger> queue;
		
		public PrimeProducer(BlockingQueue<BigInteger> queue) {
			this.queue = queue;
		}
		
		public void run(){
			BigInteger p = BigInteger.ONE;
			while(!Thread.currentThread().isInterrupted()){
				try {
					queue.put(p = p.nextProbablePrime());
				} catch (InterruptedException e) {
					System.out.println("生产者任务线程中断"+Thread.currentThread().getState()+"--"+queue);
				}
			}
		}
		
		public void cancel(){
			interrupt();
		}		
	}
	
	static class PrimerCustomer extends Thread{
		
		private final BlockingQueue<BigInteger> queue;
		
		public PrimerCustomer(BlockingQueue<BigInteger> queue) {
			this.queue = queue;
		}
		
		public void run(){
			try {
				while(!queue.isEmpty()){
					BigInteger number = queue.take();
					System.out.println("获取Number："+number);
				}				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
