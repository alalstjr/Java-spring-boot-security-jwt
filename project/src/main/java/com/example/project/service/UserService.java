package com.example.project.service;

import org.springframework.stereotype.Service;

import com.example.project.domain.Account;
import com.example.project.dto.AccountSaveRequestDto;

@Service
public interface UserService {
	public Account saveOrUpdateUser(AccountSaveRequestDto dto);
}
