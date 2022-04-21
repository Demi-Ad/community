package com.example.community.domain.comment.service;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.comment.dto.CommentRequestDto;
import com.example.community.domain.comment.dto.CommentResponseDto;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    EntityManager entityManager;

    @Test
    void test() {
        Account account = new Account("AA@AA.AA","AAAA",null,"AAAA");
        accountRepository.save(account);
        Post post = new Post("AAAA","AAA");
        post.postedAccount(account);
        postRepository.save(post);

        CommentRequestDto commentLV1 = new CommentRequestDto(post.getId(), account.getId(), "AAAA", null);
        Long commentLV1Id = commentService.save(commentLV1);
        CommentRequestDto commentLV2_1 = new CommentRequestDto(post.getId(), account.getId(), "AAAA", commentLV1Id);
        commentService.save(commentLV2_1);
        CommentRequestDto commentLV2_2 = new CommentRequestDto(post.getId(), account.getId(), "AAAA", commentLV1Id);
        commentService.save(commentLV2_2);

        entityManager.flush();
        entityManager.clear();

        List<CommentResponseDto> commentResponse = commentService.getCommentResponse(post.getId());

        Assertions.assertThat(commentResponse.size()).isEqualTo(1);
        Assertions.assertThat(commentResponse.get(0).getChildrenCommentList().size()).isEqualTo(2);

    }
}