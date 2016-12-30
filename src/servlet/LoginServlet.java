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
public class LoginServlet extends HttpServlet {
	
    public LoginServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession(false);
		System.out.println("in Login servlet");
		if(session==null){
			display(request, response);			
		}else{
			response.sendRedirect(request.getContextPath() + "/ShowScore");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("here is Login's doPost method!");
		HttpSession session = request.getSession(false);
		ServletContext context= getServletContext();
		if(request.getParameter("logout")!=null){
			System.out.println("logging out");
			if(session!=null){//that's a login user logging out
				int counter_login=Integer.parseInt((String)context.getAttribute("counter_login"));
				context.setAttribute("counter_login", Integer.toString(--counter_login));
				session.invalidate();
				session=null;
			}
			display(request, response);		
		}
		if(request.getParameter("logout_visitor")!=null){
			if(session!=null){//that's a visitor logging out
				int counter_visitor=Integer.parseInt((String)context.getAttribute("counter_visitor"));
				context.setAttribute("counter_visitor", Integer.toString(--counter_visitor));
				session.invalidate();
				session=null;
			}
			display(request, response);		
		}

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
		out.println ("<p style='font-size:10px'>站点统计：	在线人数："+counter_total+",已登录："+counter_login+",游客："+counter_visitor+"</p>");
	}
}
