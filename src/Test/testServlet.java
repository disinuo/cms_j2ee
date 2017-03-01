package Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class testServlet extends HttpServlet {
	private Context ctx; 
	private DataSource ds;
	private Statement stmt; 
	private ResultSet rs;
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public testServlet() {
        super();
    }
    /**
     * 会被自动调用的初始化方法
     */

//    public void init()
//            throws ServletException {
//        if (ds == null) {
//
//            throw new ServletException("datasource not properly configured");
//        }
//    }
    public void init() throws ServletException{
	    try {
			ctx=new InitialContext();
		    ds= (DataSource) ctx.lookup("java:comp/env/jdbc/myDB") ;
		} catch (NamingException e) {
			System.out.println("Initial Error: "+e);
			e.printStackTrace();
		}
    }
//	  the back up method when change to resin
//    public void setDataSource(DataSource ds) {
//        this.ds = ds;
//    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 * doGet & doPost
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		boolean ifExist=false;
		try{
			/*
			 *  ctx=new InitialContext();
		    	datasource= (DataSource) ctx.lookup("java:comp/env/jdbc/myDB") ;
			 */
			// 获得数据库连接.# url，user，password都写在context.xml里啦
			Connection con= ds.getConnection();
	        //操作数据库，实现增删改查
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT * FROM user WHERE id='2'");   
			PrintWriter out = response.getWriter();
			while(rs.next()){
				ifExist=true;
				out.println (rs.getString("ID")+"- "+ rs.getString("username")+"- " +rs.getString("password")+"<P>");
			}
			System.out.println(ifExist);
			rs.close();
			stmt.close();
			con.close();
/*	---------------另一种数据库连接方式-----------------------
			 //1.加载驱动程序
	        Class.forName("com.mysql.jdbc.Driver");
	        //2. 获得数据库连接
	        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	        //3.操作数据库，实现增删改查
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT user_name, age FROM imooc_goddess");
	        //如果有数据，rs.next()返回true
	        while(rs.next()){
	            System.out.println(rs.getString("user_name")+" 年龄："+rs.getInt("age"));
	        }
	-------------------------------------- */
	   
		}catch(Exception e){
			System.out.print("service method error"+e);
		}
		
		
			

	}
}
