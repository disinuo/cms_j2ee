package service;

import model.Exam;
import java.util.List;
import javax.ejb.Remote;
@Remote
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
