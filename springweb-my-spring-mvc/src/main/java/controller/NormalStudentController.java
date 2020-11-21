package controller;

import java.util.List;
import java.util.Map;

import annotation.Autowired;
import annotation.Controller;
import annotation.RequestMapping;
import domain.Student;
import service.StudentService;
import view.ModelAndView;

@Controller
public class NormalStudentController {
	@Autowired
	public StudentService ss;
	@Autowired
	ModelAndView mv=new ModelAndView();
	
	@RequestMapping(value="/studentList.do")
	public ModelAndView StudentList() throws Exception
	{
		List<Student> ls= ss.ListAll();
		mv.setViewName("/index");
		mv.addObject("students", ls);
		return mv;
	}
}
