package com.example.community.common.util;

import com.example.community.common.exceptionSupplier.ExceptionSupplier;
import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.comment.entity.Comment;
import com.example.community.domain.comment.repo.CommentRepository;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthorizeCheckUtil {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public boolean postAuthorizedCheck(Long postId) {
        Account account = this.currentContextAccount();
        if (account != null) {
            Post post = postRepository.findById(postId).orElseThrow(ExceptionSupplier::supply400);
            return post.isCreatedUser(account);
        }
        return false;
    }

    public boolean commentAuthorizedCheck(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(ExceptionSupplier::supply400);
        Account account = currentContextAccount();
        return comment.isAuthor(account);
    }

    private Account currentContextAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AccountDetail) {
            return ((AccountDetail) principal).getAccount();
        }
        return null;
    }

    public boolean isLoginUser(Authentication authentication) {
        return authentication.getPrincipal() instanceof AccountDetail;
    }
}
