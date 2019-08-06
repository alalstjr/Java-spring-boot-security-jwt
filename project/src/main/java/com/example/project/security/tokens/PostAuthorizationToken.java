package com.example.project.security.tokens;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.example.project.domain.Account;
import com.example.project.enums.EnumMapper;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {
	
	private static EnumMapper enumMapper;

	private PostAuthorizationToken(
			Object principal, 
			Object credentials,
			Collection<? extends GrantedAuthority> authorities
			) {
		super(principal, credentials, authorities);
	}

	public static PostAuthorizationToken getTokenFromAccountContext(Account account) {
		
		return new PostAuthorizationToken(
				account, 
				account.getPassword(), 
				enumMapper.userRoleList(account.getUserRole())
				);
	}
}
