package com.example.community.admin.forbiddenWord.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BooleanTextUtil {
    public String convert(Boolean condition) {
        return condition ? "Y" : "N";
    }

    public List<String> convert (Boolean ...conditions) {
        return Arrays.stream(conditions)
                .map(this::convert)
                .collect(Collectors.toList());

    }
}
