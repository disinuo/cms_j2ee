package filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/ShowScore")
public class CharsetFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
//			set the charset of the response to "utf-8"
			request.setCharacterEncoding("utf-8");
		}
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {

	}

}
