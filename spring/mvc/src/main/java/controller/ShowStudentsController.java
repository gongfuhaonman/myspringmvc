package controller;

import java.util.List;

import annotation.Autowired;
import annotation.Component;
import annotation.Controller;
import annotation.RequestMapping;
import dao.StudentDao;
import domain.Student;
import servlet.ModelAndView;
//@Component(name = "showStudentsController")
@Controller(name = "showStudentsController")
public class ShowStudentsController {
	@Autowired(name = "studentDao")
	StudentDao sd;
	@RequestMapping(value="/show.do")
	public ModelAndView ShowStudents() {
		ModelAndView mv=new ModelAndView();
		List<Student>studentList=sd.getStudentList();
		mv.addAttribute("list", studentList);
		mv.setView("/hello.jsp");
		return mv;
	}
}
