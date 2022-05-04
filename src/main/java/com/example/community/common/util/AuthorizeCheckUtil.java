package com.example.community.common.util;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthorizeCheckUtil {

    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    public boolean check(Long postId, Long accountId) {
        return postRepository.checkCreateUser(accountId,postId);
    }

    public boolean check(Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isNotLoginUser(authentication))
            return false;

        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();

        return post.isCreatedUser(accountDetail.getAccount());
    }

    public boolean check(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isNotLoginUser(authentication))
            return false;

        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();

        String[] split = request.getRequestURI().split("/");
        Long id = Long.parseLong(split[split.length - 1]);

        return accountDetail.getAccount().getId().equals(id);

    }

    public boolean check(AccountDetail accountDetail, Long id) {
        if (accountDetail == null) {
            return false;
        }
        log.info(accountDetail.getAccount().toString());
        return accountDetail.getAccount().getId().equals(id);
    }

    private boolean isNotLoginUser(Authentication authentication) {
        return authentication == null || !(authentication.getPrincipal() instanceof AccountDetail);
    }

    public boolean isLoginUser(Authentication authentication) {
        return authentication.getPrincipal() instanceof AccountDetail;
    }
}
