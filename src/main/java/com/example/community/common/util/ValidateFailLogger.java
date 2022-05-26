package com.example.community.common.util;

import com.example.community.config.security.util.SecurityContextUtil;
import com.example.community.domain.account.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValidateFailLogger {

    private final SecurityContextUtil securityContextUtil;

    public void convert(Errors errors) {
        Account account = securityContextUtil.currentAccount();
        String str = errors.getAllErrors().stream()
                .map(this::prettyFormat)
                .collect(Collectors.joining(", "));
        log.warn("Validate Fail = {} {}",account.getEmail(),str);
    }

    private String prettyFormat(ObjectError objectError) {
        StringBuilder sb = new StringBuilder();
        sb.append("[filed : ")
                .append(objectError.getObjectName())
                .append(" reason : ")
                .append(objectError.getDefaultMessage())
                .append("]");

        return sb.toString();
    }
}
