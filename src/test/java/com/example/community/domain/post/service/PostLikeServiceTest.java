package com.example.community.domain.post.service;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import com.example.community.domain.postLike.service.PostLikeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class PostLikeServiceTest {

    @Autowired
    PostLikeService postLikeService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void test() {
        Post post = new Post("A","A");
        Account account1 = new Account("AAA@AA.AA","AAAA",null,"aaaa");
        Account account2 = new Account("BBB@AA.AA","AAAA",null,"bbbb");
        post.postedAccount(account1);
        accountRepository.save(account1);
        accountRepository.save(account2);
        postRepository.save(post);

        postLikeService.toggleLikePost(account1,post.getId());
        postLikeService.toggleLikePost(account2,post.getId());

        entityManager.flush();
        entityManager.clear();

        Long likeCount = postLikeService.postLikeCount(post);
        Assertions.assertThat(likeCount).isEqualTo(2);

        postLikeService.toggleLikePost(account1,post.getId());
        entityManager.flush();
        entityManager.clear();
        Long secondLikeCount = postLikeService.postLikeCount(post);
        Assertions.assertThat(secondLikeCount).isEqualTo(1);
    }
}