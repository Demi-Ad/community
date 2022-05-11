package com.example.community.admin.notice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NoticeAdminResponseDto {
    private Long noticeId;
    private String title;
    private String content;
    private String adminName;
    private LocalDateTime createAt;

    public NoticeAdminResponseDto(Long noticeId, String title, String content, String adminName, LocalDateTime createAt) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.adminName = adminName;
        this.createAt = createAt;
    }
}
