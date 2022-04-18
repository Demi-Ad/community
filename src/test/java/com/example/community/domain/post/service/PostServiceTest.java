package com.example.community.domain.post.service;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.account.service.AccountRegisterService;
import com.example.community.domain.post.dto.PostRequestDto;
import com.example.community.domain.post.dto.TagRequestDto;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EntityManager em;

    @Autowired
    PostRepository postRepository;

    @Test
    void postServiceTest() {
        PostRequestDto postRequestDto = new PostRequestDto("A", "A", List.of(new TagRequestDto("A"), new TagRequestDto("B")));

        Account account = new Account("test@test.com","AAA",null,"AAA");
        Long id = accountRepository.save(account).getId();

        Long postId = postService.save(postRequestDto, id);

        em.flush();
        em.clear();

        Post post = postRepository.findById(postId).orElseThrow();

        Assertions.assertThat(post.getPostTagList().size()).isEqualTo(2);
        Assertions.assertThat(post.getAccount()).isEqualTo(account);


    }
}