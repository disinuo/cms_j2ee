package dao.Bean;

import dao.DaoHelper;
import dao.Impl.DaoHelperImpl;
import dao.UserDao;
import model.Course;
import model.User;
import tool.UserType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by disinuo on 17/2/24.
 */
@Stateless(name = "UserDaoEJB")
public class UserDaoBean  implements UserDao {
    @PersistenceContext
    protected EntityManager em;

    @Override
    public boolean ifExist(String id) {
        User user=em.find(User.class,id);
        if(user!=null) return true;
        else return false;
    }
    @Override
    public UserType getType(String id) {
        User user=em.find(User.class,id);
        return user.getType();
    }


}
