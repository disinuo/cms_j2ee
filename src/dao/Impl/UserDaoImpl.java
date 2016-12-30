package dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DaoHelper;
import dao.UserDao;
import tool.UserType;

public class UserDaoImpl implements UserDao{
	private static DaoHelper daoHelper=DaoHelperImpl.getInstance();
	private static UserDaoImpl userDao=new UserDaoImpl();
	
	private  UserDaoImpl() {
	}
	public static UserDaoImpl getInstance(){
		return userDao;
	}
	
	
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
