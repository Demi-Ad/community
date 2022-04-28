package com.example.community.common.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class StaticPathContext {
    private final String imgPath;
    private final String filePath;

    @Autowired
    public StaticPathContext(@Value("${static.postImg.save-path}") String imgPath, @Value("${static.upload.save-path}") String filePath) {
        this.imgPath = imgPath;
        this.filePath = filePath;
    }
}
