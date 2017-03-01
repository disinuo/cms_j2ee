package com.service.Bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ExamDao;
import com.dao.ScoreDao;
import com.model.Exam;
import com.service.ExamService;
@Service
public class ExamServiceBean implements ExamService{
	@Autowired
	private static ExamDao examDao;
	@Autowired
	private static ScoreDao scoreDao;
	
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
