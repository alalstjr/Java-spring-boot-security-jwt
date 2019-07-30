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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ACCOUNT")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "ACCOUNT_ID", nullable = false)
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
	
	public Account(String userId, String username, String password, UserRole role) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userRole = role;
	}
}
