package cn.com.jd.controller.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;

import com.alibaba.dubbo.common.json.JSON;

public class ZkClientTest {
	
	public static void main(String[] args) {
		List<Person> p = new ArrayList<Person>();
		p.add(new Person());
		p.add(new Person());
		p.add(new Person());
		p.get(0).setName("小明");
		p.get(0).setAge(12);
		p.get(1).setName("小刚");
		p.get(1).setAge(15);
		p.get(2).setName("小花");
		p.get(2).setAge(3);
		Long t = System.currentTimeMillis();
		sort(p);
		System.out.println(p.get(0).getName()+"--"+p.get(1).getName()+"--"+p.get(2).getName()+(System.currentTimeMillis()-t));
	}
	
	private static void sort(List<Person> persons){
		Collections.sort(persons,new Comparator<Person>() {

			public int compare(cn.com.jd.controller.util.Person o1,
					cn.com.jd.controller.util.Person o2) {
				if(o1 == o2) return 0;
				if(o1 == null) return 1;
				if(o2 == null) return -1;
				int result = o1.getAge() - o2.getAge();
				return result > 0 ? -1:((result<0) ? 1 : 0);
			}
			
		});
	}
}
