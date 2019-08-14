package com.example.project.service;

import org.springframework.stereotype.Service;

import com.example.project.domain.Account;
import com.example.project.dto.AccountSaveRequestDTO;

@Service
public interface UserService {
	public Account saveOrUpdateUser(AccountSaveRequestDTO dto);
}
