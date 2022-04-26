package com.example.community.domain.post.util;

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
