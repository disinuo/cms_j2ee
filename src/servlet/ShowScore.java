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
import model.Score;

/**
 * Servlet implementation class DatabaseHandler
 */
@WebServlet("/ShowScore")
public class ShowScore extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Context ctx; 
	private DataSource ds;
	private Connection cnn;
	private Statement stmt;
	private PreparedStatement pstmt;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowScore() {
        super();
    }
    /**
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
    public boolean userIDIfExist(HttpServletRequest request, HttpServletResponse response) throws  IOException {
    	String name=(String)request.getAttribute("id");
    	String sql="SELECT * FROM user WHERE id='"+name+"' OR userName='"+name+"'";
    	ResultSet rs_user=handleStatement(sql);    	
		try {
			if(rs_user.next()){//the user exists
				request.setAttribute("id", rs_user.getString("id"));
				request.setAttribute("type", rs_user.getString("type"));
				return true;
			}
			rs_user.close();
			closeResource();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    private void setInfo(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		String type=(String)request.getAttribute("type");
		String id=(String)request.getAttribute("id");
		if(type==null) {
			type=(String)request.getSession(false).getAttribute("type");
			id=(String)request.getSession(false).getAttribute("id");
		}
    	String sql="SELECT * FROM "+ type+" WHERE id=?";
		System.out.println(sql + "  id");
    	ResultSet rs_userDetail=handlePreparedStatement(sql,id);
		try {
			rs_userDetail.next();
			System.out.println("I'm setting chinese name! id= "+id);
			request.setAttribute("chineseName", rs_userDetail.getString("chineseName"));
			rs_userDetail.close();
			closeResource();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    /**
     * 
     */
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
			request.setAttribute("id", request.getParameter("id"));
 			if(userIDIfExist(request,response)){
 	 			String id=(String)request.getAttribute("id");
 	 			setInfo(request, response);
 				//if the 
 				boolean isLogin = (id==null)? false:true;
 				if(isLogin){//then create a session
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
 					display(request, response);
 				}else{//not logged in,then make it to login
 					response.sendRedirect(request.getContextPath()+"/Login");	
 				}
 			}else {
				displayNotExist(request, response);
			}
			
		}else{//already has a session
//			session.invalidate();
			System.out.println("Alreay has a session");
			String id=(String)session.getAttribute("id");
			setInfo(request, response);
			request.setAttribute("id", id);
			displayAlreadyIn(request,response);
			display(request,response);
		}

    }
    
    
    private void displayNotExist(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
		out.println ("对不起该账号不存在！<br>");
	}
	private void displayAlreadyIn(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
		out.println ("您已经登录！<br>");
	}
	private void addUserIDCookie(HttpServletResponse response,String id) {
		Cookie login_id_cookie=new Cookie("id",id);
//TODO		!!!!WHY HERE is RESPOSE add cookie.
		//but forward when getting cookie is from the request!
		response.addCookie(login_id_cookie);

	}
	private void display(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
			out.print("Welcome!  "+request.getAttribute("chineseName")+"<br>");
			displayScores(request, response);
			displayLogoutPage(request, response);
		

	}
	private void getScores(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id=(String)request.getAttribute("id");
		String sql="SELECT * FROM score WHERE sid = '"+id+"'";
		ResultSet rs=handleStatement(sql);
		ArrayList<Score> scores=new ArrayList<Score>();
		try {
			while(rs.next()){
				Score sc=new Score(rs.getInt("eid"), "", rs.getInt("score"));
				scores.add(sc);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("scores", scores);
		closeResource();
	}

	private void displayScores(HttpServletRequest request, HttpServletResponse response) throws IOException{
		getScores(request, response);
		ArrayList<Score> scores=(ArrayList<Score>)request.getAttribute("scores");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("我的成绩:  <br>");
		for (Score score:scores) {
			out.println(score.getExamID()+": "+score.getScore()+"<br>");

		}
		out.println("</p>");
	}
	public void displayLogoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		// ע��Logout
		out.println("<form method='GET' action='" + response.encodeURL(request.getContextPath() + "/Login") + "'>");
		out.println("</p>");
		out.println("<input type='submit' name='logout' value='logout'>");
		out.println("</form>");
		out.println("</body></html>");

	}
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
			System.out.print("HandlePreparedStatement Method Error:");
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
			System.out.print("HandlePreparedStatement Method Error:");
			e.printStackTrace();
		}
		return null;
	}
	
	private ResultSet handleStatement(String sql){
		try {
			cnn = ds.getConnection();
			stmt=cnn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			System.out.print("HandleStatement Method Error:");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * close the resources:statement,connection
	 */
	public void closeResource(){
		try {
			if(stmt!=null)stmt.close();
			if(pstmt!=null)pstmt.close();
			cnn.close();
		} catch (SQLException e) {
			System.out.print("closing resource Error:");
			e.printStackTrace();
		}
			
	}
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
}
