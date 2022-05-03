package com.example.community.domain.account.service;

import com.example.community.domain.account.dto.AccountInfoDto;
import com.example.community.domain.account.repo.AccountInfoRepository;
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
    private final AccountInfoRepository repository;

    public AccountInfoDto createAccountInfo(Long userId) {
        return repository.projectionAccountInfo(userId).orElseThrow(this::BadRequestSupplier);
    }

    private RuntimeException BadRequestSupplier() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재 하지않는 유저");
    }
}
