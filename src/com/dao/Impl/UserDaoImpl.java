package com.dao.Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.BaseDao;
import com.dao.UserDao;
import com.model.User;
import com.tool.UserType;

@Repository
public class UserDaoImpl implements UserDao{
	@Autowired 
	BaseDao baseDao;
	
	@Override
	public boolean ifExist(String id) {
//		int id_int=Integer.parseInt(id);
//		System.out.println(id_int);
//    	String hql="FROM User user WHERE user.id=:id";
//    	User user=(User)baseDao.findById(User.class, id_int);
//    	if(user!=null) return true;
//    	else return false;
		return true;
	}
	@Override
	public UserType getType(String id) {
//		int id_int=Integer.parseInt(id);
//    	String hql="FROM User user WHERE user.id=:id";
//    	session=sessionFactory.openSession();
//    	User user=(User)session.createQuery(hql).setParameter("id", id_int).getSingleResult();
//    	return user.getType();
    	return UserType.STUDENT;
	}

}
