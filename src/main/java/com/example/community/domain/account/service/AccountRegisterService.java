package com.example.community.domain.account.service;

import com.example.community.domain.account.dto.RegisterDto;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountRegisterService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    public String accountRegister(RegisterDto dto) {
        //TODO : 회원가입 시 /register?email=${email}&key={random} 해당 링크를 이메일로 전송
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);
        Account account = Account.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .build();

        accountRepository.save(account);

        return account.getEmail();
    }

    public boolean emailRegisterValidate(String key,String value) {
        return false;
    }
}
