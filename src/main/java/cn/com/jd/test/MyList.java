package cn.com.jd.test;

public interface MyList<E> extends MyCollection<E>{
	
	int size();
	
	boolean isEmpty();
	
	void add(E e);
	
	void remove(Object o);
	
	E get(int index);
	
}
