package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.javabean.ExamListBean;
import com.javabean.ScoreListBean;
import com.model.Exam;
import com.model.Score;
import com.service.ExamService;
import com.service.ScoreService;
import com.service.StudentService;
import com.service.UserService;
import com.tool.UserType;

/**
 * Servlet implementation class DatabaseHandler
 * Attribute name: id,type(the status of the user.eg:student)
 */
@WebServlet("/ShowScore")
public class ShowScoreServlet extends HttpServlet{
	public void init() throws ServletException {  
    	super.init();
    	appliationContext=new ClassPathXmlApplicationContext("applicationContext.xml"); 
    	studentService=(StudentService)appliationContext.getBean("StudentService");
    	scoreService=(ScoreService)appliationContext.getBean("ScoreService");
    	examService=(ExamService)appliationContext.getBean("ExamService");
    	userService=(UserService)appliationContext.getBean("UserService");
    } 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		response.setContentType("text/html; charset=UTF-8");
		ServletContext context= getServletContext();
		int counter_visitor=Integer.parseInt((String)context.getAttribute("counter_visitor"));
		int counter_login=Integer.parseInt((String)context.getAttribute("counter_login"));
		
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
	try {
 		if(session==null){// Not has a session
 			String id=request.getParameter("id");
//=============  1.Visitor ===========================
			if(id==null){//not login
				//to avoid the visitor refresh the page,and the number of visitor keep increasing.So create a session for him
				session=request.getSession(true);
 				context.setAttribute("counter_visitor", Integer.toString(++counter_visitor));
				context.getRequestDispatcher("/jsp/Visitor.jsp").forward(
							request, response);
//====================================================	
 			}else{
 				request.setAttribute("id", id);
 				//===============2.first time log in ======================
	 			if(userIDIfExist(request,response)){
	 				context.setAttribute("counter_login", Integer.toString(++counter_login));
	 				setUserType(request, response);
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
					getScores(request, response);
					context.getRequestDispatcher("/jsp/MyScore.jsp").forward(
							request, response);
	//=====================================================	
	//==============3.user not exist ======================
	 			}else {
	 				context.getRequestDispatcher("/jsp/UserNotExist.jsp").forward(
							request, response);
	//=====================================================	
	 			}
 			}

		}else{//already has a session
 			String id=(String)session.getAttribute("id");
 			System.out.println("id???session   "+id);
			if(id==null){//a visitor with a session
				context.getRequestDispatcher("/jsp/Visitor.jsp").forward(
						request, response);
			}else{
	//==============4.already log in ======================
				UserType type=(UserType)session.getAttribute("type");
				request.setAttribute("id", id);
				request.setAttribute("type", type);
				setInfo(request, response);
				getScores(request, response);
				context.getRequestDispatcher("/jsp/MyScore.jsp").forward(
						request, response);
//				displayNormal(request, response);
			}
			
		}
	} catch (ServletException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
    	System.out.println("ShowServlet--userIfExist");
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
	 * Attribute name: if_has_exam_not_taken
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getScores(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session=request.getSession(false);
		String id=(String)request.getAttribute("id");		
		List<Score> scores=scoreService.getTakenScores(id);
		ScoreListBean scoreListBean=new ScoreListBean();
		scoreListBean.setScoreList(scores);
		request.setAttribute("scores", scoreListBean);
		
		List<Exam> notTakenExam=examService.getNotTakenExams(id);
		ExamListBean examListBean=new ExamListBean();
		examListBean.setExamList(notTakenExam);
		if(notTakenExam.size()>0){
			request.setAttribute("exams_not_taken", examListBean);
//			request.setAttribute("if_has_exam_not_taken", true);
		}
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
	

	/**
	 * Attribute name: exams_not_taken
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void displayScores(HttpServletRequest request, HttpServletResponse response) throws IOException{
		getScores(request, response);

		PrintWriter out = response.getWriter();
		ArrayList<Score> scores=(ArrayList<Score>)request.getAttribute("scores");
		out.println("我的成绩:  <br>");
		out.println("<table><thead><td>课程</td><td>考试</td><td>分数</td></thead>");
		for (Score score:scores) {
			out.println("<tr><td>"+score.getExam().getCourse().getName()+"</td><td>"+score.getExam().getName()+"</td><td>"+score.getScore()+"</td></tr>");
		}
		out.println("</table>");
//			checkout whether the user has some exam didn't take
		Boolean if_has_exam_not_taken=(Boolean)request.getAttribute("if_has_exam_not_taken");
		if(if_has_exam_not_taken!=null&&if_has_exam_not_taken){
			displayExamNotTakenAlert(request, response);	
		}
	}
	private void displayCounter(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
    	ServletContext context= getServletContext();
    	int counter_visitor=Integer.parseInt((String)context.getAttribute("counter_visitor"));
		int counter_login=Integer.parseInt((String)context.getAttribute("counter_login"));
		int counter_total=counter_login+counter_visitor;
		out.println ("<p style='font-size:10px'>站点统计：	在线人数："+counter_total+",已登录："+counter_login+",游客："+counter_visitor+"</p>");
	}
	//alert user has some exam that should've taken
	private void displayExamNotTakenAlert(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
		out.println ("<p style='color:red'>您有没参加的考试!</p>");
		ArrayList<Exam> exams=(ArrayList<Exam>)request.getAttribute("exams_not_taken");
		out.println("<table style='color:red'>");
		for(Exam exam:exams) {
			out.println("<tr><td>"+exam.getCourse().getName()+"</td><td>"+exam.getName()+"</td><td>"+exam.getDate()+"</td></tr>");
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
	private StudentService studentService;
	private ScoreService scoreService;
	private ExamService examService;
	private UserService userService;
	private static ApplicationContext appliationContext;

}
