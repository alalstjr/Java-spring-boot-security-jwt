package com.example.project.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.project.dto.FormLoginDTO;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

	private PreAuthorizationToken(String username, String password) {
		super(username, password);
	}
	
	public PreAuthorizationToken(FormLoginDTO dto) {
		this(dto.getUserId(), dto.getPassword());
	}
	
	public String getUsername() {
		return (String)super.getPrincipal();
	}
	
	public String getUserPassword() {
		return (String)super.getCredentials();
	}
}
