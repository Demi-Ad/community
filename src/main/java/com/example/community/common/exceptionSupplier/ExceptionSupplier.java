package com.example.community.common.exceptionSupplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExceptionSupplier {

    public static ResponseStatusException supply400() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public static ResponseStatusException supply404() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public static ResponseStatusException supply403() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
