package com.Test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.ExamDao;
import com.dao.ScoreDao;
import com.dao.StudentDao;
import com.dao.UserDao;
import com.model.Exam;
import com.model.Score;
import com.model.Student;
import com.tool.UserType;
@Repository
public class HibernateDAOTest {
	@Autowired
	ExamDao examDao;
	@Autowired
	StudentDao studentDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ScoreDao scoreDao;
	String studentId="141250027";
	
	@Test
	public void test1(){
		List<Exam> exams=examDao.getChosenExams(studentId);
		for(Exam e:exams){
			System.out.println(e.getCourse().getName()+" "+e.getName());
		}
		
	}
	@Test
	public void test2(){
		List<Exam> exams=examDao.getTakenExams(studentId);
		for(Exam e:exams){
			System.out.println(e.getCourse().getName()+" "+e.getName());
		}
		
	}
	@Test
	public void test3(){
		Student student=studentDao.getInfo(studentId);
		System.out.println(student.getChineseName());
	}
	@Test
	public void test4(){
		List<Score> scores=scoreDao.getTakenScores(studentId);
		for(Score s:scores){
			System.out.println(s.getCourseName()+" "+s.getScore());			
		}
	}
	@Test
	public void test9(){
		Boolean ifExist=userDao.ifExist(studentId);
		System.out.println(ifExist);
	}
	@Test
	public void test10(){
		UserType type=userDao.getType(studentId);
		System.out.println(type);
	}

}
