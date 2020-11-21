package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import annotation.Autowired;
import annotation.Service;
import dao.StudentDao;
import domain.Student;

@Service
public class StudentService {
	@Autowired
	private StudentDao sd;
	
	public Student GetById(String id) throws Exception {
		return sd.get(id);
	}

	public Map<String, Student> GetAll() throws Exception {
		List<Student> l=sd.getAll();
//		System.out.println(l.size());
		Map<String, Student> m = new HashMap<String, Student>();
		for (int i = 0; i < l.size(); i++) {
            m.put(l.get(i).getId(),l.get(i));
//            System.out.println("查询到一个"+l.get(i).getName());
        }
		return m;
	}
	
	public List<Student> ListAll() throws Exception {
		List<Student> l=sd.getAll();
		return l;
	}
	
	
}
