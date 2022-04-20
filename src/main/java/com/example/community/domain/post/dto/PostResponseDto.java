package com.example.community.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;
    private String postTitle;
    private String postContent;
    private String author;
    private String profilePath;
    private LocalDateTime createdBy;
    private List<TagDto> tagList;
    private Boolean isCreated;
}
