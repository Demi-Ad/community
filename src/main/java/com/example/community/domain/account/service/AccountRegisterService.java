package com.example.community.domain.account.service;

import com.example.community.domain.account.common.EmailToSha256Converter;
import com.example.community.domain.account.common.EmailValidateQuery;
import com.example.community.domain.account.dto.RegisterDto;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@Slf4j
public class AccountRegisterService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailToSha256Converter converter;

    private final String salt;

    @Autowired
    public AccountRegisterService(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
                                  EmailToSha256Converter converter, @Value("${salt.key}") String salt) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.converter = converter;
        this.salt = salt;
    }

    public EmailValidateQuery accountRegister(RegisterDto registerDto) {
        //TODO : 회원가입 시 /sign?id=${id}&key={Sha256}
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        registerDto.setPassword(encodedPassword);
        Account account = Account.builder()
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .nickname(registerDto.getNickname())
                .build();

        accountRepository.save(account);
        String sha256 = converter.emailToSha256(account.getEmail());
        return EmailValidateQuery.of(account.getId(), sha256);
    }


    public boolean emailRegisterValidate(EmailValidateQuery query) {
        Account account = accountRepository.findById(query.getId()).orElseThrow();
        String sha256 = converter.emailToSha256(account.getEmail());
        log.info("sha256 = {}", sha256);
        log.info("query = {}",query.getSha256());
        if (query.getSha256().equals(sha256)) {
            account.unLockUser();
            return true;
        }
        return false;
    }
}
