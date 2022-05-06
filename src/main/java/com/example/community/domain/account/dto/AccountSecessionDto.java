package com.example.community.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSecessionDto {
    @Email(message = "가입하신 이메일을 확인해주세요")
    private String emailConfirm;
    @AssertTrue(message = "동의하지 않으면 탈퇴가 불가능합니다")
    private boolean allDeleteCheck;

}
