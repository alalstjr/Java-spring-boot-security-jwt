package com.example.project.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.project.domain.Account;
import com.example.project.domain.AccountContext;

public interface UserService extends UserDetailsService {
	public Optional<Account> findById(String userId);
	public Account saveOrUpdateUser(Account account);
	public void deleteUser(String userId);
	public PasswordEncoder passwordEncoder();
}
