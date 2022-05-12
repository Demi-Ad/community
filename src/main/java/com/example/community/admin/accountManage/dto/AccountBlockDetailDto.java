package com.example.community.admin.accountManage.dto;

import com.example.community.admin.accountManage.entity.AccountBlock;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountBlockDetailDto {
    private boolean isBlock;
    private String blockComment;
    private LocalDateTime blockedDate;
    private LocalDateTime untilDate;

    public AccountBlockDetailDto() {
        this.isBlock = false;
    }

    public AccountBlockDetailDto(String blockComment, LocalDateTime blockedDate, LocalDateTime untilDate) {
        this.isBlock = true;
        this.blockComment = blockComment;
        this.blockedDate = blockedDate;
        this.untilDate = untilDate;
    }

    public static AccountBlockDetailDto notBlocked() {
        return new AccountBlockDetailDto();
    }

    public static AccountBlockDetailDto blocked(AccountBlock accountBlock) {
        return new AccountBlockDetailDto(accountBlock.getBlockComment(), accountBlock.getBlockDate(), accountBlock.getBlockUntilDate());
    }
}
