package com.example.project;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project.domain.Account;
import com.example.project.repository.AccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectApplicationTests {

	@Autowired
	private AccountRepository accountRepository;
	
	@After
	public void cleanup() {
        /** 
        	이후 테스트 코드에 영향을 끼치지 않기 위해 
        	테스트 메소드가 끝날때 마다 respository 전체 비우는 코드
        **/
		accountRepository.deleteAll();
	}

	@Test
	public void userInsert() {
		accountRepository.save(
				Account.builder()
				.userId("유저아이디")
				.username("유저이름")
				.password("비밀번호")
				.build()
				);
		
		// when
		List<Account> userList = accountRepository.findAll();
		
		// then
		Account account = userList.get(0);
		assertThat(account.getUserId(), is("유저아이디"));
		assertThat(account.getUsername(), is("유저이름"));
		assertThat(account.getPassword(), is("비밀번호"));
	}
}
