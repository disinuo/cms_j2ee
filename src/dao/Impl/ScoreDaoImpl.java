package dao.Impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import dao.ScoreDao;
import factory.ConfigureFactory;
import model.Score;

public class ScoreDaoImpl implements ScoreDao{
	private SessionFactory sessionFactory;
	private Session session;
	private static ScoreDaoImpl scoreDao=new ScoreDaoImpl();
	
	private  ScoreDaoImpl() {
		sessionFactory=ConfigureFactory.getSessionFactory();
	}
	public static ScoreDaoImpl getInstance(){
		return scoreDao;
	}
	
	
	@Override
	public List<Score> getTakenScores(String studentId) {
		int sid=Integer.parseInt(studentId);
		String hql="SELECT s FROM Exam e,Course c,Score s WHERE s.exam.id=e.id AND c.id=e.course.id AND s.sid=:sid";
		session=sessionFactory.openSession();
		List<Score> scores=(List<Score>)session.createQuery(hql).setParameter("sid",sid).getResultList();
		session.close();
		return scores;
	}


	@Override
	public List<Score> getAllScores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Score> getScoresOfACourse(String courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Score> getScoresOfAnExam(String examId) {
		// TODO Auto-generated method stub
		return null;
	}

}
