package com.example.community.admin.postManage.dto;

import com.example.community.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostManageResponseDto {
    private Long id;
    private String title;

    private String email;
    private LocalDateTime createdAt;

    public PostManageResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.email = post.getAccount().getEmail();
        this.createdAt = post.getCreatedAt();
    }
}
