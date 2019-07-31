package com.example.project.domain;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class AccountContext {
	
	private static final Logger log = LoggerFactory.getLogger(AccountContext.class);
	
	@Autowired
	private Account account;
	
	public AccountContext(Account account) {
		this.account = account;
	}
	
	public static AccountContext updateAccount(Account account) {
		return new AccountContext(account);
	}
	
}
