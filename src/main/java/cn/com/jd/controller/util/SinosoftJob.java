package cn.com.jd.controller.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SinosoftJob {
	
	/**创建一个 100个线程的线程池  */
	private static ExecutorService excutor = Executors.newFixedThreadPool(100);
	
	public  void execute() {
		try{
			//1:主线程里解析文件 返回List
			List<Object> list = executeFile();
			//2:循环多线程执行操作
			for(final Object obj : list){
				excutor.execute(new Runnable() {
					
					public void run() {
						// 执行数据库操作
						insertOrUpdateDB(obj);
					}
				});
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			excutor.shutdown();
		}
	}
	
	/**
	 * 执行解析文件操作
	 * @return
	 */
	private List<Object> executeFile(){
		return null;
	}
	
	/**
	 * 此处封装你的数据库操作
	 * @return
	 */
	private boolean insertOrUpdateDB(Object obj){
		return false;
	}
	
}
