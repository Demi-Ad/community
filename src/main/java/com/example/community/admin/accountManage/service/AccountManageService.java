package com.example.community.admin.accountManage.service;

import com.example.community.admin.accountManage.dto.AccountBlockDetailDto;
import com.example.community.admin.accountManage.dto.AccountBlockRequestDto;
import com.example.community.admin.accountManage.dto.AccountDto;
import com.example.community.admin.accountManage.dto.AccountManageDto;
import com.example.community.admin.accountManage.entity.AccountBlock;
import com.example.community.admin.accountManage.repo.AccountBlockRepository;
import com.example.community.common.component.Pagination;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.guestBook.dto.GuestBookResponseDto;
import com.example.community.domain.guestBook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountManageService {
    private final AccountRepository accountRepository;
    private final AccountBlockRepository accountBlockRepository;

    private final GuestBookService guestBookService;

    public Pagination<AccountDto> accountList(Pageable pageable) {
        Page<AccountDto> pageList = accountRepository.listManageAccount(pageable);
        return Pagination.of(pageList);
    }

    public AccountManageDto accountDetailInformation(Long accountId, Pageable pageable) {
        Account account = accountRepository.findById(accountId).orElseThrow();

        AccountManageDto.AccountManageDtoBuilder builder = AccountManageDto.builder()
                .email(account.getEmail())
                .nickName(account.getNickname())
                .isEmailVerified(account.getIsEmailVerified())
                .registeredAt(account.getRegisteredAt());

        if (accountBlockRepository.existsByBlockAccount(account)) {
            AccountBlockDetailDto accountBlockDetailDto = accountBlockRepository.findByBlockAccount(account)
                    .map(AccountBlockDetailDto::blocked)
                    .orElseThrow();

            builder.accountBlockDetailDto(accountBlockDetailDto);
        } else {
            builder.accountBlockDetailDto(AccountBlockDetailDto.notBlocked());
        }

        Pagination<GuestBookResponseDto> guestBookList = guestBookService.listGuestBook(accountId, pageable);
        builder.guestBookPageList(guestBookList);

        return builder.build();
    }

    @Transactional
    public void blockAccount(AccountBlockRequestDto accountBlockRequestDto) {

        Account account = accountRepository.findById(accountBlockRequestDto.getAccountId()).orElseThrow();

        AccountBlock accountBlock = accountBlockRepository.findByBlockAccount(account)
                .orElseGet(() -> new AccountBlock(accountBlockRequestDto.getBlockComment(), accountBlockRequestDto.getBlockDateTime()));


        if (accountBlock.getId() == null) {
            accountBlock.blockAccount(account);
            accountBlockRepository.save(accountBlock);
        } else {
            accountBlock.changeBlock(accountBlockRequestDto.getBlockComment(), accountBlockRequestDto.getBlockDateTime());
        }
    }

    @Transactional
    public void unblockAccount(Long id) {
        accountBlockRepository.deleteBlockAccount(id);
    }
}
