package factory;

import service.ExamService;
import service.ScoreService;
import service.StudentService;
import service.UserService;
import service.Impl.ExamServiceImpl;
import service.Impl.ScoreServiceImpl;
import service.Impl.StudentServiceImpl;
import service.Impl.UserServiceImpl;

public class ServiceFactory {
	public static ScoreService getScoreService(){
		return ScoreServiceImpl.getInstance();
	}
	public static StudentService getStudentService(){
		return StudentServiceImpl.getInstance();
	}
	public static UserService getUserService(){
		return UserServiceImpl.getInstance();
	}
	public static ExamService getExamService(){
		return ExamServiceImpl.getInstance();
	}
}
