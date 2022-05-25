package com.example.community.domain.account.service;


import com.example.community.common.component.TextToSha256Converter;
import com.example.community.common.mailing.dto.MailDto;
import com.example.community.common.mailing.service.MailService;
import com.example.community.domain.account.common.EmailValidateQuery;
import com.example.community.domain.account.common.RegisterState;
import com.example.community.domain.account.dto.RegisterDto;
import com.example.community.domain.account.dto.RegisterSignDto;
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
    private final TextToSha256Converter converter;

    private final MailService mailService;

    private final AccountProfileImageService accountProfileImageService;

    public void accountRegister(RegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        registerDto.setPassword(encodedPassword);
        String profilePath = accountProfileImageService.profileImageSave(registerDto.getProfileImg());

        Account account = Account.builder()
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .nickname(registerDto.getNickname())
                .profileImg(profilePath)
                .build();

        accountRepository.save(account);

        registerMailing(account.getId(), account.getEmail());
    }

    private void registerMailing(long id, String email) {
        String sha256 = converter.convert(email);
        String validationUri = EmailValidateQuery.of(id, sha256).toUri();

        MailDto mailDto = MailDto.builder()
                .mailAddress(email)
                .title("회원가입 완료링크")
                .message(validationUri)
                .build();
        log.info("validation url = {}",validationUri);
        //mailService.sendMail(mailDto);

    }


    public RegisterSignDto emailRegisterValidate(EmailValidateQuery query) {
        Account account;
        RegisterSignDto signDto = new RegisterSignDto();

        try {
            account = accountRepository.findById(query.getId()).orElseThrow();
        } catch (NoSuchElementException e) {
            signDto.setRegisterState(RegisterState.FAIL);
            return signDto;
        }

        if (!account.getIsEmailVerified()) {
            String sha256 = converter.convert(account.getEmail());
            if (query.validate(sha256)) {
                account.emailVerification();
                signDto.setEmail(account.getEmail());
                signDto.setNickName(account.getNickname());
                signDto.setRegisterState(RegisterState.SUCCESS);
            } else {
                signDto.setRegisterState(RegisterState.FAIL);
            }
        } else {
            signDto.setNickName(account.getNickname());
            signDto.setRegisterState(RegisterState.ALREADY_CONFIRM);
        }
        return signDto;
    }
}
