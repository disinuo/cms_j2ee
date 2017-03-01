package dao;

import java.util.List;

import model.Exam;
import model.Score;

public interface ScoreDao {
	/**
	 * get scores that the current user has taken
	 * @param studentId
	 * @return
	 */
	public List<Score> getTakenScores(String studentId);
	/**
	 * get all scores in the database
	 * @return
	 */
	public List<Score> getAllScores();
	/**
	 * get all scores of a course
	 * @param courseId
	 * @return
	 */
	public List<Score> getScoresOfACourse(String courseId);
	/**
	 * get all scores of an exam
	 * @param examId
	 * @return
	 */
	public List<Score> getScoresOfAnExam(String examId);
}
