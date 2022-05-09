package com.example.community.domain.notification.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseWrapper<T> {
    private final List<T> data;

    public ResponseWrapper(List<T> data) {
        this.data = data;
    }

    public static <T> ResponseWrapper<T> of(List<T> data) {
        return new ResponseWrapper<>(data);
    }
}
