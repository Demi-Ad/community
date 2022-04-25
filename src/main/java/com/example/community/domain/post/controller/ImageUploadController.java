package com.example.community.domain.post.controller;

import com.example.community.domain.post.dto.ImageUploadResponseDto;
import com.example.community.domain.post.service.PostImageUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ImageUploadController {

    private final PostImageUploadService postImageUploadService;

    @PostMapping("/image/upload")
    public ImageUploadResponseDto upload(MultipartFile upload) {
        return new ImageUploadResponseDto(postImageUploadService.saveToUri(upload));
    }
}
