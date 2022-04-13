package com.example.community.commontest;

import org.apache.logging.log4j.util.Base64Util;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class Base64Test {

    @Test
    void emailBase64Test() {
        String email = "aaa@aa.com";

        String encode = Base64Utils.encodeToUrlSafeString(email.getBytes(StandardCharsets.UTF_8));

        System.out.println("BASE64 :: "+encode);

        String decoded = new String(Base64Utils.decodeFromUrlSafeString(encode));

        Assertions.assertThat(decoded).isEqualTo(email);
    }
}
