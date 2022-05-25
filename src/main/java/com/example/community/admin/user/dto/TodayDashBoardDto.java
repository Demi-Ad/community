package com.example.community.admin.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodayDashBoardDto {
    private Long newPost;
    private Long newComment;
    private Long newAccount;
}
