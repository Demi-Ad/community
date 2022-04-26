package com.example.community.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    @Size(min = 2,message = "제목은 2글자 이상입니다")
    @NotEmpty(message = "제목은 비어있을 수 없습니다")
    private String title;
    @NotEmpty(message = "본문은 비어있을 수 없습니다")
    private String content;
    private String tagJoiningStr;
    private List<MultipartFile> uploadFiles;

    public PostRequestDto(String title, String content, String tagJoiningStr) {
        this.title = title;
        this.content = content;
        this.tagJoiningStr = tagJoiningStr;
    }
}
