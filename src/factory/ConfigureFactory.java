package factory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import model.*;


public class ConfigureFactory {
	private static Configuration config;
	private static SessionFactory sessionFactory;
	public static Configuration getConfiguration(){
		if(config==null){
			config = new Configuration().configure();
			config.addAnnotatedClass(Exam.class);
			config.addAnnotatedClass(Score.class);
			config.addAnnotatedClass(Course.class);
			config.addAnnotatedClass(Teacher.class);
			config.addAnnotatedClass(SelectC.class);
			config.addAnnotatedClass(Student.class);
			config.addAnnotatedClass(User.class);
		}
		return config;
	}
	public static SessionFactory getSessionFactory(){
		if(sessionFactory==null){
			getConfiguration();
			sessionFactory=config.buildSessionFactory();
		}
		return sessionFactory;
	}
	public static void closeSessionFactory(){
		if(sessionFactory!=null){
			sessionFactory.close();
			sessionFactory=null;
		}
	}
}
