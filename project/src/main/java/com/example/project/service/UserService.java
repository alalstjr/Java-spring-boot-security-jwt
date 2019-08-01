package com.example.project.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.project.domain.Account;
import com.example.project.dto.AccountSaveRequestDto;

public interface UserService {
	public Account saveOrUpdateUser(AccountSaveRequestDto dto);
}
