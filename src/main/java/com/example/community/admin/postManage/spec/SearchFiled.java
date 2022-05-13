package com.example.community.admin.postManage.spec;

import com.example.community.domain.post.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum SearchFiled {

    TITLE("title"),
    CONTENT("content"),
    NICKNAME("nickname"),
    EMAIL("email");

    private final String value;

    public static SearchFiled of(String filed) {

        return Arrays.stream(SearchFiled.values())
                .filter(searchFiled -> searchFiled.name().equalsIgnoreCase(filed))
                .findFirst()
                .orElse(null);
    }

    public Specification<Post> search(String value) {
        switch (this) {
            case TITLE:
                return searchTitle(value);
            case CONTENT:
                return searchContent(value);
            case NICKNAME:
                return searchAccountNickName(value);
            case EMAIL:
                return searchAccountEmail(value);
            default:
                return null;
        }
    }

    private Specification<Post> searchTitle(String value) {
        return (root, query, cb) -> cb.like(root.get("title"),"%" + value + "%");
    }

    private Specification<Post> searchContent(String value) {
        return (root, query, cb) -> cb.like(root.get("content"),"%" + value + "%");
    }

    private Specification<Post> searchAccountNickName(String value) {
        return (root, query, cb) -> {
            Join<Object, Object> account = root.join("account");
            return cb.like(account.get("nickname"),"%" + value + "%");
        };
    }

    private Specification<Post> searchAccountEmail(String value) {
        return (root, query, cb) -> {
            Join<Object, Object> account = root.join("account");
            return cb.like(account.get("email"),"%" + value + "%");
        };
    }
}
