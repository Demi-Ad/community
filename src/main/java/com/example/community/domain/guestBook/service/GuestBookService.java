package com.example.community.domain.guestBook.service;

import com.example.community.common.component.Pagination;
import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.guestBook.dto.GuestBookResponseDto;
import com.example.community.domain.guestBook.entity.GuestBook;
import com.example.community.domain.guestBook.repo.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GuestBookService {
    private final GuestBookRepository guestBookRepository;
    private final AccountRepository accountRepository;


    public Long save(String content, Long ownerId, Account guest) {
        Account owner = accountRepository.findById(ownerId).orElseThrow();

        GuestBook guestBook = GuestBook.builder()
                .owner(owner)
                .guest(guest)
                .content(content)
                .build();
        guestBookRepository.save(guestBook);
        return guest.getId();
    }

    public Pagination<GuestBookResponseDto> listGuestBook(Long ownerId, Pageable pageable) {
        Page<GuestBookResponseDto> page = guestBookRepository.findGuestBookPaging(ownerId, pageable);
        return Pagination.of(page);
    }

    public Boolean isOwner(Authentication authentication, Long ownerId) {
        if (authentication.getPrincipal() instanceof AccountDetail) {
            Account account = ((AccountDetail) authentication.getPrincipal()).getAccount();
            return account.getId().equals(ownerId);
        }
        return false;
    }



    public Long delete(Long guestBookId) {
        GuestBook guestBook = guestBookRepository.findById(guestBookId).orElseThrow();
        Long id = guestBook.getOwner().getId();
        guestBookRepository.deleteById(guestBookId);
        return id;
    }

    public boolean isOwner(Long guestBookId, AccountDetail accountDetail) {
        GuestBook guestBook = guestBookRepository.findById(guestBookId).orElseThrow();
        return guestBook.getOwner().getId().equals(accountDetail.getAccount().getId());
    }
}
