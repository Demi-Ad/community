package com.example.community.domain.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class AccountInfoDetailDto {
    private String nickname;
    private String profile;

    private int postWriteCount;
    private int commentWriteCount;
    private int guestBookWriteCount;


    public AccountInfoDetailDto(String nickname, String email, String profile, int postWriteCount, int commentWriteCount, int guestBookWriteCount) {
        this.nickname = nickname;
        setProfile(profile);
        this.postWriteCount = postWriteCount;
        this.commentWriteCount = commentWriteCount;
        this.guestBookWriteCount = guestBookWriteCount;
    }




    public void setProfile(String profile) {
        this.profile = "/profile/" +  profile;
    }
}
