package ioc;

import annotation.Bean;
import annotation.ComponentScan;
import annotation.Configuration;

@Configuration
@ComponentScan({"config"})
public class Application {

	
	//对于@Bean标记的方法，返回的都是一个bean，在增强的方法中，Spring会先去容器中查看一
	//下是否有这个bean的实例了，注意，bean就是一个类的实例，就是一个对象！
	//如果有了的话，就返回已有对象，没有的话就创建一个，然后放到容器中。

}