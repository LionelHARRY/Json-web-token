package com.springboot.jsonwebtoken.service;

import static java.util.Collections.emptyList;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationService {
	static final long EXPIRATION_TIME = 864_000_00; //1 day
	static final String SIGNING_KEY = "secretkey"; //Algorithm-specific signing key used to digitally sign the JWT
	static final String PREFIX = "Bearer"; //Prefix of the token
	
	/**
	 * Creates the token and adds it to the request's <i>Authorization</i> header
	 * @param res
	 * @param username
	 */
	static public void addToken(HttpServletResponse res, String username) {
		String JwtToken = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				//Encoding the signing key with SHA-512 algorithm
				.signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
				.compact();
		res.addHeader("Authorization", PREFIX + " " + JwtToken);
		//This is needed because we are not able to access the Authorization header through a JavaScript
		//Fronted by default
		res.addHeader("Access-Control-Expose-Headers", "Authorization");
	}
	
	/**
	 * Gets the token from the response <i>Authorization</i> header using the parser() method
	 * @param request
	 * @return UsernamePasswordAuthenticationToken or null
	 */
	static public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
	    if (token != null) {
	      String user = Jwts.parser()
	          .setSigningKey(SIGNING_KEY)
	          .parseClaimsJws(token.replace(PREFIX, ""))
	          .getBody()
	          .getSubject();

	      if (user != null) 
	    	  return new UsernamePasswordAuthenticationToken(user, null, emptyList());
	    }
	    return null;
	}
}
