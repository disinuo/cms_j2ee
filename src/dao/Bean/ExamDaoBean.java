package dao.Bean;

import dao.DaoHelper;
import dao.ExamDao;
import dao.Impl.DaoHelperImpl;
import model.Exam;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by disinuo on 17/2/24.
 */
@Stateless(name = "ExamDaoBeanEJB")
public class ExamDaoBean implements ExamDao {
    @PersistenceContext
    protected EntityManager em;

    public ExamDaoBean() {}

    @Override
    public List<Exam> getChosenExams(String studentId) {
        String sql="SELECT Exam  FROM SelectC,Exam,Course WHERE SelectC.course.id = Exam.course.id AND Course.id=SelectC.course.id AND SelectC.student.id = :sid";
        Query query = em.createQuery(sql).setParameter("sid",studentId);
        List<Exam> exams =(List<Exam>)query.getResultList();
        em.clear();//在处理大量实体的时候，如果不把已经处理过的实体从EntityManager中分离出来，将会消耗大量的内存；此方法分离内存中受管理的实体Bean，让VM进行垃圾回收
        return exams;

    }

    @Override
    public List<Exam> getTakenExams(String studentId) {
        String sql="SELECT Exam FROM Exam,Course,Score WHERE Score.exam.id=Exam.id AND Course.id=Exam.course.id AND Score.student.id =:sid";
        Query query = em.createQuery(sql).setParameter("sid",studentId);
        List<Exam> exams =(List<Exam>)query.getResultList();
        em.clear();//在处理大量实体的时候，如果不把已经处理过的实体从EntityManager中分离出来，将会消耗大量的内存；此方法分离内存中受管理的实体Bean，让VM进行垃圾回收
        return exams;
    }

    @Override
    public List<Exam> getAllExams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Exam getExamByID(String examId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Exam> getExamOfACourse(String courseId) {
        // TODO Auto-generated method stub
        return null;
    }
}
