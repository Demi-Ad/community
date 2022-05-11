package com.example.community.admin.accountManage.dto;

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
    private String profileImg;
    private String nickname;
    private LocalDateTime registeredAt;
}
