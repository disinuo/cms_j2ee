package service.Bean;

import dao.ScoreDao;
import model.Exam;
import model.Score;
import service.ScoreService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by disinuo on 17/2/24.
 */
@Stateless(name = "ScoreServiceEJB")
public class ScoreServiceBean implements ScoreService {
    @EJB ScoreDao scoreDao;
    @Override
    public List<Score> getTakenScores(String studentId) {
        return scoreDao.getTakenScores(studentId);
    }

    @Override
    public List<Exam> getNotTakenExams(String studentId) {
        // TODO Auto-generated method stub
        return null;
    }

}
