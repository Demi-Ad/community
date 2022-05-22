package com.example.community.admin.forbiddenWord.entity;

import com.example.community.admin.forbiddenWord.dto.ForbiddenWordDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForbiddenWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forbidden_word_id")
    private Long id;

    private String forbiddenText;

    private Boolean isPostForbidden;

    private Boolean isCommentForbidden;

    private Boolean isGuestBookForbidden;


    public ForbiddenWord(String forbiddenText, Boolean isPostForbidden, Boolean isCommentForbidden, Boolean isGuestBookForbidden) {
        this.forbiddenText = forbiddenText;
        this.isPostForbidden = isPostForbidden;
        this.isCommentForbidden = isCommentForbidden;
        this.isGuestBookForbidden = isGuestBookForbidden;
    }

    public ForbiddenWordDto toDto() {
        return ForbiddenWordDto.builder()
                .id(id)
                .forbiddenWord(forbiddenText)
                .isPostForbidden(isPostForbidden)
                .isCommentForbidden(isCommentForbidden)
                .isGuestBookForbidden(isGuestBookForbidden)
                .build();
    }


}
