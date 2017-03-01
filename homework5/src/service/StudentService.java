package service;

import model.Student;
import javax.ejb.Remote;
@Remote
public interface StudentService {
	public Student getInfo(String studentId);
}
