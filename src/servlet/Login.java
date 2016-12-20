package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private int counter_visitor=0;
	private int counter_login=0;
    public Login() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		ServletContext Context= getServletContext();
		counter_visitor=Integer.parseInt((String)Context.getAttribute("counter_visitor"));
		counter_login=Integer.parseInt((String)Context.getAttribute("counter_login"));

		HttpSession session = request.getSession(false);
		System.out.println("**********************");
		System.out.println("in Login servlet");

		System.out.println("*************************");
		display(request, response);
		String ifLogout=request.getParameter("logout");
		if(ifLogout!=null){
			System.out.println("logging out");
			if(session!=null){
				session.invalidate();
				session=null;
			}
		}
//		RequestDispatcher dispatcher =request.getRequestDispatcher("/user/login.html?id="+idCookie);
//		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("here is Login's doPost method!");
		doGet(request, response);
	}
	private void display(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id="";
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if(cookie.getName().equals("id")){
					id=cookie.getValue();
					break;
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.println("<html><title>CMS|Login</title><body>");
		out.println("<form  method='post' action='/cms_j2ee/ShowScore'>");		
		out.println("<label for='id'>name</label>");		
		out.println("<input id='d' type='text' name='id' value="+id+">");
		out.println("<label for='password'>password</label>");
		out.println("<input id='password' type='password' name='password' value=''>");
		out.println("<input type='submit' name='btn' value='Login'>");
		out.println("</form>");
		
		out.println("<form method='post' action='/cms_j2ee/ShowScore'>");
		out.println("<input type='submit' name='btn' value='Login_as_a_visitor'>");
		out.println("</form>");
		out.println("<p>站点统计：</p>");
		out.println("<p style='size:8px'>总访问量："+(counter_login+counter_visitor));
		out.println(",登录人数："+counter_login);
		out.println(",游客人数："+counter_visitor);
		out.println("</body></html>");
	}

}
