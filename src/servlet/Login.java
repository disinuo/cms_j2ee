package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
		HttpSession session = request.getSession(false);
		System.out.println("**********************");
		System.out.println("in Login servlet");

		System.out.println("*************************");
		String idCookie="";
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for(Cookie ck:cookies){
				if(ck.getName().equals("id")){
					idCookie=ck.getValue();
					break;
				}
			}
		}
		String ifLogout=request.getParameter("logout");
		if(ifLogout!=null){
			System.out.println("%%%%%%%%");
			System.out.println("loging out");
			System.out.println("%%%%%%%%");
			if(session!=null){
				session.invalidate();
				session=null;
			}
		}
		response.sendRedirect(request.getContextPath()+"/user/login.html?id="+idCookie);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO
	}

}
