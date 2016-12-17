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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.sun.org.apache.regexp.internal.recompile;

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
	private ResultSet rs;
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
    public boolean userIDIfExist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String sql="SELECT * FROM user WHERE id=?";
    	handlePreparedStatement(sql, (String)request.getAttribute("name"));
		try {
			if(rs.next()){
				request.setAttribute("name", rs.getString("userName"));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }

    /**
     * 
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String name=request.getParameter("name");
		request.setAttribute("name", name);
		if(userIDIfExist(request,response)){
			out.print("NAME:"+request.getAttribute("name"));
		}else{
			out.println ("user not exist!");
		}
		closeResource();
    	
    }
    /**
     * handle the sql with dynamic parameter
     * >1 parameters
     * @param sql
     * @param param
     */
	private void handlePreparedStatement(String sql,List<String> param){
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
	}
	/**
     * handle the sql with dynamic parameter
     * only 1 parameter
     * @param sql
     * @param param
     */
	private void handlePreparedStatement(String sql,String param){
		try {
			cnn = ds.getConnection();
			pstmt=cnn.prepareStatement(sql);
			pstmt.setString(1, param);	
			rs=pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.print("HandlePreparedStatement Method Error:");
			e.printStackTrace();
		}
	}
	
	private void handleStatement(String sql){
		try {
			cnn = ds.getConnection();
			stmt=cnn.createStatement();
			rs=stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.print("HandleStatement Method Error:");
			e.printStackTrace();
		}
	}
	/**
	 * close the resources:resultSet,statement,connection
	 */
	public void closeResource(){
		try {
			rs.close();
			if(stmt!=null)stmt.close();
			if(pstmt!=null)pstmt.close();
			cnn.close();
		} catch (SQLException e) {
			System.out.print("closing resource Error:");
			e.printStackTrace();
		}
			
	}

}
