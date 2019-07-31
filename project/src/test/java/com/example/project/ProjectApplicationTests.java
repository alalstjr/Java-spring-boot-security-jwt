package com.example.project;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.project.domain.Account;
import com.example.project.domain.AccountContext;
import com.example.project.enums.EnumMapper;
import com.example.project.enums.EnumValue;
import com.example.project.enums.UserRole;
import com.example.project.repository.AccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectApplicationTests {

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private AccountContext accountContext;
	
	@Autowired
    private EnumMapper enumMapper;

    @GetMapping("/mapper")
    public Map<String, List<EnumValue>> getMapper() {
        return enumMapper.getAll();
    }
	
	@Test
	public void contextLoads() {
//		Account account = new Account(
//				"ID",
//				"NAME",
//				"PASSWORD",
//				UserRole.USER
//				);
		
		accountContext.updateAccount("아이디", "비밀번호", );
		
		
		repository.save(account);
		Account saved = repository.findAll().get(0);
		assertThat(saved.getUserId(), is("ID"));
		assertThat(saved.getUsername(), is("NAME"));
		assertThat(saved.getPassword(), is("PASSWORD"));
		assertThat(saved.getUserRole(), is(UserRole.USER));
	}
}
