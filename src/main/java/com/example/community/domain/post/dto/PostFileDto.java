package com.example.community.domain.post.dto;

import com.example.community.domain.post.entity.PostFile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostFileDto {
    private String convertFileName;
    private String originFileName;

    public PostFileDto(PostFile postFile) {
        this.convertFileName = postFile.getFileConvertName();
        this.originFileName = postFile.getFileOriginName();
    }
}
