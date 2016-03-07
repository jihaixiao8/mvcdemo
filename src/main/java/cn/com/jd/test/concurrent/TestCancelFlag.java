package cn.com.jd.test.concurrent;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * �˴���volatile���׿ɲ��ɿ�����Ҫ���
 * @author jihaixiao
 * @date 2016��3��2�� ����4:22:42
 */
public class TestCancelFlag {
	
	public static void main(String[] args) {
		RandomGenerator generator = new RandomGenerator();
		for(int i =0;i<10;i++){
			Thread t = new Thread(generator);
			t.start();
			System.out.println(t.getName()+"--"+t.getState()+"����");
		}		
		try{
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			generator.cancel();
		}
		List<BigInteger> list  = generator.get();
		System.out.println(list.size());
	}
	
}

class RandomGenerator implements Runnable{
	
	private final List<BigInteger> numbers = new ArrayList<BigInteger>();
	
	private volatile boolean cancelled;

	public void run() {
		BigInteger p = BigInteger.ONE;
		while(!cancelled){
			p = p.nextProbablePrime();
			synchronized (this) {
				numbers.add(p);
			}
		}
		System.out.println(Thread.currentThread().getName()+"--"+numbers.size());
	}
	
	public void cancel(){
		cancelled = true;
		System.out.println("������ȡ��");
	}
	
	public synchronized List<BigInteger> get(){
		return new ArrayList<BigInteger>(numbers);
	}
	
}
