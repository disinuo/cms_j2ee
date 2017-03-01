package dao.Bean;

import dao.DaoHelper;
import dao.Impl.DaoHelperImpl;
import dao.StudentDao;
import model.Score;
import model.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by disinuo on 17/2/24.
 */
@Stateless(name = "StudentDaoEJB")
public class StudentDaoBean implements StudentDao {
    @PersistenceContext
    protected EntityManager em;

    @Override
    public Student getInfo(String studentId) {
        Student student = em.find(Student.class,studentId);
        return student;
    }

}
