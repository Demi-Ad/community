package com.example.community.domain.post.service;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.post.dto.PostRequestDto;
import com.example.community.domain.post.dto.PostResponseDto;
import com.example.community.domain.post.dto.TagDto;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.entity.PostTag;
import com.example.community.domain.post.entity.Tag;
import com.example.community.domain.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    private final TagService tagService;

    public Long save(PostRequestDto postRequestDto,Long accountId) {
        List<String> tagStrList = List.of(postRequestDto.getTagListStr().split(","));
        List<Tag> tagList = tagService.saveElseFind(tagStrList);
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

    @Transactional(readOnly = true)
    public PostResponseDto findPost(Long postId) {

        Post post = postRepository.findByIdJoinAccount(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"존재 하지 않는 글"));
        List<TagDto> tagList = getTagList(post);

        return getPostResponseDto(post, tagList);
    }


    @Transactional(readOnly = true)
    public List<PostResponseDto> findPagingPost(PageRequest pageRequest) {

        List<Post> posts = postRepository
                .pagingJoinAccount(PageRequest.of(0, 10, Sort.Direction.DESC,"id"))
                .toList();

        List<PostResponseDto> postResponseList = new ArrayList<>();
        for (Post post : posts) {
            List<TagDto> tagList = getTagList(post);
            PostResponseDto postResponseDto = getPostResponseDto(post, tagList);
            postResponseList.add(postResponseDto);
        }
        return postResponseList;
    }

    private List<TagDto> getTagList(Post post) {
        return post.getPostTagList()
                .stream()
                .map(postTag -> new TagDto(postTag.getTag().getItem()))
                .collect(Collectors.toList());
    }

    private PostResponseDto getPostResponseDto(Post post, List<TagDto> tagList) {
        return PostResponseDto.builder()
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .author(post.getAccount().getNickname())
                .profilePath(post.getAccount().getProfileImg())
                .createdBy(post.getCreatedAt())
                .tagList(tagList)
                .build();
    }

}
