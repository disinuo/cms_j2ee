package dao.Bean;

import dao.DaoHelper;
import dao.Impl.DaoHelperImpl;
import dao.Impl.StudentDaoImpl;
import dao.StudentDao;
import model.Student;

import javax.ejb.Stateless;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by disinuo on 17/2/24.
 */
@Stateless(name = "StudentDaoEJB")
public class StudentDaoBean implements StudentDao {
    private static DaoHelper daoHelper= DaoHelperImpl.getInstance();

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
