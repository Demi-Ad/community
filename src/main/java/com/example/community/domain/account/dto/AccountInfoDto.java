package com.example.community.domain.account.dto;

import com.example.community.domain.account.projection.AccountInfoProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class AccountInfoDto {
    private String nickname;
    private String email;
    private String profile;

    private int postWriteCount;
    private int commentWriteCount;


    public AccountInfoDto(String nickname, String email, String profile, int postWriteCount, int commentWriteCount) {
        this.nickname = nickname;
        this.email = email;
        setProfile(profile);
        this.postWriteCount = postWriteCount;
        this.commentWriteCount = commentWriteCount;
    }


    public AccountInfoDto(AccountInfoProjection accountInfoProjection) {
        this.nickname = accountInfoProjection.getNickname();
        this.email = accountInfoProjection.getEmail();
        setProfile(accountInfoProjection.getProfileImg());
    }

    public void setProfile(String profile) {
        this.profile = "/profile/" +  profile;
    }
}
