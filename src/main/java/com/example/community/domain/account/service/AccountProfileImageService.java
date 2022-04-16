package com.example.community.domain.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class AccountProfileImageService {

    private final String savePath;

    @Autowired
    public AccountProfileImageService(@Value("${static.path.save-path}") String savePath) {
        this.savePath = savePath;

    }

    public String profileImageSave(MultipartFile multipartFile) {

        if (multipartFile.isEmpty() || !multipartFile.getContentType().startsWith("image"))
            return "";

        String saveFileName = generateSaveFileName(multipartFile);
        File file = new File(savePath + "\\" + saveFileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return saveFileName;
    }

    private String generateSaveFileName(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String[] split = originalFilename.split("\\.");
        String extension = split[split.length - 1] != null ? split[split.length - 1] : "";
        return UUID.randomUUID() + "." + extension;
    }

}
