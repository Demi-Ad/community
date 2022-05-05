package com.example.community.config.security.util.exception;

public class SecurityContextNotFoundException extends RuntimeException {

    public SecurityContextNotFoundException() {
    }

    public SecurityContextNotFoundException(String message) {
        super(message);
    }

    public SecurityContextNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityContextNotFoundException(Throwable cause) {
        super(cause);
    }

    public SecurityContextNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
