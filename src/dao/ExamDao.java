package dao;

import model.Exam;

import javax.ejb.Remote;
import java.util.ArrayList;
import java.util.List;

@Remote
public interface ExamDao {
	/**
	 * get the exams of courses that the current user has chosen
	 * @param studentId
	 * @return
	 */
	public List<Exam> getChosenExams(String studentId);
	
	public List<Exam> getTakenExams(String studentId);
	/**
	 * get all exams in the database
	 * @return
	 */
	public List<Exam> getAllExams();
	/**
	 * get exam by ID
	 * @param examId
	 * @return
	 */
	public Exam getExamByID(String examId);
	/**
	 * get all exams of a course
	 * @param courseId
	 * @return
	 */
	public List<Exam> getExamOfACourse(String courseId);
	
}
