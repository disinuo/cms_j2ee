package service;

import model.Exam;
import model.Score;

import java.util.List;
import javax.ejb.Remote;
@Remote
public interface ScoreService {
	/**
	 * get scores that the current user has taken
	 * @param studentId
	 * @return
	 */
	public List<Score> getTakenScores(String studentId);
	/**
	 * get exams that the student should have taken
	 * @param studentId
	 * @return
	 */
	public List<Exam> getNotTakenExams(String studentId);

}
