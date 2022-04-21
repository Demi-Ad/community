package com.example.community.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private Long postId;
    private Long commentWriteAccountId;
    private String commentContent;
    private Long parentCommentId;
}
