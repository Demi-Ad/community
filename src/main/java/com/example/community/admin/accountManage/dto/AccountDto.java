package com.example.community.admin.accountManage.dto;

import com.example.community.domain.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
    private Long id;
    private String email;
    private Boolean isEmailVerified;
    private String nickname;
    private LocalDateTime registeredAt;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.isEmailVerified = account.getIsEmailVerified();
        this.nickname = account.getNickname();
        this.registeredAt = account.getRegisteredAt();
    }
}
