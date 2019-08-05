package com.example.project.security.filters;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest req, 
			HttpServletResponse res
			)
			throws AuthenticationException, IOException, ServletException {
		return null;
	}
}
