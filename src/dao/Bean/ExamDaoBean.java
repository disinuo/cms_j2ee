package dao.Bean;

import dao.DaoHelper;
import dao.ExamDao;
import dao.Impl.DaoHelperImpl;
import model.Exam;

import javax.ejb.Stateless;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by disinuo on 17/2/24.
 */
@Stateless(name = "ExamDaoBeanEJB")
public class ExamDaoBean implements ExamDao{
    private static DaoHelper daoHelper= DaoHelperImpl.getInstance();
    public ExamDaoBean() {}

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
    public ArrayList<Exam> getTakenExams(String studentId) {
        String sql="SELECT exam.id as examID,exam.name as examName,exam.date as examDate,course.id as courseID,course.name as courseName FROM exam,course,score WHERE score.eid=exam.id AND course.id=exam.cid AND score.sid =?";
        ResultSet rs=daoHelper.handlePreparedStatement(sql,studentId);
        ArrayList<Exam> exams=new ArrayList<Exam>();
        try {
//			System.out.print("in getSCores method resultSet: "+rs);
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
                //TODO handle resultSet is null
                System.out.println( "getTakenExams  resultSet is null");
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
