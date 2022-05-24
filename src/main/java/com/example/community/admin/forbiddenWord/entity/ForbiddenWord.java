package com.example.community.admin.forbiddenWord.entity;

import com.example.community.admin.forbiddenWord.dto.ForbiddenWordDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    public void change(ForbiddenWord forbiddenWord) {
        this.forbiddenText = forbiddenWord.getForbiddenText();
        this.isPostForbidden = forbiddenWord.getIsPostForbidden();
        this.isCommentForbidden = forbiddenWord.getIsCommentForbidden();
        this.isGuestBookForbidden = forbiddenWord.getIsGuestBookForbidden();
    }


}
