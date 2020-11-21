package ioc;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import annotation.Autowired;
import annotation.Bean;
import annotation.Component;
import annotation.ComponentScan;
import annotation.Configuration;
import annotation.Controller;
import annotation.RestController;
import annotation.Service;
import annotation.View;
import utils.ClassTool;

public class ApplicationContext {
	public Map<Class<?>, Object> map = new HashMap<>();

	public ApplicationContext(Class<?>... clzs){
		try {
			for (Class<?> clz : clzs) {
				loadBeans(clz);
				scanComponents(clz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void scanComponents(Class<?> clz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		ComponentScan cs=clz.getAnnotation(ComponentScan.class);
		if(cs!=null)
		{
			String[] packages=cs.value();
			if(packages.length==0)//没有别的路径了，就只有本包。
				packages=new String[] {clz.getPackage().getName()};
			for(String pk:packages)
			{
				Set<Class<?>> clzSet=ClassTool.getClasses(pk);//得到扫描路径下所有的类
				for(Class<?> c:clzSet)
				{
					loadBeans(c);
					scanComponents(c);
					int boolcomponent=0;//记录是不是组件。
					Component com0=c.getAnnotation(Component.class);
					if(com0!=null) loadComponent(c);//如果被标记了，就加载组件
					Controller com1=c.getAnnotation(Controller.class);
					if(com1!=null) loadComponent(c);//如果被标记了，就加载组件
					RestController com2=c.getAnnotation(RestController.class);
					if(com2!=null) loadComponent(c);//如果被标记了，就加载组件
					Service com3=c.getAnnotation(Service.class);
					if(com3!=null) loadComponent(c);//如果被标记了，就加载组件
					View com4=c.getAnnotation(View.class);
					if(com4!=null) loadComponent(c);//如果被标记了，就加载组件
				}
			}
		}
	}

	private void loadComponent(Class<?> c) {
		try {
			Object o=c.newInstance();
			Field[] fields=c.getDeclaredFields();
			for(Field f:fields)
			{
				Autowired aw=f.getAnnotation(Autowired.class);
				
				if(aw!=null)
				{
					f.setAccessible(true);//扩宽权限 
					Object obj=map.get(f.getType());
					if(map.get(f.getType())==null) {
						loadComponent(f.getType());
					}
					f.set(o, map.get(f.getType()));
				}
			}
			map.put(c, o);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	private void loadBeans(Class<?> clz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		Configuration conf = clz.getAnnotation(Configuration.class);
		if (conf != null) {
			Object confObj = clz.newInstance();
			Method[] methods = clz.getDeclaredMethods();
			for (Method m : methods) {
				Bean bean = m.getAnnotation(Bean.class);
				if (bean != null) {
					Object ret = m.invoke(confObj);
					map.put(m.getReturnType(), ret);
				}
			}
		}
	}//如果这个类是有注解的，就实例化这个对象，并且对其中的bean的函数执行，将其返回的东西加入上下文。

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> clz) {
		return (T) map.get(clz);
	}
	
}
