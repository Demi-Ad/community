package com.example.community.common.component;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

@Component
public class EmailToBase64Converter {

    public String emailToBase64(String email) {
        return Base64Utils.encodeToUrlSafeString(email.getBytes(StandardCharsets.UTF_8));
    }

    public String Base64ToEmail(String base64) {
        return new String(Base64Utils.decodeFromUrlSafeString(base64));
    }
}
