package com.example.community.domain.post.util;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorizeCheckUtil {

    private final PostRepository postRepository;

    public boolean check(Long postId, Long accountId) {
        return postRepository.checkCreateUser(accountId,postId);
    }

    public boolean check(Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AccountDetail))
            return false;

        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();

        if (accountDetail == null)
            return false;

        return post.isCreatedUser(accountDetail.getAccount());
    }
}
