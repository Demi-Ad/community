package com.example.community.common.lisnter;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Account account = Account.builder().email("votm777@naver.com")
                .password(passwordEncoder.encode("aaaa"))
                .nickname("aaaa")
                .build();

        account.unLock();
        accountRepository.save(account);
    }
}
