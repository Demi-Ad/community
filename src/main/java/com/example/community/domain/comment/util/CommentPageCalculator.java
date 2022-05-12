package com.example.community.domain.comment.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommentPageCalculator {

    public int calc(double number) {
        log.info("Calc = {}", number);
        if (number == 0) {
            return 1;
        }
        return ((int) Math.ceil(number));
    }
}
