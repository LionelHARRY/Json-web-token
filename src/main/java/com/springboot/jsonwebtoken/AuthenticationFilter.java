package com.springboot.jsonwebtoken;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.springboot.jsonwebtoken.service.AuthenticationService;

/**
 * 
 * Handle authentication in all other endpoints except "/login".
 *
 */
public class AuthenticationFilter extends GenericFilterBean{
	@Override
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
	      throws IOException, ServletException {
	    Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest)request);
	    
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    filterChain.doFilter(request, response);
	  }
}
