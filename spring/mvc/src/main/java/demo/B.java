package demo;

import annotation.Autowired;
import annotation.Component;

@Component(name="b")
public class B {
	@Autowired(name="c")
	private C c;
	public void say() {
		System.out.println("I'm b");
		c.say();
		
	}
}
