package service;

import java.util.ArrayList;

import model.Exam;
import javax.ejb.Remote;
@Remote
public interface ExamService {

	/**
	 * get all exams that the user chosen
	 * @param studentId
	 * @return
	 */
	public ArrayList<Exam> getChosenExams(String studentId);
	/**
	 * get exams that should have taken but not
	 * return null if all have been taken
	 * @param studentId
	 * @return
	 */
	public ArrayList<Exam> getNotTakenExams(String studentId);


}
