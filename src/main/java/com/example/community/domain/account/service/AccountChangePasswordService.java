package com.example.community.domain.account.service;

import com.example.community.config.security.util.SecurityContextUtil;
import com.example.community.domain.account.dto.ChangePasswordDto;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountChangePasswordService {
    private final AccountRepository accountRepository;
    private final SecurityContextUtil securityContextUtil;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(ChangePasswordDto changePasswordDto) {
        if (changePasswordDto.passwordEq()) {
            Account account = securityContextUtil.currentAccount();
            String password = changePasswordDto.getPassword();
            String encodePassword = passwordEncoder.encode(password);
            account.changePassword(encodePassword);
            accountRepository.save(account);
        }
    }
}
