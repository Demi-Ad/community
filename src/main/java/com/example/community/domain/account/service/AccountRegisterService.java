package com.example.community.domain.account.service;


import com.example.community.common.component.TextToSha256Converter;
import com.example.community.domain.account.dto.RegisterDto;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@Slf4j
public class AccountRegisterService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private final AccountProfileImageService accountProfileImageService;

    public AccountRegisterService(AccountRepository accountRepository,
                                  PasswordEncoder passwordEncoder,
                                  TextToSha256Converter converter,
                                  AccountProfileImageService accountProfileImageService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountProfileImageService = accountProfileImageService;
    }

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
    }
}
