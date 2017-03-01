package dao.Impl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dao.UserDao;
import factory.ConfigureFactory;
import model.User;
import tool.UserType;

public class UserDaoImpl implements UserDao{
	private SessionFactory sessionFactory;
	private Session session;
	private static UserDaoImpl userDao=new UserDaoImpl();
	
	private  UserDaoImpl() {
		sessionFactory=ConfigureFactory.getSessionFactory();
	}
	public static UserDaoImpl getInstance(){
		return userDao;
	}
	
	
	@Override
	public boolean ifExist(String id) {
		int id_int=Integer.parseInt(id);
    	String hql="FROM User user WHERE user.id=:id";
    	session=sessionFactory.openSession();
    	User user=(User)session.createQuery(hql).setParameter("id", id_int).getSingleResult();
    	if(user!=null) return true;
    	else return false;
	}
	@Override
	public UserType getType(String id) {
		int id_int=Integer.parseInt(id);
    	String hql="FROM User user WHERE user.id=:id";
    	session=sessionFactory.openSession();
    	User user=(User)session.createQuery(hql).setParameter("id", id_int).getSingleResult();
    	return user.getType();
	}

}
