package com.example.community.domain.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class AccountInfoDetailDto {
    private String nickname;
    private String email;
    private String profile;

    private int postWriteCount;
    private int commentWriteCount;
    private int guestBookWriteCount;


    public AccountInfoDetailDto(String nickname, String email, String profile, int postWriteCount, int commentWriteCount, int guestBookWriteCount) {
        this.nickname = nickname;
        this.email = email;
        setProfile(profile);
        this.postWriteCount = postWriteCount;
        this.commentWriteCount = commentWriteCount;
        this.guestBookWriteCount = guestBookWriteCount;
    }




    public void setProfile(String profile) {
        this.profile = "/profile/" +  profile;
    }
}
