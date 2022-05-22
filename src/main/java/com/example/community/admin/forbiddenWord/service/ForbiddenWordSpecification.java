package com.example.community.admin.forbiddenWord.service;

import com.example.community.admin.forbiddenWord.entity.ForbiddenWord;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public enum ForbiddenWordSpecification {
    POST("isPostForbidden"), COMMENT("isCommentForbidden"), GUEST_BOOK("isGuestBookForbidden");

    private final String filedName;
    ForbiddenWordSpecification(String filedName) {
        this.filedName = filedName;
    }

    public Specification<ForbiddenWord> search(String searchText) {
        Specification<ForbiddenWord> specification = Specification.where((root, query, cb) -> cb.isTrue(root.get(this.filedName)));

        if (searchText != null) {
            specification.and(((root, query, cb) -> cb.like(root.get("forbiddenText"), searchText)));
        }

        return specification;
    }

    public Specification<ForbiddenWord> search() {
        return (root, query, cb) -> cb.isTrue(root.get(this.filedName));
    }
}
