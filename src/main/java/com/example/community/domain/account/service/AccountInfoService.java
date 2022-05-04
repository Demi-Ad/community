package com.example.community.domain.account.service;

import com.example.community.domain.account.dto.AccountInfoBasicDto;
import com.example.community.domain.account.dto.AccountInfoDetailDto;
import com.example.community.domain.account.projection.AccountInfoProjection;
import com.example.community.domain.account.repo.AccountInfoRepository;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountInfoService {
    private final AccountInfoRepository accountInfoRepository;
    private final AccountRepository accountRepository;

    public AccountInfoDetailDto createAccountInfo(Long userId) {
        return accountInfoRepository.projectionAccountInfo(userId).orElseThrow(this::BadRequestSupplier);
    }

    public AccountInfoBasicDto projectionAccountInfo(Long userId) {
        AccountInfoProjection accountInfoProjection = accountRepository.searchById(userId)
                .orElseThrow(this::BadRequestSupplier);

        return new AccountInfoBasicDto(accountInfoProjection);
    }

    private RuntimeException BadRequestSupplier() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지않는 유저");
    }
}
