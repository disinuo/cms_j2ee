package com.dao.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.BaseDao;
import com.dao.StudentDao;
import com.model.Student;

@Repository
public class StudentDaoImpl implements StudentDao{
	@Autowired
	BaseDao baseDao;
	
	@Override
	public Student getInfo(String studentId) {
		int sid=Integer.parseInt(studentId);
		Student student=(Student)baseDao.findById(Student.class,sid);
		return student;
	}

}
