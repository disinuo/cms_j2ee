package service.Impl;

import java.util.ArrayList;

import dao.ExamDao;
import dao.ScoreDao;
import factory.DaoFactory;
import factory.ServiceFactory;
import model.Exam;
import service.ExamService;

public class ExamServiceImpl implements ExamService{
	private static ExamServiceImpl examService = new ExamServiceImpl();
	private static ExamDao examDao=DaoFactory.getExamDao();
	private static ScoreDao scoreDao=DaoFactory.getScoreDao();
	private  ExamServiceImpl() {}
	public static ExamServiceImpl getInstance(){
		return examService;
	}
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
