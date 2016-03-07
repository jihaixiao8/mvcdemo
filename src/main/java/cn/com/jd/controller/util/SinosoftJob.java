package cn.com.jd.controller.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SinosoftJob {
	
	/**����һ�� 100���̵߳��̳߳�  */
	private static ExecutorService excutor = Executors.newFixedThreadPool(100);
	
	public  void execute() {
		try{
			//1:���߳�������ļ� ����List
			List<Object> list = executeFile();
			//2:ѭ�����߳�ִ�в���
			for(final Object obj : list){
				excutor.execute(new Runnable() {
					
					public void run() {
						// ִ�����ݿ����
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
	 * ִ�н����ļ�����
	 * @return
	 */
	private List<Object> executeFile(){
		return null;
	}
	
	/**
	 * �˴���װ������ݿ����
	 * @return
	 */
	private boolean insertOrUpdateDB(Object obj){
		return false;
	}
	
}
