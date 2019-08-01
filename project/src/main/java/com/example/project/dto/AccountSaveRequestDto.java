package com.example.project.dto;

import com.example.project.domain.Account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountSaveRequestDto {
	private String userId;
	private String username;
	private String password;
	
	public Account toEntity() {
		return Account.builder()
				.userId(userId)
				.username(username)
				.password(password)
				.build();
	}
}
