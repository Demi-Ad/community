package com.example.community.domain.account.service;

import com.example.community.common.mailing.dto.MailDto;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountForgotPasswordService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isExistsAccount(String email) {
        return accountRepository.existsByEmailEquals(email);
    }


    public void changePassword(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow();
        String generatePassword = tempPasswordGenerate();
        account.changePassword(passwordEncoder.encode(generatePassword));
        MailDto mailDto = new MailDto(email,"임시비밀번호 발급 안내","임시비밀번호 = [" + generatePassword + "]");
        log.info("change password = {}",generatePassword);
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
