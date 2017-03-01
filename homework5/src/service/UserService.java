package service;

import tool.UserType;
import javax.ejb.Remote;
@Remote
public interface UserService {
	public boolean ifExist(String id);
	public UserType getType(String id);
}
