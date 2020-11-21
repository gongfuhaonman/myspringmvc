package controller;

import java.util.List;

import annotation.Autowired;
import annotation.Component;
import annotation.Controller;
import annotation.RequestMapping;
import dao.StudentDao;
import domain.Student;
import servlet.ModelAndView;

//@Component(name = "updateStudentsController")
@Controller(name = "updateStudentsController")
public class UpdateController {
	@Autowired(name = "studentDao")
	StudentDao sd;
	@RequestMapping(value="/update.do")
	public ModelAndView UpdateStudents(String id, String name) {
		ModelAndView mv=new ModelAndView();
		sd.updateStudent(id, name);
		List<Student>studentList=sd.getStudentList();
		mv.addAttribute("list", studentList);
		mv.setView("/hello.jsp");
		return mv;
	}
}
