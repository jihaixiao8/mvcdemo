package cn.com.jd.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {
	public static void main(String[] args) {
		Task task = new Task();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
		Thread t = new Thread(futureTask);
		t.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("���߳�����ִ������");
		
		try {
			System.out.println("task���н����"+futureTask.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("��������ִ�����");
	}
}

class MyTask implements Callable<Integer>{

	public Integer call() throws Exception {
		System.out.println("���߳����ڼ���");
		Thread.sleep(3000);
		int sum = 0;
		for(int i=1;i<100;i++){
			sum += i;
		}
		return sum;
	}
	
}
