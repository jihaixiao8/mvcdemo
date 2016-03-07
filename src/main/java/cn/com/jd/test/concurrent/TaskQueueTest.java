package cn.com.jd.test.concurrent;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @description ��������������������߳�������Ƶ�ʴ�������������Ƶ��ʱ��ʹ��
 * ȡ����־λ���ж����񲻿��ף�һ���������ˣ�put����������������ʹ��־λ�޸ĵ�������
 * �߲����Ƕ��ˣ��������߳�һֱ������ȥ
 * @author jihaixiao
 * @date 2016��3��2�� ����4:59:54
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
					System.out.println("�ڴ�λ���Ƿ�����");
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
