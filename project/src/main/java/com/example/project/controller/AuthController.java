package com.example.project.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.domain.Account;
import com.example.project.dto.AccountSaveRequestDTO;
import com.example.project.serviceImpl.UserServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class AuthController {
	
	private UserServiceImpl userServiceImpl;
	
	// CREATE	
	@PostMapping("")
	public ResponseEntity<?> insertUser(
			@Valid @RequestBody AccountSaveRequestDTO dto,
			BindingResult result
			) {
		Account newAccount = userServiceImpl.saveOrUpdateUser(dto);
		
		return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
	}
}
