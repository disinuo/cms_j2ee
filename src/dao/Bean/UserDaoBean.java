package dao.Bean;

import dao.DaoHelper;
import dao.Impl.DaoHelperImpl;
import dao.Impl.UserDaoImpl;
import dao.UserDao;
import tool.UserType;

import javax.ejb.Stateless;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by disinuo on 17/2/24.
 */
@Stateless(name = "UserDaoEJB")
public class UserDaoBean  implements UserDao {
    private static DaoHelper daoHelper= DaoHelperImpl.getInstance();

    @Override
    public boolean ifExist(String id) {
        String sql="SELECT id FROM user WHERE id=?";
        ResultSet rs=daoHelper.handlePreparedStatement(sql,id);
        try {
            if(rs.next()){//the user exists

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            daoHelper.closeResultSet(rs);
            daoHelper.closeStatement_Connection();
        }
        return false;
    }
    @Override
    public UserType getType(String id) {
        String sql="SELECT type FROM user WHERE id=?";
        UserType type=UserType.ERROR;
        ResultSet rs=daoHelper.handlePreparedStatement(sql,id);
        try {
            if(rs.next()){//the user exists
                type=UserType.toEnum(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            daoHelper.closeResultSet(rs);
            daoHelper.closeStatement_Connection();
        }
        return type;
    }

}
