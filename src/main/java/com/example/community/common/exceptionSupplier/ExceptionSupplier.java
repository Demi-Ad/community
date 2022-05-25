package com.example.community.common.exceptionSupplier;

import com.example.community.config.security.auth.AccountDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ExceptionSupplier {

    public static ResponseStatusException supply400() {
        return ExceptionSupplier.logging(HttpStatus.BAD_REQUEST);
    }

    public static ResponseStatusException supply404() {
        return ExceptionSupplier.logging(HttpStatus.NOT_FOUND);
    }

    public static ResponseStatusException supply403() {
        return  ExceptionSupplier.logging(HttpStatus.UNAUTHORIZED);

    }

    private static ResponseStatusException logging(HttpStatus httpStatus) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userName = currentUserName();

        Map<String, String> requestAttr = getRequestAttr(request);
        String uri = requestAttr.getOrDefault("uri", "N/A");
        String method = requestAttr.getOrDefault("method", "N/A");
        String ip = requestAttr.getOrDefault("ip", "N/A");

        log.warn("httpStatus = {} uri = {} method = {} user = {}, ip = {}", httpStatus,uri,method,userName, ip);

        return new ResponseStatusException(httpStatus);
    }

    private static String currentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        String userName;

        if (principal instanceof AccountDetail) {
            AccountDetail accountDetail = (AccountDetail) principal;
            userName = accountDetail.getAccount().getEmail();
        } else {
            userName = authentication.getName();
        }
        return userName;
    }

    private static String getIpAddress(HttpServletRequest request) {
        return request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
    }

    private static Map<String,String> getRequestAttr(HttpServletRequest request) {
        Map<String,String> map = new HashMap<>();

        map.put("uri",request.getRequestURI());
        map.put("method",request.getMethod());
        map.put("ip",getIpAddress(request));
        return map;
    }
}
