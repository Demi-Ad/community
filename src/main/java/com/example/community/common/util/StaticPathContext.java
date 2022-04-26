package com.example.community.common.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class StaticPathContext {
    private final String imgPath;

    @Autowired
    public StaticPathContext(@Value("${static.postImg.save-path}") String imgPath) {
        this.imgPath = imgPath;
    }
}
