package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    	System.out.println("$$$$  userIfExist Method  $$$$$$");
    	System.out.println(name);
    	System.out.println("$$$$$$$$$$");
    	String sql="SELECT * FROM user WHERE id='"+name+"' OR userName='"+name+"'";
    	ResultSet rs_user=handleStatement(sql);    	
		try {
			if(rs_user.next()){//the user exists
				request.setAttribute("id", rs_user.getString("id"));
				sql="SELECT * FROM $tableName WHERE id=?";
				sql =sql.replace("$tableName",rs_user.getString("type"));
				
		    	ResultSet rs_userDetail=handlePreparedStatement(sql,(String)request.getAttribute("id"));
				rs_userDetail.next();
				request.setAttribute("chineseName", rs_userDetail.getString("chineseName"));
				rs_userDetail.close();
				return true;
			}
			rs_user.close();
			closeResource();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }

    /**
     * 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session=request.getSession(false);
		Cookie login_id_cookie=null;
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for(Cookie ck:cookies){
				if(ck.getName().equals("id")){
					login_id_cookie=new Cookie("id", ck.getValue());
					break;
				}
			}
		}
 		if(session==null){
 			String id=request.getParameter("id");
			//if the 
			boolean isLogin = (id==null)? false:true;
			if(isLogin){//then create a session
				session=request.getSession(true);
				session.setAttribute("id", id);
				request.setAttribute("id", id);
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
			
		}else{//already has a session
			System.out.println("Alreay has a session");
			String id=(String)session.getAttribute("id");
			request.setAttribute("id", id);
			displayAlreadyIn(request,response);
			display(request,response);
		}

    }
    
    
    private void displayAlreadyIn(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
		out.println ("已经登录！");
	}
	private void addUserIDCookie(HttpServletResponse response,String id) {
		Cookie login_id_cookie=new Cookie("id",id);
//TODO		!!!!WHY HERE is RESPOSE add cookie.
		//but forward when getting cookie is from the request!
		response.addCookie(login_id_cookie);

	}
	private void display(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		if(userIDIfExist(request,response)){
			out.print("Welcome!  "+request.getAttribute("chineseName"));
		}else{
			out.println ("对不起该账号不存在！");
		}
		displayLogoutPage(request, response);

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
				System.out.println(param.get(i));
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
