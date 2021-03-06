package com.example.community.domain.postFile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;

@Data
@AllArgsConstructor
public class DownloadFileDto {
    private Resource resource;
    private String originName;
}
