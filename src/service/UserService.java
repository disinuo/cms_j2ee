package service;

import tool.UserType;

public interface UserService {
	public boolean ifExist(String id);
	public UserType getType(String id);
}
