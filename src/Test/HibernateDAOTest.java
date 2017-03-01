package Test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.ExamDao;
import dao.ScoreDao;
import dao.StudentDao;
import dao.UserDao;
import factory.ConfigureFactory;
import factory.DaoFactory;
import model.Exam;
import model.Score;
import model.Student;
import tool.UserType;

public class HibernateDAOTest {
	ExamDao examDao;
	StudentDao studentDao;
	UserDao userDao;
	ScoreDao scoreDao;
	String studentId="141250027";
	@Before
	public void init(){
		examDao=DaoFactory.getExamDao();
		studentDao=DaoFactory.getStudentDao();
		scoreDao=DaoFactory.getScoreDao();
	}
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
//	@After
	public void close(){
		ConfigureFactory.closeSessionFactory();
	}
}
