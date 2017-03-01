package service.Bean;

import dao.Impl.StudentDaoImpl;
import dao.StudentDao;
import model.Student;
import service.Impl.StudentServiceImpl;
import service.StudentService;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by disinuo on 17/2/24.
 */
@Stateless(name = "StudentServiceEJB")
public class StudentServiceBean implements StudentService {
    @EJB StudentDao studentDao;

    @Override
    public Student getInfo(String studentId) {
        return studentDao.getInfo(studentId);
    }

}
