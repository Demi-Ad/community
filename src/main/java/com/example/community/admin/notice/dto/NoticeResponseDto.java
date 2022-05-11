package com.example.community.admin.notice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NoticeResponseDto {
    private Long id;
    private String title;
    private String content;
    private String adminName;
    private LocalDateTime createAt;


    public NoticeResponseDto(String title, String content, LocalDateTime createAt, String adminName) {
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.adminName = adminName;
    }

    public NoticeResponseDto(Long id,String title, LocalDateTime createAt, String adminName) {
        this.id = id;
        this.title = title;
        this.adminName = adminName;
        this.createAt = createAt;
    }
}
