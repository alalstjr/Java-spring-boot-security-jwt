package com.example.project.serviceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project.domain.Account;
import com.example.project.dto.AccountSaveRequestDto;
import com.example.project.repository.AccountRepository;
import com.example.project.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private AccountRepository accountRepository;
	
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Account saveOrUpdateUser(AccountSaveRequestDto dto) {
		String rawPassword = dto.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		dto.setPassword(encodedPassword);
		
		return accountRepository.save(dto.toEntity());
	}
}
