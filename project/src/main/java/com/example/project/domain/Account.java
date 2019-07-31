package com.example.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.example.project.enums.UserRole;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "ACCOUNT")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "ACCOUNT_ID", nullable = false, unique = true)
	@NotBlank(message = "아이디는 비워둘 수 없습니다.")
	private String userId;
	
	@Column(name = "ACCOUNT_USERNAME", nullable = false)
	@NotBlank(message = "이름은 비워둘 수 없습니다.")
	private String username;
	
	@Column(name = "ACCOUNT_PASSWORD", nullable = false)
	@NotBlank(message = "비밀번호는 비워 둘 수 없브니다.")
	private String password;
	
	@Column(name = "ACCOUN_ROLE", nullable = false)
	@Enumerated(value = EnumType.STRING)
	public UserRole userRole = UserRole.USER; 
	
	@Builder
	public Account(String userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}
}
