package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Exam;
import model.Score;

/**
 * Servlet implementation class DatabaseHandler
 * Attribute name: id,type(the status of the user.eg:student)
 */
@WebServlet("/ShowScore")
public class ShowScore extends HttpServlet{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		response.setContentType("text/html; charset=UTF-8");
		ServletContext context= getServletContext();
		int counter_visitor=Integer.parseInt((String)context.getAttribute("counter_visitor"));
		int counter_login=Integer.parseInt((String)context.getAttribute("counter_login"));
	
		
		System.out.println("the name input: "+request.getParameter("id"));
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
 		if(session==null){// Not has a session
 			String id_input=request.getParameter("id");
//=============  1.Visitor ===========================
 			if(id_input==null){//not login
 				context.setAttribute("counter_visitor", Integer.toString(++counter_visitor));
				displayVisitor(request, response);
//====================================================	
 			}else{
 				request.setAttribute("id", id_input);
//===============2.first time log in ======================
	 			if(userIDIfExist(request,response)){
	 				context.setAttribute("counter_login", Integer.toString(++counter_login));
	 	 			String id=(String)request.getAttribute("id");
	 	 			setInfo(request, response);
	 				//then create a session
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
 					displayNormal(request, response);
//=====================================================	
//==============3.user not exist ======================
	 			}else {
	 				displayNotExist(request, response);
//=====================================================	
	 			}
 			}
//==============4.already log in ======================
		}else{//already has a session
			System.out.println("Alreay has a session");
			String id=(String)session.getAttribute("id");
			String type=(String)session.getAttribute("type");
			request.setAttribute("id", id);
			request.setAttribute("type", type);
			setInfo(request, response);
			displayNormal(request,response);
		}
//====================================================	
    }
	private void addUserIDCookie(HttpServletResponse response,String id) {
		Cookie login_id_cookie=new Cookie("id",id);
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
				closeResource();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	return false;
    }
    /**
     * set the information of the user
     * Attribute name: chineseName
     */
    private void setInfo(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		String type=(String)request.getAttribute("type");
		String id=(String)request.getAttribute("id");
    	String sql="SELECT * FROM "+ type+" WHERE id=?";
    	ResultSet rs=handlePreparedStatement(sql,id);
		try {
			rs.next();
			request.setAttribute("chineseName", rs.getString("chineseName"));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				closeResource();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
	/**
	 * get all the exams that the user should take
 	 * Attribute name: exam_chosen
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
				System.out.println( "getExamsChosen  resultSet is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				closeResource();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("exam_chosen", exams);
	}
	/**
	 * get all the scores that the user gets
	 * Attribute name: scores
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getScores(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id=(String)request.getAttribute("id");
		String sql="SELECT exam.id as examID,"
				+ "exam.name as examName,"
				+ "exam.date as examDate,"
				+ "course.id as courseID,"
				+ "course.name as courseName,"
				+ "score.score as score "
				+ "FROM exam,course,score "
				+ "WHERE score.eid=exam.id "
				+ "AND course.id=exam.cid AND score.sid =?";
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
		}finally {
			try {
				rs.close();
				closeResource();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("scores", scores);
	}

/*****************************************************************************************
 */
	
/*****************************************************************************************
 * Methods about display :
 */
	// the normal situation
	private void displayNormal(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		out.println("<html><title>CMS|showScore</title><body><br>");
		out.print("Welcome!  "+request.getAttribute("chineseName")+"<br>");
		displayScores(request, response);//and alert if user has some exam not taken
		displayLogoutPage(request, response);
		displayCounter(request, response);
		out.println("</body></html>");
	}
	//special page for the visitor
	private void displayVisitor(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
    	out.println("<html><title>CMS|visitor</title><body><br>");
    	out.println ("Welcome 游客~！<br>");
		out.println ("想看更多信息就去登录吧！~<br>");
		displayGoToLogin(request,response);
		displayCounter(request, response);
		
		out.println("</body></html>");
	}
    private void displayNotExist(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
    	out.println("<html><title>CMS|Wrong ID</title><body><br>");
		out.println ("对不起该账号不存在！<br>");
		displayGoToLogin(request,response);
		displayCounter(request, response);
		out.println("</body></html>");
	}

	/**
	 * Attribute name: exams_not_taken
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void displayScores(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
//		get the exams that the user should take into request.attribute
		getExamsChosen(request, response);
		ArrayList<Exam> exams_chosen=(ArrayList<Exam>)request.getAttribute("exam_chosen");
		if(exams_chosen==null||exams_chosen.size()<1){
			out.println ("<p style='color:#198821'>您没有需要参加的考试~</p>");
		}else{
//			get the scores of exams that the user took, into request.attribute
			getScores(request, response);
			ArrayList<Score> scores=(ArrayList<Score>)request.getAttribute("scores");
			out.println("我的成绩:  <br>");
			out.println("<table><thead><td>课程</td><td>考试</td><td>分数</td></thead>");
			for (Score score:scores) {
				out.println("<tr><td>"+score.getCourseName()+"</td><td>"+score.getExamName()+"</td><td>"+score.getScore()+"</td></tr>");
			}
			out.println("</table>");
//			checkout whether the user has some exam didn't take
			ArrayList<Exam> exams_not_taken=new ArrayList<Exam>();
			//get all the examIDs that user should take
			//compare the examIDs. `should take` vs `have taken`
			for(Exam exam:exams_chosen){
				boolean taken=false;
				for(Score score:scores){
					if(exam.getId()==score.getExamID()){
						taken=true;
						break;
					}
				}
				if(!taken){
					exams_not_taken.add(exam);
				}
			}
			if(exams_not_taken.size()>0){
				request.setAttribute("exams_not_taken", exams_not_taken);
				displayExamNotTakenAlert(request, response);	
			}
		}
	}
	private void displayCounter(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
    	ServletContext context= getServletContext();
    	int counter_visitor=Integer.parseInt((String)context.getAttribute("counter_visitor"));
		int counter_login=Integer.parseInt((String)context.getAttribute("counter_login"));
		int counter_total=counter_login+counter_visitor;
		out.println ("<p style='font-size:10px'>站点统计：	总访问量："+counter_total+",登录人数："+counter_login+",游客人数："+counter_visitor+"</p>");
	}
	private void displayGoToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
    	out.println("<form method='POST' action='" + response.encodeURL(request.getContextPath() + "/Login") + "'>");
		out.println("</p>");
		out.println("<input type='submit' name='logout_visitor' value='go to login'>");//log out as a visitor
		out.println("</form>");
	}
	//alert user has some exam that should've taken
	private void displayExamNotTakenAlert(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
		out.println ("<p style='color:red'>您有没参加的考试!</p>");
		ArrayList<Exam> exams=(ArrayList<Exam>)request.getAttribute("exams_not_taken");
		out.println("<table style='color:red'>");
		for(Exam exam:exams) {
			out.println("<tr><td>"+exam.getCourseName()+"</td><td>"+exam.getName()+"</td><td>"+exam.getDate()+"</td></tr>");
		}
		out.println("</table>");
	}
	public void displayLogoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<form method='POST' action='" + response.encodeURL(request.getContextPath() + "/Login") + "'>");
		out.println("</p>");
		out.println("<input type='submit' name='logout' value='logout'>");
		out.println("</form>");
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
		try {
			cnn = ds.getConnection();
			pstmt=cnn.prepareStatement(sql);
			for(int i=0,len=param.size();i<len;i++){
				pstmt.setString(i+1, param.get(i));
			}
			return pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.print("HandlePreparedStatement Method -- >=2 parameters Error:");
			e.printStackTrace();
		}
		return null;
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
			return pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.print("HandlePreparedStatement Method --1 parameter Error:");
			e.printStackTrace();
		}
		return null;
	}
	/**
     * handle the sql with dynamic parameter
     * no parameter
     * @param sql
     */
	private ResultSet handlePreparedStatement(String sql){
		try {
			cnn = ds.getConnection();
			pstmt=cnn.prepareStatement(sql);
			return pstmt.executeQuery(sql);
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
    public ShowScore() {
        super();
    }
/***********variables****************************************************************
 */
	private static final long serialVersionUID = 1L;
	private Context ctx; 
	private DataSource ds;
	private Connection cnn;
	private PreparedStatement pstmt;

}
