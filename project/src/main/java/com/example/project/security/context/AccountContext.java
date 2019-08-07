package com.example.project.security.context;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.project.domain.Account;
import com.example.project.enums.EnumMapper;
import com.example.project.enums.UserRole;


public class AccountContext extends User {

	private Account account;
	
	// private static EnumMapper enumMapper;
	
	private AccountContext(
			Account account, 
			String username, 
			String password, 
			Collection<? extends GrantedAuthority> authorities
			) {
		super(username, password, authorities);
		this.account = account;
	}
	
	public static AccountContext fromAccountModel(Account account) {
		
		return new AccountContext(
				account, 
				account.getUserId(), 
				account.getPassword(),
				userRoleList(account.getUserRole())
				// enumMapper.userRoleList(account.getUserRole())
				);
	}
	
	private static List<SimpleGrantedAuthority> userRoleList(UserRole role) {
		return Arrays
				.asList(role)
				.stream()
				.map(r -> new SimpleGrantedAuthority(r.getValue()))
				.collect(Collectors.toList()
				);
	}
	
	public final Account getAccount() {
		return account;
	}
}
