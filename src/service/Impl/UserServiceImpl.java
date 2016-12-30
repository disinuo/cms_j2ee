package service.Impl;

import dao.UserDao;
import factory.DaoFactory;
import service.UserService;
import tool.UserType;

public class UserServiceImpl implements UserService{
	private UserDao userDao=DaoFactory.getUserDao();
	private static UserServiceImpl userService = new UserServiceImpl();
	private UserServiceImpl(){}
	public static UserServiceImpl getInstance(){
		return userService;
	}
	
	@Override
	public boolean ifExist(String id) {
		return userDao.ifExist(id);
	}
	@Override
	public UserType getType(String id) {
		return userDao.getType(id);
	}

}
