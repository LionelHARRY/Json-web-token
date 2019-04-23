package com.springboot.jsonwebtoken;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.jsonwebtoken.domain.AccountCredentials;
import com.springboot.jsonwebtoken.service.AuthenticationService;

/**
 * 
 * Handles <i>POST</i> requests to the "/login" endpoint.
 *
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter{
	public LoginFilter(String url, AuthenticationManager authManager) {
	    super(new AntPathRequestMatcher(url));
	    setAuthenticationManager(authManager);
	  }
	  
	  /**
	   * Authentication
	   */
	  @Override
	  public Authentication attemptAuthentication(
		HttpServletRequest req, HttpServletResponse res)
				throws AuthenticationException, IOException, ServletException {
		AccountCredentials creds = new ObjectMapper()
	        .readValue(req.getInputStream(), AccountCredentials.class);
		return getAuthenticationManager().authenticate(
	        new UsernamePasswordAuthenticationToken(
	            creds.getUsername(),
	            creds.getPassword(),
	            Collections.emptyList()
	        )
	    );
	  }
	  
	  /**
	   * Executed if the authentication is successful and call addToken method</br>
	   * and the token will be added to the <i>Authorization</i> header.
	   */
	  @Override
	  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
	      Authentication auth) throws IOException, ServletException {
		  AuthenticationService.addToken(res, auth.getName());
	  }
}
