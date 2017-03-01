package dao;

import java.util.ArrayList;

import model.Exam;

import javax.ejb.Remote;

@Remote
public interface ExamDao {
	/**
	 * get the exams of courses that the current user has chosen
	 * @param studentId
	 * @return
	 */
	public ArrayList<Exam> getChosenExams(String studentId);
	
	public ArrayList<Exam> getTakenExams(String studentId);
	/**
	 * get all exams in the database
	 * @return
	 */
	public ArrayList<Exam> getAllExams();
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
	public ArrayList<Exam> getExamOfACourse(String courseId);
	
}
