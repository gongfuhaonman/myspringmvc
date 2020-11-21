package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import annotation.Component;
import domain.Student;


@Component
public class StudentDao {
	//添加
	public Boolean add(final Student student) throws Exception {
		JDBCTemplate<Boolean> t = new Transaction<Boolean>() {
			@Override
			protected Boolean doTransaction(Connection conn) throws Exception {
				PreparedStatement ps = conn
						.prepareStatement("insert into student(id,name,age) values(?,?,?)");
				ps.setString(1, student.getId());
				ps.setString(2, student.getName());
				ps.setInt(3, student.getAge());
				return ps.execute();
			}
		};
		return t.execute();
	}
	//获取
	public Student get(final String id) throws Exception {
		JDBCTemplate<Student> q = new Query<Student>() {
			@Override
			protected Student doQuery(Connection conn) throws Exception {
				PreparedStatement ps = conn
						.prepareStatement("select * from student where id=?");
				ps.setString(1, id);
				ps.execute();
				ResultSet rs = ps.getResultSet();
				Student student = null;
				if (rs.next()) {
					student = new Student();
					student.setId(rs.getString("id"));
					student.setName(rs.getString("name")) ;
					student.setAge(rs.getInt("age")) ;
				}
				return student;
			}
		};
		return q.execute();
	}

	// 获取全部
	public List<Student> getAll() throws Exception {
		JDBCTemplate<List<Student>> q = new Query<List<Student>>() {

			@Override
			protected List<Student> doQuery(Connection conn) throws Exception {
				List<Student> students = new ArrayList<Student>();
				PreparedStatement ps = conn
						.prepareStatement("select * from student");
				ps.execute();
				ResultSet rs = ps.getResultSet();
				while (rs.next()) {
					Student student = new Student();
					student.setId(rs.getString("id"));
					student.setName(rs.getString("name")) ;
					student.setAge(rs.getInt("age")) ;
					students.add(student);
				}
				return students;
			}
		};
		return q.execute();
	}

	// 修改
	public Boolean update(final Student student, final String oldId)
			throws Exception {
		JDBCTemplate<Boolean> t = new Transaction<Boolean>() {
			@Override
			protected Boolean doTransaction(Connection conn) throws Exception {
				PreparedStatement ps = conn
						.prepareStatement("update student set id=?,name=?,age=? where id=?");
				ps.setString(1, student.getId());
				ps.setString(2, student.getName());
				ps.setInt(3, student.getAge());
				ps.setString(4, oldId);
				return ps.execute();
			}
		};
		return t.execute();
	}

	// 删除
	public Boolean delete(final String id) throws Exception {
		JDBCTemplate<Boolean> t = new Transaction<Boolean>() {
			@Override
			protected Boolean doTransaction(Connection conn) throws Exception {
				PreparedStatement ps = conn
						.prepareStatement("delete from student where id=?");
				ps.setString(1, id);
				return ps.execute();
			}
		};
		return t.execute();
	}
}
