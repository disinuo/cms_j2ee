package dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoHelper;
import dao.ExamDao;
import model.Exam;

public class ExamDaoImpl implements ExamDao{
	private static DaoHelper daoHelper=DaoHelperImpl.getInstance();
	private static ExamDaoImpl examDao=new ExamDaoImpl();
	
	private  ExamDaoImpl() {}
	public static ExamDaoImpl getInstance(){
		return examDao;
	}
	@Override
	public ArrayList<Exam> getChosenExams(String studentId) {
		String sql="SELECT exam.id as examID,exam.name as examName,exam.date as examDate,selectC.cid as courseID,course.name as courseName FROM selectC,exam,course WHERE selectC.cid = exam.cid AND course.id=selectC.cid AND selectC.sid = ?";
		ResultSet rs=daoHelper.handlePreparedStatement(sql,studentId);
		ArrayList<Exam> exams=new ArrayList<Exam>();
		try {
			if(rs!=null){
				while(rs.next()){
					Exam exam=new Exam();
					exam.setId(rs.getInt("examID"));
					exam.setName(rs.getString("examName"));
					exam.setDate(rs.getString("examDate"));
					exam.setCourseID(rs.getInt("courseID"));
					exam.setCourseName(rs.getString("courseName"));
					exams.add(exam);
				}
			}else{
				System.out.println( "getExamsChosen  resultSet is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			daoHelper.closeResultSet(rs);
			daoHelper.closeStatement_Connection();
		}
		return exams;
	}

	@Override
	public ArrayList<Exam> getAllExams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exam getExamByID(String examId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Exam> getExamOfACourse(String courseId) {
		// TODO Auto-generated method stub
		return null;
	}

}
