package com.example.community.domain.post.util;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorizeCheckUtil {

    private final PostRepository postRepository;

    public boolean check(Long postId, AccountDetail accountDetail) {
        if (accountDetail == null)
            return false;

        Post post = postRepository.findByIdJoinAccount(postId).orElseThrow();
        return post.isCreatedUser(accountDetail.getAccount());
    }
}
