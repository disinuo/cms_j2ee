package service;

import java.util.ArrayList;

import model.Exam;
import model.Score;

public interface ShowScoreService {
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
