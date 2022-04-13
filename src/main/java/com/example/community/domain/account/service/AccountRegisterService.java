package com.example.community.domain.account.service;

import com.example.community.domain.account.dto.AccountDto;
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

    @Transactional(readOnly = true)
    public boolean existsEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public String accountRegister(AccountDto dto) {
        //TODO : 회원가입 시 /register?email=${email}&key={random} 해당 링크를 이메일로 전송
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);
        Account registerAccount = accountRepository.save(dto.of());
        String email = registerAccount.getEmail();

        return null;
    }

    public boolean emailRegisterValidate(String key,String value) {
        return false;
    }
}
