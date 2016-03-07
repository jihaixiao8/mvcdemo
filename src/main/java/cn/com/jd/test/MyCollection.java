package cn.com.jd.test;

public interface MyCollection<E> {
	
	int size();
	
	boolean isEmpty();
	
	void add(E e);
	
	void remove(Object o);
	
}
