package com.example.community.domain.account.dto;

import com.example.community.domain.account.common.RegisterState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterSignDto {
    private RegisterState registerState;
    private String nickName;
    private String email;
}
