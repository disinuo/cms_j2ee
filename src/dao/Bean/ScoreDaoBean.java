package dao.Bean;

import dao.DaoHelper;
import dao.Impl.DaoHelperImpl;
import dao.ScoreDao;
import model.Exam;
import model.Score;

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
@Stateless(name = "ScoreDaoEJB")
public class ScoreDaoBean implements ScoreDao {
    @PersistenceContext
    protected EntityManager em;

    @Override
    public List<Score> getTakenScores(String studentId) {
        String sql="SELECT Score FROM Exam,Course,Score  WHERE Score.exam.id=Exam.id AND Course.id=Exam.course.id AND Score.student.id=:sid";

        Query query = em.createQuery(sql).setParameter("sid",studentId);

        List<Score> scores=(List<Score>)query.getResultList();
        em.clear();//在处理大量实体的时候，如果不把已经处理过的实体从EntityManager中分离出来，将会消耗大量的内存；此方法分离内存中受管理的实体Bean，让VM进行垃圾回收
        return scores;

    }


    @Override
    public List<Score> getAllScores() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Score> getScoresOfACourse(String courseId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Score> getScoresOfAnExam(String examId) {
        // TODO Auto-generated method stub
        return null;
    }

}