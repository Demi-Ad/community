package com.example.community.admin.accountManage.dto;

import com.example.community.common.component.Pagination;
import com.example.community.domain.guestBook.dto.GuestBookResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccountManageDto {
    private String email;
    private String nickName;
    private LocalDateTime registeredAt;

    private boolean isEmailVerified;
    private AccountBlockDetailDto blockDetail;

    private Pagination<GuestBookResponseDto> guestBookPageList;

    @Builder
    public AccountManageDto(String email, String nickName, LocalDateTime registeredAt, boolean isEmailVerified, AccountBlockDetailDto accountBlockDetailDto, Pagination<GuestBookResponseDto> guestBookPageList ) {
        this.email = email;
        this.nickName = nickName;
        this.registeredAt = registeredAt;
        this.isEmailVerified = isEmailVerified;
        this.blockDetail = accountBlockDetailDto;
        this.guestBookPageList = guestBookPageList;
    }
}
