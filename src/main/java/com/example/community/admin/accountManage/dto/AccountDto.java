package com.example.community.admin.accountManage.dto;

import com.example.community.domain.account.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class AccountDto implements Serializable {
    private Long id;
    private String email;
    private Boolean isEmailVerified;

    private Boolean isBlocked;
    private String nickname;
    private LocalDateTime registeredAt;

    public AccountDto(Account account, boolean isBlocked) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.isBlocked = isBlocked;
        this.isEmailVerified = account.getIsEmailVerified();
        this.nickname = account.getNickname();
        this.registeredAt = account.getRegisteredAt();
    }

    public AccountDto(Long id, String email, Boolean isEmailVerified, Boolean isBlocked, String nickname, LocalDateTime registeredAt) {
        this.id = id;
        this.email = email;
        this.isEmailVerified = isEmailVerified;
        this.isBlocked = isBlocked;
        this.nickname = nickname;
        this.registeredAt = registeredAt;
    }
}
