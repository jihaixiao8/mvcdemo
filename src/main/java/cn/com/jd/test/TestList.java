package cn.com.jd.test;


public class TestList {
	public static void main(String[] args) {
		int[] arr = {1,5,3,4,2};
		System.arraycopy(arr, 2, arr, 1, 3);
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0;i<arr.length;i++){
			if(i == arr.length -1){
				sb.append(arr[i]+"]");
			}else{
				sb.append(arr[i]+",");
			}
			
		}
		System.out.println(sb.toString());
	}
}
