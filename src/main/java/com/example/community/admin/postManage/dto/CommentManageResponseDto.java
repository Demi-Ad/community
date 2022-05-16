package com.example.community.admin.postManage.dto;

import com.example.community.domain.comment.entity.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentManageResponseDto {
    private Long commentId;
    private String email;
    private String content;

    private Long parentCommentId;

    public CommentManageResponseDto(Comment comment) {
        this.commentId = comment.getId();
        if (comment.getAccount() == null) {
            this.email = "삭제 된 글";
        } else {
            this.email = comment.getAccount().getEmail();
        }
        this.content = comment.getContent();
        if (comment.getParentComment() != null) {
            this.parentCommentId = comment.getParentComment().getId();
        } else {
            this.parentCommentId = null;
        }
    }
}
