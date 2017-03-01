package service.Impl;

import dao.StudentDao;
import dao.Impl.StudentDaoImpl;
import model.Student;
import service.StudentService;

public class StudentServiceImpl implements StudentService{
	private static StudentServiceImpl studentService = new StudentServiceImpl();
	private StudentServiceImpl(){}
	public static StudentServiceImpl getInstance(){
		return studentService;
	}
	private StudentDao studentDao=StudentDaoImpl.getInstance();
	
	@Override
	public Student getInfo(String studentId) {
		return studentDao.getInfo(studentId);
	}

}
