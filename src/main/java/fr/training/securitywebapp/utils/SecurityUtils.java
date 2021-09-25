package fr.training.securitywebapp.utils;

import java.util.List;
import java.util.Set;

import fr.training.securitywebapp.config.SecurityConfig;
import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtils {
	
	public static boolean isSecurityPage(HttpServletRequest request) {
		String urlPattern = UrlPatternUtils.getUrlPattern(request);
		
		Set<String> roles = SecurityConfig.getAllAppRoles();
		
		for(String role: roles) {
			List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
			if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasPermission(HttpServletRequest request) {
		String urlPattern = UrlPatternUtils.getUrlPattern(request);
		
		Set<String> allRoles = SecurityConfig.getAllAppRoles();
		
		for (String role : allRoles) {
			if (!request.isUserInRole(role)) {
				continue;
			}
			List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
			if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
				return true;
			}
		}
		
		return false;
	}
	
}
