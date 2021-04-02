package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder   passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = this.accountRepository.findByUsername(username);

        /**
         * Username 값이 DATA DB 에 존재하지 않는 경우
         * UsernameNotFoundException 에러 메소드를 사용합니다.
         * */
        if (account.isPresent()) {
            return User.builder()
                    .username(account.get().getUsername())
                    .password(account.get().getPassword())
                    .roles(account.get().getRole().getKey())
                    .build();
        } else {
            throw new UsernameNotFoundException(username + "정보를 찾을 수 없습니다.");
        }
    }

    @Override
    public Account saveOrUpdateAccount(Account account) {
        account.encodePassword(this.passwordEncoder);

        return this.accountRepository.save(account);
    }
}
