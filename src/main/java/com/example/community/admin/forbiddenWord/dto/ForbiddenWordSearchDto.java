package com.example.community.admin.forbiddenWord.dto;

import com.example.community.admin.forbiddenWord.entity.ForbiddenWord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForbiddenWordSearchDto {
    private String forbiddenText;
    private boolean isPostForbidden;
    private boolean isCommentForbidden;
    private boolean isGuestBookForbidden;


    public Specification<ForbiddenWord> createSpecification() {
        return (root, query, cb) -> {

            if (!StringUtils.hasText(forbiddenText) && !isPostForbidden && !isCommentForbidden && !isGuestBookForbidden)
                return cb.conjunction();

            Predicate p = null;

            if (StringUtils.hasText(forbiddenText)) {
                p = cb.like(root.get("forbiddenText"), "%" + forbiddenText + "%");
                if (isPostForbidden) {
                    p = cb.and(p, cb.isTrue(root.get("isPostForbidden")));
                }
                if (isCommentForbidden) {
                    p = cb.and(p, cb.isTrue(root.get("isCommentForbidden")));
                }
                if (isGuestBookForbidden) {
                    p = cb.and(p, cb.isTrue(root.get("isGuestBookForbidden")));
                }
                return p;
            }

            p = cb.disjunction();

            if (isPostForbidden) {
                p = cb.or(p, cb.isTrue(root.get("isPostForbidden")));
            }
            if (isCommentForbidden) {
                p = cb.or(p, cb.isTrue(root.get("isCommentForbidden")));
            }
            if (isGuestBookForbidden) {
                p = cb.or(p, cb.isTrue(root.get("isGuestBookForbidden")));
            }

            return p;
        };
    }
}
