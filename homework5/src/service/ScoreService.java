package service;

import java.util.ArrayList;

import model.Exam;
import model.Score;
import javax.ejb.Remote;
@Remote
public interface ScoreService {
	/**
	 * get scores that the current user has taken
	 * @param studentId
	 * @return
	 */
	public ArrayList<Score> getTakenScores(String studentId);
	/**
	 * get exams that the student should have taken
	 * @param studentId
	 * @return
	 */
	public ArrayList<Exam> getNotTakenExams(String studentId);

}
