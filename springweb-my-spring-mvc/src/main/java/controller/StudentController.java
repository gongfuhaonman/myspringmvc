package controller;

import java.util.HashMap;
import java.util.Map;

import annotation.Autowired;
import annotation.RequestMapping;
import annotation.RestController;
import dao.StudentDao;
import domain.Student;
import service.StudentService;

@RestController
public class StudentController {
	
	@Autowired
	public StudentService ss;
	
	@RequestMapping(value="/getStudent.do")
	public Student getStudent(String id) throws Exception
	{
		return ss.GetById(id);
	}
	@RequestMapping(value="/getAllStudent.do")
	public Map<String,Student> getAStudent() throws Exception
	{
		return ss.GetAll();
	}
}
