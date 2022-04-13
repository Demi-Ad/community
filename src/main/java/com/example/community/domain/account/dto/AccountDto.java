package com.example.community.domain.account.dto;

import com.example.community.domain.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
    @NotNull
    @NotEmpty
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;
    @NotEmpty
    @NotNull
    private String password;
    private String profileImg;


    public Account of() {
        return Account.builder()
                .email(email)
                .password(password)
                .profileImg(profileImg)
                .build();
    }
}
