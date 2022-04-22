package com.example.community.domain.comment.dto;

import com.example.community.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private String author;
    private String authorProfile;
    private String content;
    private LocalDateTime createAt;
    private List<CommentResponseDto> childrenCommentList = new ArrayList<>();

    public CommentResponseDto(Comment comment) {
        this.author = comment.getAccount().getNickname();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.authorProfile = comment.getAccount().getProfileImg();
        comment.getChildrenComment().stream()
                .map(childComment -> CommentResponseDto.builder()
                        .author(childComment.getAccount().getNickname())
                        .content(childComment.getContent())
                        .authorProfile(childComment.getAccount().getProfileImg())
                        .createAt(childComment.getCreatedAt())
                        .build())
                .forEach(this.childrenCommentList::add);
    }

    @Builder
    public CommentResponseDto(String author, String content,String authorProfile, LocalDateTime createAt) {
        this.author = author;
        this.authorProfile = authorProfile;
        this.content = content;
        this.createAt = createAt;
    }
}
