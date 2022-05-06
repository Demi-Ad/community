package com.example.community.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    @Size(min = 4, message = "비밀번호는 최소 4글자 이상")
    private String password;
    @Size(min = 4, message = "비밀번호는 최소 4글자 이상")
    private String reconfirmPassword;


    public boolean passwordEq() {
        return password.equals(reconfirmPassword);
    }
}
