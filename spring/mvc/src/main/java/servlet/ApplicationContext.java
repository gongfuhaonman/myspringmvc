package servlet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import annotation.Autowired;
import annotation.Component;
import annotation.ComponentScan;
import annotation.Controller;
import annotation.RestController;
import service.StartService;
import tool.ClassUtil;




public class ApplicationContext {
	private Map<String,Object>map =new HashMap<>();
	private Map<String,Class<?>>map2 =new HashMap<>();
//	Queue<Class<?>>waitQueue =new LinkedList<>();
	public ApplicationContext()  {
		addComponent();
		
	}

	public void addComponent() {
		ComponentScan cs=StartService.class.getAnnotation(ComponentScan.class);//该注解放在A类上
		String[]str=cs.value();
		for(String s:str) {
			List<Class<?>>clz=ClassUtil.getClasses(s);
			for(Class<?> c:clz) {
				String name;
				Component comp=c.getAnnotation(Component.class);
				Controller cont=c.getAnnotation(Controller.class);
				RestController rest=c.getAnnotation(RestController.class);
				if(comp!=null) {
					name=comp.name();
					map2.put(name,c);
				}
				if(cont!=null){
					name=cont.name();
					map2.put(name,c);
				}
				if(rest!=null) {
					name=rest.name();
					map2.put(name,c);
				}
				}
			}
		for(String s:str) {
			List<Class<?>>clz=ClassUtil.getClasses(s);
			for(Class<?> c:clz) {
					Component comp=c.getAnnotation(Component.class);
					Controller cont=c.getAnnotation(Controller.class);
					RestController rest=c.getAnnotation(RestController.class);
					String name;
					if(comp!=null) {
						name=comp.name();
						addAnnotation(name,c);
					}
					if(cont!=null){
						name=cont.name();
						addAnnotation(name,c);
					}
					if(rest!=null) {
						name=rest.name();
						addAnnotation(name,c);
					}
					/*
					try {
					if(comp!=null ) {
						if( map.get(comp.name())==null)addAnnotation(c);
					}else if(cont!=null) {
							map.put(cont.name(), c.newInstance());
						}
					 else if(rest!=null)map.put(rest.name(), c.newInstance());
					}catch (InstantiationException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}
			}
	}
	public void addAnnotation(String name,Class<?>clz)  {
		//System.out.println(clz.getName()+"1");
	
		
		Object object=null;
		try {
			
			try {
				Constructor<?>con= clz.getConstructor();
				object=con.newInstance();
				
			} catch (NoSuchMethodException | SecurityException|IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				//object = clz.newInstance();
				map.put(name, object);
				Field[] fields=clz.getDeclaredFields();
				for(Field f:fields) {
					Autowired autowired=f.getAnnotation(Autowired.class);
					if(autowired!=null) {
					//	System.out.println(autowired.name()+"2");
						Object obj=map.get(autowired.name());	
						if(obj==null) {
							addAnnotation(autowired.name(),map2.get(autowired.name()));
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
