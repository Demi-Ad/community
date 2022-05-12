package com.example.community.admin.accountManage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountBlockRequestDto {
    private Long accountId;
    private String blockComment;
    private LocalDateTime blockDateTime;

    public AccountBlockRequestDto(Long accountId) {
        this.accountId = accountId;
    }
}
