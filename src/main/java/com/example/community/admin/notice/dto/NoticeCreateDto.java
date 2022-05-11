package com.example.community.admin.notice.dto;

import com.example.community.admin.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeCreateDto {
    private String title;
    private String content;

    public Notice toEntity() {
        return new Notice(title,content);
    }
}
