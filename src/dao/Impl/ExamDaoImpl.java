package dao.Impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import dao.ExamDao;
import factory.ConfigureFactory;
import model.Exam;

public class ExamDaoImpl implements ExamDao{
	private SessionFactory sessionFactory;
	private Session session;
	private static ExamDaoImpl examDao=new ExamDaoImpl();
	
	private  ExamDaoImpl() {
		sessionFactory=ConfigureFactory.getSessionFactory();
	}
	public static ExamDaoImpl getInstance(){
		return examDao;
	}
	@Override
	public List<Exam> getChosenExams(String studentId) {
		int sid=Integer.parseInt(studentId);
		String hql="SELECT e FROM SelectC sel,Exam e,Course c WHERE sel.cid = e.course.id AND c.id=sel.cid AND sel.sid =:sid";
		session=sessionFactory.openSession();
		List<Exam> exams=(List<Exam>)session.createQuery(hql).setParameter("sid",sid).getResultList();
		session.close();
		return exams;
	}

	@Override
	public List<Exam> getTakenExams(String studentId) {
		int sid=Integer.parseInt(studentId);
		String hql="SELECT e FROM Exam e,Course c,Score s WHERE s.exam.id=e.id AND c.id=e.course.id AND s.sid=:sid";
		session=sessionFactory.openSession();
		List<Exam> exams=(List<Exam>)session.createQuery(hql).setParameter("sid",sid).getResultList();
		session.close();
		return exams;
	}

	@Override
	public List<Exam> getAllExams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exam getExamByID(String examId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Exam> getExamOfACourse(String courseId) {
		// TODO Auto-generated method stub
		return null;
	}

}
