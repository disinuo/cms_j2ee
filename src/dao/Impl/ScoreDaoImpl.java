package dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoHelper;
import dao.ScoreDao;
import model.Exam;
import model.Score;

public class ScoreDaoImpl implements ScoreDao{
	private static DaoHelper daoHelper=DaoHelperImpl.getInstance();
	private static ScoreDaoImpl scoreDao=new ScoreDaoImpl();
	
	private  ScoreDaoImpl() {}
	public static ScoreDaoImpl getInstance(){
		return scoreDao;
	}
	
	
	@Override
	public ArrayList<Score> getTakenScores(String studentId) {
		String sql="SELECT exam.id as examID,"
				+ "exam.name as examName,"
				+ "exam.date as examDate,"
				+ "course.id as courseID,"
				+ "course.name as courseName,"
				+ "score.score as score "
				+ "FROM exam,course,score "
				+ "WHERE score.eid=exam.id "
				+ "AND course.id=exam.cid AND score.sid =?";
		ResultSet rs=daoHelper.handlePreparedStatement(sql,studentId);
		ArrayList<Score> scores=new ArrayList<Score>();
		try {
//			System.out.print("in getSCores method resultSet: "+rs);
			if(rs!=null){
				while(rs.next()){
					Score score=new Score();
					score.setExamID(rs.getInt("examID"));
					score.setExamName(rs.getString("examName"));
					score.setExamDate(rs.getString("examDate"));
					score.setCourseID(rs.getInt("courseID"));
					score.setCourseName(rs.getString("courseName"));
					score.setScore(rs.getInt("Score"));
					scores.add(score);
				}
			}else{
				//TODO handle resultSet is null
				System.out.println( "getScores  resultSet is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			daoHelper.closeResultSet(rs);
			daoHelper.closeStatement_Connection();
		}
		return scores;
	}


	@Override
	public ArrayList<Score> getAllScores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Score> getScoresOfACourse(String courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Score> getScoresOfAnExam(String examId) {
		// TODO Auto-generated method stub
		return null;
	}

}
