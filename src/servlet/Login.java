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
	
    public Login() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		

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
		PrintWriter out = response.getWriter();
		
		out.println("<html><title>CMS|Login</title><body>");
		displayTextField(request, response);
		displayVisitorLogin(request, response);
		displayCounter(request, response);
		out.println("</body></html>");
	}
	private void displayTextField(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
		out.println("<form  method='post' action='/cms_j2ee/ShowScore'>");		
		out.println("<label for='id'>name</label>");		
		out.println("<input id='d' type='text' name='id' value="+id+">");
		out.println("<label for='password'>password</label>");
		out.println("<input id='password' type='password' name='password' value=''>");
		out.println("<input type='submit' name='btn' value='Login'>");
		out.println("</form>");

	}
	private void displayVisitorLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
		out.println("<form method='post' action='/cms_j2ee/ShowScore'>");
		out.println("<input type='submit' name='btn' value='Login_as_a_visitor'>");
		out.println("</form>");

	}
	private void displayCounter(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
    	ServletContext context= getServletContext();
    	int counter_visitor=Integer.parseInt((String)context.getAttribute("counter_visitor"));
		int counter_login=Integer.parseInt((String)context.getAttribute("counter_login"));
		int counter_total=counter_login+counter_visitor;
		out.println ("<p style='font-size:10px'>站点统计：	总访问量："+counter_total+",登录人数："+counter_login+",游客人数："+counter_visitor+"</p>");
	}
}
