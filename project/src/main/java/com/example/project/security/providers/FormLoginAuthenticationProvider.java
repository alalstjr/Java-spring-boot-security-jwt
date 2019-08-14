package com.example.project.security.providers;

import java.util.NoSuchElementException;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project.domain.Account;
import com.example.project.repository.AccountRepository;
import com.example.project.security.context.AccountContext;
import com.example.project.security.tokens.PostAuthorizationToken;
import com.example.project.security.tokens.PreAuthorizationToken;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {
	
	private AccountRepository accountRepository;
	
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		PreAuthorizationToken token = (PreAuthorizationToken)authentication;
		
		String username = token.getUsername();
		String password = token.getUserPassword();
		
		Account account = accountRepository
				.findByUserId(username)
				.orElseThrow(() -> new NoSuchElementException("정보에 맞는 계정이 없습니다."));
		
		if(isCorrectPassword(password, account)) {
			return PostAuthorizationToken
					.getTokenFormAccountContext(AccountContext.fromAccountModel(account));
		}

		// 이곳까지 통과하지 못하면 잘못된 요청으로 접근하지 못한것 그러므로 throw 해줘야 한다.
		throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PreAuthorizationToken.class.isAssignableFrom(authentication);
	}

	private boolean isCorrectPassword(String password, Account account) {
		// 비교대상이 앞에와야 한다.
		return passwordEncoder.matches(password, account.getPassword());
	}
}
