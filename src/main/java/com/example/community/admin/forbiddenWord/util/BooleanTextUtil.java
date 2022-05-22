package com.example.community.admin.forbiddenWord.util;

import org.springframework.stereotype.Component;

@Component
public class BooleanTextUtil {
    public String convert(Boolean condition) {
        return condition ? "Y" : "N";
    }
}
