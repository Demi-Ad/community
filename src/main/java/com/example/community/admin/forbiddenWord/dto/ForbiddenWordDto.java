package com.example.community.admin.forbiddenWord.dto;

import com.example.community.admin.forbiddenWord.entity.ForbiddenWord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForbiddenWordDto {

    private Long id;
    private String forbiddenWord;

    private boolean isPostForbidden;

    private boolean isCommentForbidden;

    private boolean isGuestBookForbidden;


    public ForbiddenWord toEntity() {
        return new ForbiddenWord(forbiddenWord,isPostForbidden,isCommentForbidden,isGuestBookForbidden);
    }

    public ForbiddenWordDto(String forbiddenWord, boolean isPostForbidden, boolean isCommentForbidden, boolean isGuestBookForbidden) {
        this.forbiddenWord = forbiddenWord;
        this.isPostForbidden = isPostForbidden;
        this.isCommentForbidden = isCommentForbidden;
        this.isGuestBookForbidden = isGuestBookForbidden;
    }
}
