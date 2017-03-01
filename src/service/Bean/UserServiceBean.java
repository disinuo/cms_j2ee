package service.Bean;

import dao.UserDao;
import factory.DaoFactory;
import service.Impl.UserServiceImpl;
import service.UserService;
import tool.UserType;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by disinuo on 17/2/24.
 */
@Stateless(name = "UserServiceEJB")
public class UserServiceBean implements UserService {
    @EJB UserDao userDao;

    @Override
    public boolean ifExist(String id) {
        return userDao.ifExist(id);
    }
    @Override
    public UserType getType(String id) {
        return userDao.getType(id);
    }

}
