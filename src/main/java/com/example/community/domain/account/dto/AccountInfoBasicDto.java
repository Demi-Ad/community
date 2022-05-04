package com.example.community.domain.account.dto;

import com.example.community.domain.account.projection.AccountInfoProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfoBasicDto {
    private String nickname;
    private String email;
    private String profile;

    public AccountInfoBasicDto(AccountInfoProjection accountInfoProjection) {
        this.nickname = accountInfoProjection.getNickname();
        this.email = accountInfoProjection.getEmail();
        setProfile(accountInfoProjection.getProfileImg());
    }

    public void setProfile(String profile) {
        this.profile = "/profile/" +  profile;
    }

}
