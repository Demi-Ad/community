package com.example.community.common.component;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ImageUploadComponent {

    public String saveImg(MultipartFile multipartFile, String savePath) {

        if (multipartFile.isEmpty() || !multipartFile.getContentType().startsWith("image"))
            return null;

        String saveFileName = generateSaveFileName(multipartFile);
        File file = new File(savePath + "\\" + saveFileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return saveFileName;
    }

    private String generateSaveFileName(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        return UUID.randomUUID() + "." + extension;
    }
}
