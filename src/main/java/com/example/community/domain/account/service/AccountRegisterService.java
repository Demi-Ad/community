package com.example.community.domain.account.service;

import com.example.community.common.mailing.dto.MailDto;
import com.example.community.common.mailing.service.MailService;
import com.example.community.domain.account.common.EmailToSha256Converter;
import com.example.community.domain.account.common.EmailValidateQuery;
import com.example.community.domain.account.common.RegisterState;
import com.example.community.domain.account.dto.RegisterDto;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;


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

        MailDto mailDto = MailDto.builder()
                .mailAddress(account.getEmail())
                .title("회원가입 완료링크")
                .message(validationUri)
                .build();

        //mailService.sendMail(mailDto);
        return validationUri;
    }


    public RegisterState emailRegisterValidate(EmailValidateQuery query) {
        // TODO: 중복 클릭 확인 로직 추가 State SUCCESS, ALREADY_CONFIRM, FAIL
        Account account;
        try {
            account = accountRepository.findById(query.getId()).orElseThrow();
        } catch (NoSuchElementException e) {
            return RegisterState.FAIL;
        }

        if (account.getLock()) {
            String sha256 = converter.emailToSha256(account.getEmail());
            if (query.validate(sha256)) {
                account.unLock();
                return RegisterState.SUCCESS;
            }
        } else {
            return RegisterState.ALREADY_CONFIRM;
        }
        return RegisterState.FAIL;
    }
}
