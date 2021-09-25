package fr.training.securitywebapp.filter;

import java.io.IOException;
import java.util.List;

import fr.training.securitywebapp.bean.UserAccount;
import fr.training.securitywebapp.request.UserRoleRequestWrapper;
import fr.training.securitywebapp.utils.AppUtils;
import fr.training.securitywebapp.utils.SecurityUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SecurityFilter implements Filter {

	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
	throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String servletPath = request.getServletPath();
		
		UserAccount loginedUser = AppUtils.getLoginedUser(request.getSession());
		
		if(servletPath.equals("/login")) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest wrapRequest = request;
		
		
		if (loginedUser != null) {
			String userName = loginedUser.getUserName();
			
			List<String> roles = loginedUser.getRoles();
			
			wrapRequest = new UserRoleRequestWrapper(userName, roles, request);
		}
		
		if (SecurityUtils.isSecurityPage(request)) {
			
			if (loginedUser == null) {
				
				String requestUri = request.getRequestURI();
				
				//Stocke la page à rediriger après la connexion
				int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);
				
				response.sendRedirect(wrapRequest.getContextPath()+ "/login?redirectId=" + redirectId);
				return;
			}
			
			boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
			if(!hasPermission) {
				
				RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/accesDeniedView.jsp");
				
				dispatcher.forward(request, response);
				return;
			}
		}
		
		chain.doFilter(wrapRequest, response);
	}
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}
	
}
