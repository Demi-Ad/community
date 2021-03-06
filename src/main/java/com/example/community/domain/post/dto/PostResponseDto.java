package com.example.community.domain.post.dto;

import com.example.community.common.component.Pagination;
import com.example.community.domain.comment.dto.CommentResponseDto;
import com.example.community.domain.postFile.dto.PostFileDto;
import com.example.community.domain.postTag.dto.TagDto;
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
    private Long authorId;
    private String profilePath;
    private LocalDateTime createdBy;
    private Boolean isCreated;
    private Long likeCount;
    private List<TagDto> tagList;
    private Pagination<CommentResponseDto> commentResponseDtoList;
    private List<PostFileDto> uploadFileLink;
}
