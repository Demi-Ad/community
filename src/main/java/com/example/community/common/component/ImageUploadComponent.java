package com.example.community.common.component;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageUploadComponent {

    private final Environment environment;

    public String saveImg(MultipartFile multipartFile, String savePath) {

        if (multipartFile.isEmpty() || !multipartFile.getContentType().startsWith("image"))
            return null;
        boolean isDocker = Arrays.stream(environment.getActiveProfiles()).filter(s -> s.equals("docker")).count() == 1;

        String saveFileName = generateSaveFileName(multipartFile);
        File file;
        if (isDocker) {
            file = new File(savePath + saveFileName);
        } else {
            file = new File(savePath + "\\" + saveFileName);
        }
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
