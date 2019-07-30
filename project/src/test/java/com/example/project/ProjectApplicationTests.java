package com.example.project;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project.domain.Account;
import com.example.project.enums.UserRole;
import com.example.project.repository.AccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectApplicationTests {

	@Autowired
	private AccountRepository repository;
	
	@Test
	public void contextLoads() {
		Account account = new Account(
				"ID",
				"NAME",
				"PASSWORD",
				UserRole.USER
				);
		
		repository.save(account);
		Account saved = repository.findAll().get(0);
		assertThat(saved.getUserId(), is("ID"));
		assertThat(saved.getUsername(), is("NAME"));
		assertThat(saved.getPassword(), is("PASSWORD"));
		assertThat(saved.getUserRole(), is(UserRole.USER));
	}
}
