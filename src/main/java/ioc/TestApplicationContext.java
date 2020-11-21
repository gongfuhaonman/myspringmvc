package ioc;

import controller.HelloController;
import controller.StudentController;
import dao.StudentDao;
import domain.Student;

public class TestApplicationContext {

	public static void main(String[] args) throws Exception {
	      ApplicationContext context = 
	          new ApplicationContext(Application.class);
//	      Student s=new Student("07","heyang07",27);
//	      StudentDao sd=context.getBean(StudentDao.class);
//	      if(sd==null)System.out.print("没有StudentDao");
//	      sd.add(s);
//	      StudentDao.add(s1);
//	      HelloController hc=context.getBean(HelloController.class);
//	      if(hc==null)System.out.print("没有hellocontroller");
//	      int size=context.map.size();
//	      System.out.print(size);
	      StudentController st=(StudentController)(context.getBean(StudentController.class));
			try {
				System.out.println("查询结果是："+st.ss.GetAll().get("01").getName());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	  }

}
