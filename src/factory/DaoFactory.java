package factory;

import dao.ExamDao;
import dao.ScoreDao;
import dao.StudentDao;
import dao.UserDao;
import dao.Impl.ExamDaoImpl;
import dao.Impl.ScoreDaoImpl;
import dao.Impl.StudentDaoImpl;
import dao.Impl.UserDaoImpl;

public class DaoFactory {
	public static ScoreDao getScoreDao(){
		return ScoreDaoImpl.getInstance();
	}
	public static StudentDao getStudentDao(){
		return StudentDaoImpl.getInstance();
	}
	public static ExamDao getExamDao(){
		return ExamDaoImpl.getInstance();
	}
	public static UserDao getUserDao(){
		return UserDaoImpl.getInstance();
	}
}
