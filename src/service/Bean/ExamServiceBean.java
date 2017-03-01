package service.Bean;

import dao.ExamDao;
import dao.ScoreDao;
import factory.DaoFactory;
import model.Exam;
import service.ExamService;
import service.Impl.ExamServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;

/**
 * Created by disinuo on 17/2/24.
 */
@Stateless(name = "ExamServiceBeanEJB")
public class ExamServiceBean implements ExamService{
    @EJB ExamDao examDao;
    @EJB ScoreDao scoreDao;

    @Override
    public ArrayList<Exam> getChosenExams(String studentId) {
        return examDao.getChosenExams(studentId);
    }

    @Override
    public ArrayList<Exam> getNotTakenExams(String studentId) {
        ArrayList<Exam> examsNotTaken = new ArrayList<Exam>();
        ArrayList<Exam> exams_chosen=examDao.getChosenExams(studentId);
        ArrayList<Exam> exams_Taken=examDao.getTakenExams(studentId);
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
