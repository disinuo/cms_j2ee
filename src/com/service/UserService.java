package com.service;

import com.tool.UserType;

public interface UserService {
	public boolean ifExist(String id);
	public UserType getType(String id);
}
