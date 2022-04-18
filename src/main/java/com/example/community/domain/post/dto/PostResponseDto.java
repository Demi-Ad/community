package com.example.community.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {

    private String postTitle;
    private String postContent;
    private String author;
    private String profilePath;
    private List<TagDto> tagList;

}
