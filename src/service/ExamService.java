package service;

import java.util.List;

import model.Exam;

public interface ExamService {
	
	/**
	 * get all exams that the user chosen
	 * @param studentId
	 * @return
	 */
	public List<Exam> getChosenExams(String studentId);
	/**
	 * get exams that should have taken but not
	 * return null if all have been taken
	 * @param studentId
	 * @return
	 */
	public List<Exam> getNotTakenExams(String studentId);
	

}
