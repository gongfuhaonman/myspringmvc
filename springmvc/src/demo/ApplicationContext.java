package demo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import annotation.Autowired;
import annotation.Component;
import annotation.ComponentScan;
import tool.ClassUtil;

public class ApplicationContext {
	private Map<String,Object>map =new HashMap<>();
	private Map<String,Class<?>>map2 =new HashMap<>();
//	Queue<Class<?>>waitQueue =new LinkedList<>();
	public ApplicationContext()  {
	/*
	 * 实现了依赖顺序的自动配置
	 */
		ComponentScan cs=A.class.getAnnotation(ComponentScan.class);
		String[]str=cs.value();
		for(String s:str) {
			List<Class<?>>clz=ClassUtil.getClasses(s);
			for(Class<?> c:clz) {
				Component component=c.getAnnotation(Component.class);
				if(component!=null)map2.put(component.name(), c);
				}
			}
		for(String s:str) {
			List<Class<?>>clz=ClassUtil.getClasses(s);
			for(Class<?> c:clz) {
				Component component=c.getAnnotation(Component.class);
				if(component!=null )
					if( map.get(component.name())==null)
						addAnnotation(c);
				}
			}
		/*
		for(String s:str) {
			List<Class<?>>clz=ClassUtil.getClasses(s);
			for(Class<?> c:clz) {
				//System.out.println(c.getName());
				if(!addAnnotation(c)){
					waitQueue.add(c);
				}
			}
			while(!waitQueue.isEmpty()) {
				Class<?> c=waitQueue.remove();
				if(!addAnnotation(c)){
					waitQueue.add(c);
				}
			}
		}*/
	}
	public void addAnnotation(Class<?>clz)  {
		System.out.println(clz.getName());
		Component component=clz.getAnnotation(Component.class);
		Object object=null;
		try {
			if(component!=null) {
				object = clz.newInstance();
				map.put(component.name(), object);
				Field[] fields=clz.getDeclaredFields();
				for(Field f:fields) {
					Autowired autowired=f.getAnnotation(Autowired.class);
					if(autowired!=null) {
						Object obj=map.get(autowired.name());	
						if(obj==null) {
							addAnnotation(map2.get(autowired.name()));
							obj=map.get(autowired.name());
						}
						f.setAccessible(true);
						f.set(object, map.get(autowired.name()));
						/*
						if(obj!=null)
						{
							f.setAccessible(true);
							f.set(object, map.get(autowired.name()));
							
						}else {
							map.remove(component.name());
							return false;
						}*/
					}
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public Object getBean(String name) {
		return map.get(name);
	}
}
