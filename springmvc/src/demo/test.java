package demo;

public class test {

	public static void main(String[] args)  {
		ApplicationContext context=new ApplicationContext();


		A a1 =(A) context.getBean("a");
		a1.set(1);
//		a2.set(2);
		a1.say();
//		System.out.println(a2.get());
	}

}
