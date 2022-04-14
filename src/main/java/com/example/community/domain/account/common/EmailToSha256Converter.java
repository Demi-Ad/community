package com.example.community.domain.account.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class EmailToSha256Converter {

    private final String salt;

    public EmailToSha256Converter(@Value("${salt.key}") String salt) {
        this.salt = salt;
    }

    public String emailToSha256(String email) {
        byte[] bytes = email.concat(salt).getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes);
            return byteToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String byteToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x",b));
        }
        return stringBuilder.toString();
    }
}
