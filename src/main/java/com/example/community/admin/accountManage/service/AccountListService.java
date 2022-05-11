package com.example.community.admin.accountManage.service;

import com.example.community.admin.accountManage.dto.AccountDto;
import com.example.community.common.component.Pagination;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountListService {
    private final AccountRepository accountRepository;


    public Pagination<AccountDto> accountList(Pageable pageable) {
        return null;
    }
}
