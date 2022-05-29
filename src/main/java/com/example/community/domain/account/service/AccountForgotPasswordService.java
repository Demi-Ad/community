package com.example.community.domain.account.service;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
@Slf4j
public class AccountForgotPasswordService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    public AccountForgotPasswordService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String changePassword(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow();
        String generatePassword = tempPasswordGenerate();
        account.changePassword(passwordEncoder.encode(generatePassword));
        return generatePassword;
    }

    private String tempPasswordGenerate() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i ++) {
            int index = rand.nextInt(3);
            switch(index) {
                case 0:
                    sb.append((char)(rand.nextInt(26) + 97));
                    break;
                case 1:
                    sb.append((char)(rand.nextInt(26) + 65));
                    break;
                case 2:
                    sb.append(rand.nextInt(10));
                    break;
            }
        }
        return sb.toString();
    }
}
