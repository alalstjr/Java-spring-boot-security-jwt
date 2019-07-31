package com.example.project.serviceImpl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.project.domain.Account;
import com.example.project.domain.AccountContext;
import com.example.project.service.UserService;

public class UserServiceImpl implements UserService {

	AccountContext accountContext;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Account> findById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account saveOrUpdateUser(Account account) {
//		String rawPassword = accountContext.getPassword();
//		String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
//		return accountContext;
		return null;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return null;
	}
}
