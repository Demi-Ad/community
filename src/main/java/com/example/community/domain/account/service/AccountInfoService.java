package com.example.community.domain.account.service;

import com.example.community.common.exceptionSupplier.ExceptionSupplier;
import com.example.community.domain.account.dto.AccountInfoBasicDto;
import com.example.community.domain.account.dto.AccountInfoDetailDto;
import com.example.community.domain.account.projection.AccountInfoProjection;
import com.example.community.domain.account.repo.AccountInfoRepository;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountInfoService {
    private final AccountInfoRepository accountInfoRepository;
    private final AccountRepository accountRepository;

    public AccountInfoDetailDto createAccountInfo(Long userId) {
        return accountInfoRepository.projectionAccountInfo(userId).orElseThrow(ExceptionSupplier::supply400);
    }

    public AccountInfoBasicDto projectionAccountInfo(Long userId) {
        AccountInfoProjection accountInfoProjection = accountRepository.searchById(userId)
                .orElseThrow(ExceptionSupplier::supply400);
        return new AccountInfoBasicDto(accountInfoProjection);
    }
}
