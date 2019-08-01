package com.example.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import com.example.project.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
}
