package com.example.community.domain.guestBook.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GuestBookResponseDto {
    private Long sequence;
    private String guestNickName;
    private LocalDateTime createdAt;
    private String content;

    public GuestBookResponseDto(Long sequence, String content, String guestNickName, LocalDateTime createdAt) {
        this.sequence = sequence;
        this.guestNickName = guestNickName;
        this.content = content;
        this.createdAt = createdAt;
    }
}
