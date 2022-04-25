package com.example.community.domain.post.service;

import com.example.community.common.component.ImageUploadComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class PostImageUploadService {

    private final String savePath;
    private final ImageUploadComponent imageUploadComponent;

    @Autowired
    public PostImageUploadService(@Value("${static.postImg.save-path}") String savePath, ImageUploadComponent imageUploadComponent) {
        this.savePath = savePath;
        this.imageUploadComponent = imageUploadComponent;
    }

    public String saveToUri(MultipartFile multipartFile) {
        String path = imageUploadComponent.saveImg(multipartFile, savePath);
        if (path == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지 파일이 아닙니다");
        }
        log.info(path);
        return "/img/"+path;
    }
}
