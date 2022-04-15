package com.example.community.config.security.auth;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDetailService implements UserDetailsService {

    private final AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        Account loginAccount = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("가입된 사용자가 아닙니다"));

        return new AccountDetail(loginAccount);
    }
}
