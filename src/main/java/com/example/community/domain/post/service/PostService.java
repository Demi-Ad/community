package com.example.community.domain.post.service;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.post.dto.PostRequestDto;
import com.example.community.domain.post.dto.TagRequestDto;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.entity.PostTag;
import com.example.community.domain.post.entity.Tag;
import com.example.community.domain.post.repo.PostRepository;
import com.example.community.domain.post.repo.PostTagRepository;
import com.example.community.domain.post.repo.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    private final TagService tagService;

    public Long save(PostRequestDto postRequestDto,Long accountId) {

        List<Tag> tagList = tagService.saveElseSkip(postRequestDto.getTagRequestDtoList());

        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContent());

        Account account = accountRepository.findById(accountId).orElseThrow();

        post.postedAccount(account);
        tagList.forEach(tag -> {
            PostTag postTag = new PostTag();
            post.addPostTag(postTag);
            postTag.setTag(tag);
        });


        postRepository.save(post);
        return post.getId();
    }
}
