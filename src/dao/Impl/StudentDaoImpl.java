package dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dao.StudentDao;
import factory.ConfigureFactory;
import model.Score;
import model.Student;

public class StudentDaoImpl implements StudentDao{
	private SessionFactory sessionFactory;
	private Session session;
	private static StudentDaoImpl studentDao=new StudentDaoImpl();
	
	private  StudentDaoImpl() {
		sessionFactory=ConfigureFactory.getSessionFactory();
	}
	public static StudentDaoImpl getInstance(){
		return studentDao;
	}
	
	
	@Override
	public Student getInfo(String studentId) {
		int sid=Integer.parseInt(studentId);
    	String hql="FROM Student WHERE id=:sid";
		session=sessionFactory.openSession();
		Student student=(Student)session.createQuery(hql).setParameter("sid",sid).getSingleResult();
		session.close();
		return student;
	}

}
