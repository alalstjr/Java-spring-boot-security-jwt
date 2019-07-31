package com.example.project.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.domain.Account;
import com.example.project.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/api/user")
public class AuthController {
	
	UserServiceImpl userServiceImpl;
	
//	@PostMapping("")
//	public ResponseEntity<?> insertUser(
//			@Valid @RequestBody User account,
//			BindingResult result
//			) {
//		Account account = userServiceImpl.saveOrUpdateUser(account);
//	}
}
