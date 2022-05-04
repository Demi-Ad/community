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
    private int guestBookWriteCount;


    public AccountInfoDto(String nickname, String email, String profile, int postWriteCount, int commentWriteCount, int guestBookWriteCount) {
        this.nickname = nickname;
        this.email = email;
        setProfile(profile);
        this.postWriteCount = postWriteCount;
        this.commentWriteCount = commentWriteCount;
        this.guestBookWriteCount = guestBookWriteCount;
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
