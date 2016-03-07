package cn.com.jd.test;

public class StringTest {
	public static void main(String[] args) {
		String a = new String("abc");
		String b = "abc";
		String c = "abc";
		String d = "a"+"bc";
		System.out.println((a == b));
		System.out.println((b == c));
		System.out.println(b == d);
	}
}
