package dao;

import java.util.ArrayList;

import model.Score;
import javax.ejb.Remote;

@Remote
public interface ScoreDao {
	/**
	 * get scores that the current user has taken
	 * @param studentId
	 * @return
	 */
	public ArrayList<Score> getTakenScores(String studentId);
	/**
	 * get all scores in the database
	 * @return
	 */
	public ArrayList<Score> getAllScores();
	/**
	 * get all scores of a course
	 * @param courseId
	 * @return
	 */
	public ArrayList<Score> getScoresOfACourse(String courseId);
	/**
	 * get all scores of an exam
	 * @param examId
	 * @return
	 */
	public ArrayList<Score> getScoresOfAnExam(String examId);
}
