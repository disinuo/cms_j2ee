package com.service.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.service.UserService;
import com.tool.UserType;
@Service
public class UserServiceBean implements UserService{
	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean ifExist(String id) {
		return userDao.ifExist(id);
	}
	@Override
	public UserType getType(String id) {
		return userDao.getType(id);
	}

}
