package cn.com.jd.controller.util;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CompletionServiceTest {
	
	private static final ExecutorService pool = Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		CompletionService<String> completionServices = new ExecutorCompletionService<String>(pool);
		
		for(int i = 0; i< 5; i++){
			completionServices.submit(new Callable<String>() {
				
				public String call() throws Exception {
					Random r = new Random();
					int t  = r.nextInt(7);
					TimeUnit.SECONDS.sleep(t);
					return String.valueOf(t);
				}
			});
			
		}
		
		int count = 0,index = 1;
		
		while(count < 5){
			Future<String> f = completionServices.take();
			if(f == null){
				System.out.println("没有发现完成的任务");
			}else{
				System.out.println(index+"产生随机数"+f.get());
				count++;
			}
			index++;
			TimeUnit.MILLISECONDS.sleep(500);
		}
		

		
	}

}
