package com.example.project.security.hendlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.project.domain.Account;
import com.example.project.dto.TokenDto;
import com.example.project.security.jwts.JwtFactory;
import com.example.project.security.tokens.PostAuthorizationToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private JwtFactory factory;
	
	private ObjectMapper objectMapper;
	
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest req, 
			HttpServletResponse res,
			Authentication auth
			) throws IOException, ServletException {
		// Token 값을 정형화된 DTO를 만들어서 res 으로 내려주는 역활을 수행한다.
		// 인증결과 객체 auth 를 PostAuthorizationToken객체 변수에 담아줍니다. 
		PostAuthorizationToken token = (PostAuthorizationToken) auth;
		Account context = (Account) token.getPrincipal();
		
		String tokenString = factory.generateToken(context);
		
		String username = token.getName();
		String userId = token.getAccount().getUserId();
		
		processRespone(res, writeDto(tokenString, username, userId));
	}
	 
	private TokenDto writeDto(String token, String username, String userId) {
		return new TokenDto(token, username, userId);
	}
	
	private void processRespone(
			HttpServletResponse res,
			TokenDto dto
			) throws JsonProcessingException, IOException {
		res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		res.setStatus(HttpStatus.OK.value());
		res.getWriter().write(objectMapper.writeValueAsString(dto));
	}

}
