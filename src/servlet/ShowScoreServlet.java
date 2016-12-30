package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import factory.ServiceFactory;
import model.Exam;
import model.Score;
import service.ShowScoreService;
import service.StudentService;
import service.UserService;
import tool.UserType;

/**
 * Servlet implementation class DatabaseHandler
 * Attribute name: id,type(the status of the user.eg:student)
 */
@WebServlet("/ShowScore")
public class ShowScoreServlet extends HttpServlet{
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
 			String id=request.getParameter("id");
//=============  1.Visitor ===========================
			if(id==null){//not login
				//to avoid the visitor refresh the page,and the number of visitor keep increasing.So create a session for him
				session=request.getSession(true);
 				context.setAttribute("counter_visitor", Integer.toString(++counter_visitor));
				displayVisitor(request, response);
//====================================================	
 			}else{
 				request.setAttribute("id", id);
 				//===============2.first time log in ======================
	 			if(userIDIfExist(request,response)){
	 				setUserType(request, response);
	 				context.setAttribute("counter_login", Integer.toString(++counter_login));
	 	 			setInfo(request, response);
	 				//then create a session
					session=request.getSession(true);
					session.setAttribute("id", request.getAttribute("id"));
					session.setAttribute("type", request.getAttribute("type"));
					if(login_id_cookie!=null){//has a loginID cookie
						if(!login_id_cookie.getValue().equals(id)){//not same
							//then update the cookie
							addUserIDCookie(response,id);
						}					
					}else{//does not have a loginID cookie
						addUserIDCookie(response,id);
					}
					displayNormal(request, response);
	//=====================================================	
	//==============3.user not exist ======================
	 			}else {
	 				displayNotExist(request, response);
	//=====================================================	
	 			}
 			}

		}else{//already has a session
 			String id=request.getParameter("id");
			if(id==null){//a visitor with a session
				displayVisitor(request, response);
			}else{
	//==============4.already log in ======================
				String type=(String)session.getAttribute("type");
				request.setAttribute("id", id);
				request.setAttribute("type", type);
				setInfo(request, response);
				displayNormal(request,response);
			}
			
		}
//====================================================	
    }
	private void addUserIDCookie(HttpServletResponse response,String id) {
		Cookie login_id_cookie=new Cookie("id",id);
		response.addCookie(login_id_cookie);

	}
	/**
	 * check out if the user exists
	 */
    private boolean userIDIfExist(HttpServletRequest request, HttpServletResponse response){
    	String id=(String)request.getAttribute("id");
    	return userService.ifExist(id);
    }
    /**
     * set the tyoe of the user  eg:student,  teacher  
     */
    private void setUserType(HttpServletRequest request, HttpServletResponse response) {
    	String id=(String)request.getAttribute("id");
		request.setAttribute("type", userService.getType(id));
    }
    /**
     * set the information of the user
     * Attribute name: chineseName
     */
    private void setInfo(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		UserType type=(UserType)request.getAttribute("type");
		String id=(String)request.getAttribute("id");
		String name="";
		switch(type){
			case STUDENT: 
				name=studentService.getInfo(id).getChineseName();	
				break;
			case TEACHER:
				//TODO  只做了学生的
				break;
		}
		request.setAttribute("chineseName", name);
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
		ArrayList<Score> scores=showScoreService.getTakenScores(id);
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
//		ArrayList<Exam> exams_chosen=(ArrayList<Exam>)request.getAttribute("exam_chosen");
//		if(exams_chosen==null||exams_chosen.size()<1){
//			out.println ("<p style='color:#198821'>您没有需要参加的考试~</p>");
//		}else{
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
//			ArrayList<Exam> exams_not_taken=new ArrayList<Exam>();
//			
//			if(exams_not_taken.size()>0){
//				request.setAttribute("exams_not_taken", exams_not_taken);
//				displayExamNotTakenAlert(request, response);	
//			}
//		}
	}
	private void displayCounter(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
    	ServletContext context= getServletContext();
    	int counter_visitor=Integer.parseInt((String)context.getAttribute("counter_visitor"));
		int counter_login=Integer.parseInt((String)context.getAttribute("counter_login"));
		int counter_total=counter_login+counter_visitor;
		out.println ("<p style='font-size:10px'>站点统计：	在线人数："+counter_total+",已登录："+counter_login+",游客："+counter_visitor+"</p>");
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

    public ShowScoreServlet() {
        super();
    }
/***********variables****************************************************************
 */
	private StudentService studentService=ServiceFactory.getStudentService();
	private ShowScoreService showScoreService=ServiceFactory.getShowScoreService();
	private UserService userService=ServiceFactory.getUserService();

}
