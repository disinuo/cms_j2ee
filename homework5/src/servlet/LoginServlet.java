package servlet;

import java.io.IOException;

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
//		session.invalidate();
//		session=null;
		ServletContext context= getServletContext();
		System.out.println("in Login servlet");
		System.out.println(session);
		if(session==null){
			setCookieId(request, response);
			context.getRequestDispatcher("/jsp/Login.jsp").forward(
					request, response);
		}else{
			response.sendRedirect(request.getContextPath() + "/ShowScore");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("here is Login's doPost method!");
		HttpSession session = request.getSession(false);
//		session.invalidate();
//		session=null;
		ServletContext context= getServletContext();
		if(request.getParameter("logout")!=null){
			System.out.println("logging out");
			System.out.println(session);
			if(session!=null){//that's a login user logging out
				int counter_login=Integer.parseInt((String)context.getAttribute("counter_login"));
				context.setAttribute("counter_login", Integer.toString(--counter_login));
				session.invalidate();
				session=null;
			}
			setCookieId(request, response);
			context.getRequestDispatcher("/jsp/Login.jsp").forward(
					request, response);
		}
		if(request.getParameter("logout_visitor")!=null){
			if(session!=null){//that's a visitor logging out
				int counter_visitor=Integer.parseInt((String)context.getAttribute("counter_visitor"));
				context.setAttribute("counter_visitor", Integer.toString(--counter_visitor));
				session.invalidate();
				session=null;
			}
			setCookieId(request, response);
			context.getRequestDispatcher("/jsp/Login.jsp").forward(
					request, response);
		}
	}
	private void setCookieId(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id="";
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			System.out.println("cookie not null");
			for(Cookie cookie:cookies){
				if(cookie.getName().equals("id")){
					id=cookie.getValue();
					request.setAttribute("cookie_id", id);
					System.out.println("cookie_id="+id);
					break;
				}
			}
		}
	}
}
