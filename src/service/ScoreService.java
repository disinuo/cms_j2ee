package service;

import java.util.List;

import model.Exam;
import model.Score;

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
