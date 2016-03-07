package cn.com.jd.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		Task task = new Task();
		Future<Integer> future = executor.submit(task);
		executor.shutdown();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("���߳�����ִ������");
		
		try {
			System.out.println("task���н����"+future.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class Task implements Callable<Integer>{

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
