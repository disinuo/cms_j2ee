package dao;

import tool.UserType;

import javax.ejb.Remote;

@Remote
public interface UserDao {
	public boolean ifExist(String id);

	public UserType getType(String id);
}
