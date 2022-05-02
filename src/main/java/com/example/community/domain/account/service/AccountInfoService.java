package com.example.community.domain.account.service;

import com.example.community.domain.account.dto.AccountInfoDto;
import com.example.community.domain.account.repo.AccountInfoRepository;
import com.example.community.domain.account.repo.impl.H2AccountInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountInfoService {
    private final AccountInfoRepository repository;

    public Optional<AccountInfoDto> createAccountInfo(Long userId) {
        return Optional.ofNullable(repository.projectionAccountInfo(userId));
    }
}
