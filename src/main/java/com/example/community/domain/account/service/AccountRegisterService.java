package com.example.community.domain.account.service;

import com.example.community.common.mailing.dto.MailDto;
import com.example.community.common.mailing.service.MailService;
import com.example.community.domain.account.common.EmailToSha256Converter;
import com.example.community.domain.account.common.EmailValidateQuery;
import com.example.community.domain.account.dto.RegisterDto;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccountRegisterService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailToSha256Converter converter;

    private final MailService mailService;

    public String accountRegister(RegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        registerDto.setPassword(encodedPassword);
        Account account = Account.builder()
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .nickname(registerDto.getNickname())
                .build();

        accountRepository.save(account);
        String sha256 = converter.emailToSha256(account.getEmail());
        String validationUri = EmailValidateQuery.of(account.getId(), sha256).toUri();

        MailDto mailDto = new MailDto(account.getEmail(),"회원 가입 완료 링크",validationUri);
        mailService.sendMail(mailDto);
        return EmailValidateQuery.of(account.getId(), sha256).toUri();
    }


    public boolean emailRegisterValidate(EmailValidateQuery query) {
        Account account = accountRepository.findById(query.getId()).orElseThrow();
        String sha256 = converter.emailToSha256(account.getEmail());
        log.info("sha256 = {}", sha256);
        log.info("query = {}",query.getSha256());
        if (query.validate(sha256)) {
            account.unLockUser();
            return true;
        }
        return false;
    }
}
