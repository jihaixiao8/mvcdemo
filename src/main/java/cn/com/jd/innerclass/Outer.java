package cn.com.jd.innerclass;

public class Outer {
	
	public Inner to(){
		return new Inner("ÄÚ²¿Àà"); 
	}
	
	public Outer returnSelf(){
		return new Outer();
	}
	
	public static void main(String[] args) {
		Outer outer = new Outer();
		Outer.Inner inner =  outer.to();
		System.out.println(inner.getClassName());
	}

	
	class Inner{
		
		private String className;
		
		public Inner(String name){
			this.className = name;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}
		
	}
}
