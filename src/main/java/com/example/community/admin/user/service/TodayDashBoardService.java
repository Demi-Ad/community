package com.example.community.admin.user.service;

import com.example.community.admin.user.dto.TodayDashBoardDto;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.comment.entity.Comment;
import com.example.community.domain.comment.repo.CommentRepository;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodayDashBoardService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;


    public TodayDashBoardDto calcTodayCount() {

        LocalDateTime startDatetime = LocalDate.now().atStartOfDay();
        LocalDateTime endDatetime = startDatetime.plusDays(1).toLocalDate().atStartOfDay();

        return TodayDashBoardDto.builder()
                .newPost(postRepository.count(postSpecification(startDatetime,endDatetime)))
                .newComment(commentRepository.count(commentSpecification(startDatetime,endDatetime)))
                .newAccount(accountRepository.count(accountSpecification(startDatetime,endDatetime)))
                .build();
    }

    private Specification<Post> postSpecification(LocalDateTime startDatetime, LocalDateTime endDatetime) {
        return (root, query, cb) -> cb.between(root.get("createdAt"),startDatetime,endDatetime);
    }

    private Specification<Comment> commentSpecification(LocalDateTime startDatetime, LocalDateTime endDatetime) {
        return (root, query, cb) -> cb.between(root.get("createdAt"),startDatetime,endDatetime);
    }

    private Specification<Account> accountSpecification(LocalDateTime startDatetime, LocalDateTime endDatetime) {
        return (root, query, cb) -> cb.between(root.get("registeredAt"),startDatetime,endDatetime);
    }

}
