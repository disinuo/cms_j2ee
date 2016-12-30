package factory;

import service.ShowScoreService;
import service.StudentService;
import service.UserService;
import service.Impl.ShowScoreServiceImpl;
import service.Impl.StudentServiceImpl;
import service.Impl.UserServiceImpl;

public class ServiceFactory {
	public static ShowScoreService getShowScoreService(){
		return ShowScoreServiceImpl.getInstance();
	}
	public static StudentService getStudentService(){
		return StudentServiceImpl.getInstance();
	}
	public static UserService getUserService(){
		return UserServiceImpl.getInstance();
	}
}
