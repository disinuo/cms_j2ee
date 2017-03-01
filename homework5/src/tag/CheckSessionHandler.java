package tag;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CheckSessionHandler extends TagSupport {  
    private static final long serialVersionUID = 1L;  
	    @Override  
    public int doStartTag() throws JspException {   
        int result=0;  
        HttpSession session=pageContext.getSession();
        HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
        String loginId="";
        if(session!=null){
            loginId=(String)session.getAttribute("id");
            System.out.println("sessionHandler  has session");
        }else{
        	loginId=request.getParameter("id");
            System.out.println("sessionHandler  NO session!");
        }
        System.out.println("sessionHandler  loginID: "+loginId);
        if(loginId==null){
            result=SKIP_BODY;
            System.out.println("tag_sessoin_handler!~~~~~");
            try {
                HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
                response.sendRedirect(request.getContextPath() + "/Login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            result=EVAL_BODY_INCLUDE;
        }

        return result;   
    }  
  
}  
