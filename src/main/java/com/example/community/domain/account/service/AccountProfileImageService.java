package com.example.community.domain.account.service;

import com.example.community.common.component.ImageUploadComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class AccountProfileImageService {

    private final String savePath;
    private final ImageUploadComponent imageUploadComponent;

    @Autowired
    public AccountProfileImageService(@Value("${static.profile.save-path}") String savePath, ImageUploadComponent imageUploadComponent) {
        this.savePath = savePath;
        this.imageUploadComponent = imageUploadComponent;
    }

    public String profileImageSave(MultipartFile multipartFile) {
        String path = imageUploadComponent.saveImg(multipartFile, savePath);
        if (path == null) {
            return "person.png";
        }
        return path;
    }
}
