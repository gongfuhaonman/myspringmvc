package demo;
import annotation.Component;
import annotation.ComponentScan;
import annotation.Autowired;
@ComponentScan(value= {"demo","annotation"})
@Component(name="a")
public class A {
@Autowired(name="b")
private  B b;
private int a;
public void say() {
	System.out.println("I'm a");
	b.say();
}
public void set(int a) {
	this.a=a;
}
public int get() {
	return a;
}
}
