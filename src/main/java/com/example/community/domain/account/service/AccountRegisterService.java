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

    private final AccountProfileImageService accountProfileImageService;

    public void accountRegister(RegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        log.info(registerDto.toString());

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
        String sha256 = converter.emailToSha256(email);
        String validationUri = EmailValidateQuery.of(id, sha256).toUri();

        MailDto mailDto = MailDto.builder()
                .mailAddress(email)
                .title("회원가입 완료링크")
                .message(validationUri)
                .build();
        log.info("validation url = {}",validationUri);
        //mailService.sendMail(mailDto);

    }


    public RegisterState emailRegisterValidate(EmailValidateQuery query) {
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
