package com.example.project.security.tokens;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.example.project.domain.Account;
import com.example.project.enums.EnumMapper;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {
	
	private static final Logger log = LoggerFactory.getLogger(PostAuthorizationToken.class);
	
	private static EnumMapper enumMapper;

	private PostAuthorizationToken(
			Object principal, 
			Object credentials,
			Collection<? extends GrantedAuthority> authorities
			) {
		super(principal, credentials, authorities);
	}
	
	public static PostAuthorizationToken getTokenFromAccountContext(Account account) {
		
		log.error();
		
		return new PostAuthorizationToken(
				account, 
				account.getPassword(), 
				enumMapper.userRoleList(account.getUserRole())
				);
	} 
	
	 public Account getAccount() {
		 return (Account)super.getPrincipal();
	 }
}
