package cn.com.jd.innerclass;
 
public class Sequence {
	
	private Object[] items;
	
	private int next = 0;
	
	public Sequence(int size){
		items = new Object[size];
	}
	
	public void add(Object o){
		if(next < items.length){
			items[next++] = o;
		}
	}
	
	private static class A{
		static int m = 0;
	}
	
	private class SequenceSelector implements Selector{
		
		private int curtor = 0;

		public boolean hasNext() {
			return curtor != items.length;
		}
		
		public Object next() throws Exception {
			if(curtor > items.length)
				throw new Exception("a");
			return items[curtor++];
		}
		
	}
	
	public Selector selector(){
		return new SequenceSelector();
	}
	
	public static void main(String[] args) {
		Sequence s = new Sequence(10);
		for(int i = 0;i<10;i++){
			s.add(Integer.toString(i));
		}
		Selector selector = s.selector();
		while(selector.hasNext()){
			try {
				System.out.print(selector.next()+",");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		A a = new A();
		System.out.println(a.m);
		a.m = 2;
		A a1 = new A();
		System.out.println(a.m);
		System.out.println(a1.m);
		
	}
}
