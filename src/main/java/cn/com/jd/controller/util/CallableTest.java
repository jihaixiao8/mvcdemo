package cn.com.jd.controller.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableTest {
	
	private static final ExecutorService executors = Executors.newFixedThreadPool(3);
	
	public static void main(String[] args) {
		
		Future<String> future = executors.submit(new GetWordCall());
		System.out.println("一起进行");
		try {
			String result = future.get();
			System.out.println(result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			future.cancel(true);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private static class GetWordCall implements Callable<String>{

		public String call() throws Exception {
			// TODO Auto-generated method stub
			return "haha";
		}
		
	}
	
}

