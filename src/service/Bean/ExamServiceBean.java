package service.Bean;

import dao.ExamDao;
import dao.ScoreDao;
import model.Exam;
import service.ExamService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by disinuo on 17/2/24.
 */
@Stateless(name = "ExamServiceBeanEJB")
public class ExamServiceBean implements ExamService {
    @EJB
    ExamDao examDao;
    @EJB ScoreDao scoreDao;

    @Override
    public List<Exam> getChosenExams(String studentId) {
        return examDao.getChosenExams(studentId);
    }

    @Override
    public List<Exam> getNotTakenExams(String studentId) {
        List<Exam> examsNotTaken = new ArrayList<Exam>();
        List<Exam> exams_chosen=examDao.getChosenExams(studentId);
        List<Exam> exams_Taken=examDao.getTakenExams(studentId);
        for(Exam exam_chs:exams_chosen){
            Boolean taken = false;
            for(Exam exam_tk:exams_Taken){
                if(exam_chs.getId()==exam_tk.getId()){
                    taken=true;
                    break;
                }
            }
            if(!taken) {
                Exam ex=exam_chs;
                examsNotTaken.add(ex);
            }
        }
        return examsNotTaken;
    }
}
