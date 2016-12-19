package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Course;
import model.Exam;
import model.Score;

/**
 * Servlet implementation class DatabaseHandler
 */
@WebServlet("/ShowScore")
public class ShowScore extends HttpServlet{
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session=request.getSession(false);
		Cookie login_id_cookie=null;
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){//looking for the idCookie
			for(Cookie ck:cookies){
				if(ck.getName().equals("id")){
					login_id_cookie=new Cookie("id", ck.getValue());
					break;
				}
			}
		}
 		if(session==null){//has a session
 			String id_input=request.getParameter("id");
 			if(id_input==null){//not login
				response.sendRedirect(request.getContextPath()+"/Login");	
 			}else{
 				request.setAttribute("id", id_input);
	 			if(userIDIfExist(request,response)){
	 	 			String id=(String)request.getAttribute("id");
	 	 			setInfo(request, response);
	 				boolean isLogin = (id==null)? false:true;
	 				if(isLogin){//then create a session
	 					session=request.getSession(true);
	 					session.setAttribute("id", request.getAttribute("id"));
	 					session.setAttribute("type", request.getAttribute("type"));
	 					if(login_id_cookie!=null){//has a loginID cookie
	 						if(!login_id_cookie.getValue().equals(id)){//not same
	 							//then update the cookie
	 							addUserIDCookie(response,id_input);
	 						}					
	 					}else{//does not have a loginID cookie
	 						addUserIDCookie(response,id_input);
	 					}
	 					display(request, response);
	 				}
	 			}else {displayNotExist(request, response);}
 			}
			
		}else{//already has a session
//			session.invalidate();
			System.out.println("Alreay has a session");
			String id=(String)session.getAttribute("id");
			String type=(String)session.getAttribute("type");
			request.setAttribute("id", id);
			request.setAttribute("type", type);
			setInfo(request, response);
			displayAlreadyIn(request,response);
			display(request,response);
		}

    }
	private void addUserIDCookie(HttpServletResponse response,String id) {
		Cookie login_id_cookie=new Cookie("id",id);
//TODO		!!!!WHY HERE is RESPOSE add cookie.
		//but forward when getting cookie is from the request!
		response.addCookie(login_id_cookie);

	}
