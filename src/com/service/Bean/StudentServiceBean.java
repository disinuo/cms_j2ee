package com.service.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.StudentDao;
import com.dao.Impl.StudentDaoImpl;
import com.model.Student;
import com.service.StudentService;
@Service
public class StudentServiceBean implements StudentService{
	
	@Autowired
	private StudentDao studentDao;
	@Override
	public Student getInfo(String studentId) {
		return studentDao.getInfo(studentId);
	}

}
