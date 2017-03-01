package dao;

import model.Student;
import javax.ejb.Remote;

@Remote
public interface StudentDao {
	public Student getInfo(String studentId);

}
