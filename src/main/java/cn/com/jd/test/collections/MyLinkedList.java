package cn.com.jd.test.collections;

public class MyLinkedList<E> {
	
	private Entry<E> header = new Entry<E>(null, null, null);
	
	private int size = 0;
	
	public boolean add(E e){
		Entry<E> newEntry = new Entry<E>(e, header, header.next);
		
		return true;
	}
	
	

	private static class Entry<E>{
		
		E element;
		Entry<E> previous;
		Entry<E> next;
		
		Entry(E element,Entry<E> previous,Entry<E> next){
			this.element = element;
			this.previous = previous;
			this.next = next;
		}
		
	}
	
}
