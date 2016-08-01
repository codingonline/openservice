package pop2016.openservice.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import pop2016.openservice.model.User;
import pop2016.openservice.service.UserManager;
import pop2016.openservice.utils.EnvironmentProperty;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(urlPatterns="/openService")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;  
        HttpServletResponse resp = (HttpServletResponse) response;   
        HttpSession session = req.getSession();   
        User user = (User) session.getAttribute("user");
        UserManager userManager = new UserManager();
        if(user==null){
			
				String token = req.getParameter("token");
				if(token==null)
					resp.sendRedirect(EnvironmentProperty.readConf("loginUrl"));
				else {
					user = userManager.findUserByToken(token);
					if(user==null){
						resp.sendRedirect(EnvironmentProperty.readConf("loginUrl"));
					}
					else{
					session.setAttribute("user", user);
					chain.doFilter(request, response);
					}
				}
		}
		else {
			chain.doFilter(request, response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
