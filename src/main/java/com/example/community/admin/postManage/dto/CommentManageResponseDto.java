package com.example.community.admin.postManage.dto;

import com.example.community.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentManageResponseDto {
    private Long commentId;
    private String email;
    private String content;

    public CommentManageResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.email = comment.getAccount().getEmail();
        this.content = comment.getContent();
    }
}
