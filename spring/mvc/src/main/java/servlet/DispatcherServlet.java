package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import annotation.RequestMapping;
import annotation.RestController;
import servlet.ApplicationContext;
import annotation.Controller;

import tool.ClassUtil;


@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet{
	
	/*
	 * 请按照https://blog.csdn.net/qq_37960007/article/details/80217489修改compiler配置
	 * 
	 * 
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private Map<Method,Object>beanMap=new HashMap<>();
	private Map<String,Method>restMap=new HashMap<>();
	private Map<String,Method>majorMap=new HashMap<>();
	private ApplicationContext context;
	static int REST = 0;
	static int MAJOR = 1;
	public DispatcherServlet() {
		super();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			 context=new ApplicationContext();
		} catch (SecurityException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Class<?>>clz=ClassUtil.getClasses("controller");//包名
		for(Class<?>c:clz) {
			RestController rs=c.getAnnotation(RestController.class);
			Controller co=c.getAnnotation(Controller.class);
			String name=null;
			if(rs!=null) {//如果存在rs,创建对象
				name=rs.name();
				//System.out.println(name+"rest");
				doMap(c,name,config,REST);
				
			}
			if(co!=null) {
				name=co.name();
				//System.out.println(name+"cont");
				doMap(c,name,config,MAJOR);
			}
		}
	}




	public void doMap(Class<?>c,String name,ServletConfig config,int rest) {
		//Object obj=c.newInstance();	
		Object obj=context.getBean(name);
		Method[] methods=c.getMethods();
		for(Method m:methods) {
			RequestMapping rm=m.getAnnotation(RequestMapping.class);
			if(rm!=null) {
				//存在rm，建立映射
				String uri=rm.value();
				beanMap.put(m, obj);
				if(rest==REST)
					restMap.put(config.getServletContext().getContextPath()+uri, m);
				else
					majorMap.put(config.getServletContext().getContextPath()+uri, m);
			}
		}
	} 

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=utf-8");
		String uri=req.getRequestURI();
		
		Method m=majorMap.get(uri);
		
		if(m!=null) {
			doMajor(m,req,resp);
		}
		else doRest(restMap.get(uri),req,resp);

	}

	private void doRest(Method m, HttpServletRequest req, HttpServletResponse resp) {	
		Object obj=beanMap.get(m);
		System.out.println(m.getName());
		Parameter []p=m.getParameters();
		Object[] args=new Object[p.length];
		for(int i=0;i<p.length;i++) {
			String value=req.getParameter(p[i].getName());
			
			args[i]=convert(value,p[i].getType());
		}
		try {
			Object ans = m.invoke(obj, args);
			resp.getWriter().write(JSON.toJSONString(ans));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException |IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void doMajor(Method m, HttpServletRequest req, HttpServletResponse resp) {
		//暂定无参，后续修改
		Object obj=beanMap.get(m);
		Parameter []p=m.getParameters();
		Object[] args=new Object[p.length];
		
		for(int i=0;i<p.length;i++) {
			String value=req.getParameter(p[i].getName());
			//System.out.println(p[i].getName()+":"+value);
			args[i]=convert(value,p[i].getType());
		}
		//for(Object o:args)System.out.println(o);
		try {
			ModelAndView mv=null;
			if(p.length>0)
				 mv =(ModelAndView) m.invoke(obj,args);
			else 
				 mv =(ModelAndView) m.invoke(obj);
			String url=mv.getView();
			Map<String,Object> map =mv.getModel();
			for(String str : map.keySet()) {
				req.setAttribute(str, map.get(str));
			}
			//resp.sendRedirect(url);
			req.getRequestDispatcher(url).forward(req,resp);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException |IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Object convert(String value, Class<?> type) {
		// TODO Auto-generated method stub
		//如果要实现更多参数转化，添加if
		
		if(type==int.class)return Integer.valueOf(value);
		else return value;
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String uri=req.getRequestURI();
		
		Method m=majorMap.get(uri);
		Object obj=beanMap.get(m);
	//	System.out.println(obj==null);
		try {
			
			ModelAndView mv =(ModelAndView)  m.invoke(obj,req);
			String url=mv.getView();
			Map<String,Object> map =mv.getModel();
			for(String str : map.keySet()) {
				req.setAttribute(str, map.get(str));
			}
			req.getRequestDispatcher(url).forward(req,resp);
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}

