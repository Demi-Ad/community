package com.example.community.config.security.auth;

import com.example.community.admin.accountManage.entity.AccountBlock;
import com.example.community.admin.accountManage.repo.AccountBlockRepository;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final AccountBlockRepository accountBlockRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account loginAccount = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("가입된 사용자가 아닙니다"));

        if (accountBlockRepository.existsByBlockAccount(loginAccount)) {
            AccountBlock accountBlock = accountBlockRepository.findByBlockAccount(loginAccount).orElseThrow();
            LocalDateTime blockUntilDate = accountBlock.getBlockUntilDate();
            boolean isBlockAfter = blockUntilDate.isAfter(LocalDateTime.now());
            if (isBlockAfter) {
                accountBlockRepository.deleteById(accountBlock.getId());
                return new AccountDetail(loginAccount,false);
            }
            return new AccountDetail(loginAccount,true);
        }
        return new AccountDetail(loginAccount,false);
    }
}
