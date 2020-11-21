package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSON;

import annotation.Autowired;
import annotation.Controller;
import annotation.RequestMapping;
import annotation.RestController;
import controller.StudentController;
import ioc.Application;
import ioc.ApplicationContext;
import service.StudentService;
import utils.ClassTool;
import view.ModelAndView;
import view.ViewResolver;




/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ApplicationContext context;
	private Map<String,Method> methodsMap=new HashMap<>();
	private Map<Method,Object> controllerbeansMap=new HashMap<>();
	private Map<Method,Object> restcontrollerbeansMap=new HashMap<>();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DispatcherServlet() {
		super();
	}

	/**
	 * httpservlet默认有：
	 * destroy：在servlet销毁（服务器关闭）时调用。
	 * init：在servlet创建时调用，默认第一次访问的时候被调用
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		context = 
		          new ApplicationContext(Application.class); //启动ioc容器
		Map<Class<?>, Object> map=context.map;
		
//		StudentController st=(StudentController)(map.get(StudentController.class));
//		try {
//			System.out.println("查询结果是："+st.ss.GetAll().get("01").getName());
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}	
//		System.out.println("zheshi "+map.size());
		
		
		for (Entry<Class<?>, Object> entry : map.entrySet()) {
		      //类entry.getKey() 
		      //对象entry.getValue()
			Class<?> c=entry.getKey();
			RestController rc=c.getAnnotation(RestController.class);
			if(rc!=null)
			{
				try {
					Object o=entry.getValue();
					Method[] methods=c.getDeclaredMethods();
					for(Method m:methods)
					{
						RequestMapping rm=m.getAnnotation(RequestMapping.class);
						if(rm!=null)
						{
							String uri=rm.value();
							methodsMap.put(config.getServletContext().getContextPath()+uri, m);
							restcontrollerbeansMap.put(m, o);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Controller rc1=c.getAnnotation(Controller.class);
			if(rc1!=null)
			{
				try {
					Object o=entry.getValue();
					Method[] methods=c.getDeclaredMethods();
					for(Method m:methods)
					{
						RequestMapping rm=m.getAnnotation(RequestMapping.class);
						if(rm!=null)
						{
							String uri=rm.value();
							methodsMap.put(config.getServletContext().getContextPath()+uri, m);
							controllerbeansMap.put(m, o);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		    }
//		Set<Class<?>> clzSet=ClassTool.getClasses("controller");
//		for(Class<?> c:clzSet) //遍历controller包中的所有类
//		{
//			RestController rc=c.getAnnotation(RestController.class);
//			if(rc!=null)
//			{
//				try {
//					Object o=c.newInstance();
//					Method[] methods=c.getDeclaredMethods();
//					for(Method m:methods)
//					{
//						RequestMapping rm=m.getAnnotation(RequestMapping.class);
//						if(rm!=null)
//						{
//							String uri=rm.value();
//							methodsMap.put(config.getServletContext().getContextPath()+uri, m);
//							beansMap.put(m, o);
//						}
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		ViewResolver vr = context.getBean(ViewResolver.class);
//		response.setContentType("application/json;charset=utf-8");
		String uri=request.getRequestURI();
		Method m=methodsMap.get(uri);
		Parameter[] parameters=m.getParameters();
		Object[] args=new Object[parameters.length];
		for(int i=0;i<parameters.length;i++)
		{
			Parameter p=parameters[i];
			String value=request.getParameter(p.getName());
			args[i]=convert(value,p.getType());
		}
		Object obj=restcontrollerbeansMap.get(m);
		if(obj!=null) {  //如果是rest控制器
				try {
					Object ret=m.invoke(obj, args);
					response.getWriter().write(JSON.toJSONString(ret));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else { //如果是普通控制器
			Object obj1=controllerbeansMap.get(m);
			try {
				ModelAndView ret=(ModelAndView) m.invoke(obj1, args);
				if(vr!=null)
				System.out.println("视图解析器已经注入进来");
				String destinationUrl=vr.solve(ret.getViewName());  //目标视图
				System.out.println("路径是："+destinationUrl);
				Map<String,Object> mp = ret.getall(); 
				for (Map.Entry<String, Object> entry : mp.entrySet()) {
					request.setAttribute(entry.getKey(), entry.getValue());
					System.out.println("给视图发送了这个："+entry.getKey());
				    }
				request.getRequestDispatcher(destinationUrl).forward(request, response);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		}		

	private Object convert(String value, Class<?> type) {
		// TODO Auto-generated method stub
		return value;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
