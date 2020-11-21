package dao;

import java.util.ArrayList;
import java.util.List;

import annotation.Component;
import domain.Student;
@Component(name = "studentDao")
public class StudentDao {
private static List<Student>studentList=new ArrayList<Student>();
static {
	addStudent("1","小明",15,"男");
	addStudent("2","小张",16,"男");
	addStudent("3","小红",17,"女");
}
public List<Student>getStudentList() {
	return studentList;
}
public static void addStudent(String id,String name,int age,String gender) {
	studentList.add(new Student(id,name,age,gender));
}
public void deleteStudent(String id) {
	for(Student stu:studentList) {
		if(stu.getId().equals(id))
			studentList.remove(stu);
	}
}
public void updateStudent(String id,String name) {
	for(Student stu:studentList) {
		if(stu.getId().equals(id))
			stu.setName(name);
	}
}
public Student findStudent(String id) {
	for(Student stu:studentList) {
		if(stu.getId().equals(id))
			return stu;
	}
	return null;
}
}
