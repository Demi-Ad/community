package com.example.community.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentEditResponseDto {
    private Long postId;
    private Long commentId;
}
