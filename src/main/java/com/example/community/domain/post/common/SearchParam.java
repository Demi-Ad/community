package com.example.community.domain.post.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum SearchParam {
    TITLE("제목"),
    AUTHOR("작성자"),
    TAG("태그");

    private final String description;



    SearchParam(String description) {
        this.description = description;
    }

    public static SearchParam of(String name) {
        switch (name.toUpperCase()) {
            case "TITLE":
                return SearchParam.TITLE;
            case "AUTHOR":
                return SearchParam.AUTHOR;
            case "TAG":
                return SearchParam.TAG;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"파라미터가 옳지 않습니다");
    }

    public String getDescription() {
        return description;
    }
}
