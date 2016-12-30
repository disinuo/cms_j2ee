package dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DaoHelper;
import dao.StudentDao;
import model.Student;

public class StudentDaoImpl implements StudentDao{
	private static DaoHelper daoHelper=DaoHelperImpl.getInstance();
	private static StudentDaoImpl studentDao=new StudentDaoImpl();
	
	private  StudentDaoImpl() {
	}
	public static StudentDaoImpl getInstance(){
		return studentDao;
	}
	
	
	@Override
	public Student getInfo(String studentId) {
    	String sql="SELECT * FROM student WHERE id=?";
    	Student student=new Student();
    	ResultSet rs=daoHelper.handlePreparedStatement(sql,studentId);
		try {
			rs.next();
			student.setChineseName(rs.getString("chineseName"));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			daoHelper.closeResultSet(rs);
			daoHelper.closeStatement_Connection();
		}
		return student;
	}

}