/*****************************************************************************************
 * Methods with sql :
 */
	/**
	 * check out if the user exists
	 */
    private boolean userIDIfExist(HttpServletRequest request, HttpServletResponse response) throws  IOException {
    	String name=(String)request.getAttribute("id");
    	String sql="SELECT * FROM user WHERE id='"+name+"' OR userName='"+name+"'";
    	System.out.println("ifExist sql: "+sql);
    	ResultSet rs_user=handlePreparedStatement(sql);    	

    	try {
			if(rs_user.next()){//the user exists
				request.setAttribute("id", rs_user.getString("id"));
				request.setAttribute("type", rs_user.getString("type"));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs_user.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeResource();
		}
    	return false;
    }
    /**
     * set the information of the user
     */
    private void setInfo(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		String type=(String)request.getAttribute("type");
		String id=(String)request.getAttribute("id");
    	String sql="SELECT * FROM "+ type+" WHERE id=?";
    	ResultSet rs_userDetail=handlePreparedStatement(sql,id);
		try {
			rs_userDetail.next();
			request.setAttribute("chineseName", rs_userDetail.getString("chineseName"));
			rs_userDetail.close();
			closeResource();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    /**
	 * get courses that the user choosed 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getCoursesChosen(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id=(String)request.getAttribute("id");
		String sql="SELECT cid,name as cname FROM selectC,course WHERE cid = course.id AND sid = ?";
		ResultSet rs=handlePreparedStatement(sql,id);
		ArrayList<Course> courses=new ArrayList<Course>();
		try {
			if(rs!=null){
				while(rs.next()){
					Course course=new Course();
					course.setId(rs.getInt("cid"));
					course.setName(rs.getString("cname"));
					courses.add(course);
				}
			}else{
				//TODO handle resultSet is null
				System.out.println( "getCoursesChosen  resultSet is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("course_chosen", courses);
		closeResource();
	}
	/**
	 * get all the exams that the user should take
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getExamsChosen(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id=(String)request.getAttribute("id");
		String sql="SELECT exam.id as examID,exam.name as examName,exam.date as examDate,selectC.cid as courseID,course.name as courseName FROM selectC,exam,course WHERE selectC.cid = exam.cid AND course.id=selectC.cid AND selectC.sid = ?";
		ResultSet rs=handlePreparedStatement(sql,id);
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
				//TODO handle resultSet is null
				System.out.println( "getExamsChosen  resultSet is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("exam_chosen", exams);
		closeResource();
	}
	/**
	 * get all the scores that the user gets
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getScores(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id=(String)request.getAttribute("id");
		String sql="SELECT exam.id as examID,exam.name as examName,exam.date as examDate,course.id as courseID,course.name as courseName,score.score as score FROM exam,course,score WHERE score.eid=exam.id AND course.id=exam.cid AND score.sid =?";
		System.out.println("getScores: "+sql+id);
		ResultSet rs=handlePreparedStatement(sql,id);
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
		}
		request.setAttribute("scores", scores);
		closeResource();
	}

/*****************************************************************************************
 */
	
/*****************************************************************************************
 * Methods about display :
 */
	private void display(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
			out.print("Welcome!  "+request.getAttribute("chineseName")+"<br>");
			displayScores(request, response);
			displayLogoutPage(request, response);
	}
	private void displayScores(HttpServletRequest request, HttpServletResponse response) throws IOException{
		getScores(request, response);
		ArrayList<Score> scores=(ArrayList<Score>)request.getAttribute("scores");
		PrintWriter out = response.getWriter();
		out.println("<html><body><br>");
		out.println("我的成绩:  <br>");
		out.println("<table><thead><td>课程</td><td>考试</td><td>分数</td></thead>");
		for (Score score:scores) {
			out.println("<tr><td>"+score.getCourseName()+"</td><td>"+score.getExamName()+"</td><td>"+score.getScore()+"</td></tr>");
		}
		out.println("</table></body></html>");
	}
    private void displayNotExist(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
		out.println ("对不起该账号不存在！<br>");
	}
	private void displayAlreadyIn(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
		out.println ("您已经登录！<br>");
	}
	public void displayLogoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<form method='GET' action='" + response.encodeURL(request.getContextPath() + "/Login") + "'>");
		out.println("</p>");
		out.println("<input type='submit' name='logout' value='logout'>");
		out.println("</form>");
		out.println("</body></html>");

	}
/*****************************************************************************************
 */
	
/*****************************************************************************************
 * Methods about JDBC :  3 handleStatement,1 close resource
 */
	
/*----------------------------------------------------------------------
 * three methods that get the data from database using preparedStatement
 *
 */
	/**
     * handle the sql with dynamic parameter
     * >1 parameters
     * @param sql
     * @param param
     */
	private ResultSet handlePreparedStatement(String sql,List<String> param){
		ResultSet rs=null;
		try {
			cnn = ds.getConnection();
			pstmt=cnn.prepareStatement(sql);
			for(int i=0,len=param.size();i<len;i++){
				pstmt.setString(i+1, param.get(i));
			}
			rs=pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.print("HandlePreparedStatement Method -- >=2 parameters Error:");
			e.printStackTrace();
		}
		return rs;
	}
	/**
     * handle the sql with dynamic parameter
     * only 1 parameter
     * @param sql
     * @param param
     */
	private ResultSet handlePreparedStatement(String sql,String param){
		try {
			cnn = ds.getConnection();
			pstmt=cnn.prepareStatement(sql);
			pstmt.setString(1, param);	
			ResultSet rs=pstmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			System.out.print("HandlePreparedStatement Method --1 parameter Error:");
			e.printStackTrace();
		}
		return null;
	}
	
	private ResultSet handlePreparedStatement(String sql){
		try {
			cnn = ds.getConnection();
			pstmt=cnn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			System.out.print("HandlePreparedStatement Method --no parameter Error:");
			e.printStackTrace();
		}
		return null;
	}
/*----------------------------------------------------------------------
 */
	/**
	 * close the resources:statement,connection
	 */
	public void closeResource(){
		try {
			if(pstmt!=null)pstmt.close();
			if(cnn!=null)cnn.close();
		} catch (SQLException e) {
			System.out.print("closing resource Error:");
			e.printStackTrace();
		}
			
	}
/*****************************************************************************************
 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
    /**
     * initialize the datasource ***************************
     * auto invoked 
     */
    public void init(){
	    try {
			ctx=new InitialContext();
		    ds= (DataSource) ctx.lookup("java:comp/env/jdbc/myDB") ;
		} catch (NamingException e) {
			System.out.println("Initial Error: "+e);
			e.printStackTrace();
		}
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowScore() {
        super();
    }
/***********variables****************************************************************
 */
	private static final long serialVersionUID = 1L;
	private Context ctx; 
	private DataSource ds;
	private Connection cnn;
	private Statement stmt;
	private PreparedStatement pstmt;

}
