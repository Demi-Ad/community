package com.example.community.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchPreviewDto {
    private String img;
    private String item;
    private String link;
}
