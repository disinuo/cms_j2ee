package Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class testServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public testServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    Context ctx; DataSource ds;
    Statement stmt; ResultSet rs;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=GBK");
		try{
			// TODO Auto-generated method stub
			Connection con= ds.getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT * FROM EMPLOYEE");   
			PrintWriter out = response.getWriter();
			while(rs.next()){
				out.println (rs.getString("ID")+"- "+ rs.getString("NAME")+"- " +rs.getString("LOCATION")+"<P>");
				System.out.println("LOCATION:"+rs.getString("LOCATION"));
				}
				rs.close();
				stmt.close();
				con.close();	
		}catch(Exception e){
			System.out.print("doGet error"+e);
		}
		
		
			

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
