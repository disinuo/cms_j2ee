package com.dao;

import com.tool.UserType;

public interface UserDao {
	public boolean ifExist(String id);

	public UserType getType(String id);
}
