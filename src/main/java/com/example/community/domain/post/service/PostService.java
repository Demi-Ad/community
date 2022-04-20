package com.example.community.domain.post.service;

import com.example.community.config.security.auth.AccountDetail;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        postSetTag(tagList, post);

        postRepository.save(post);
        return post.getId();
    }



    public void editePost(PostRequestDto postRequestDto, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.getPostTagList().clear();
        List<Tag> tagList = tagService.saveElseFind(List.of(postRequestDto.getTagListStr().split(",")));
        postSetTag(tagList,post);
        post.edit(postRequestDto.getTitle(), postRequestDto.getTitle());
    }

    @Transactional(readOnly = true)
    public PostRequestDto editFormData(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        String title = post.getTitle();
        String content = post.getContent();
        String tagListStr = post.getPostTagList()
                .stream()
                .map(postTag -> postTag.getTag().getItem()).collect(Collectors.joining(","));

        return new PostRequestDto(title,content,tagListStr);
    }


    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        if (isCreatedUser(post))
            postRepository.delete(post);
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다");
    }

    @Transactional(readOnly = true)
    public PostResponseDto findPostSingleView(Long postId) {

        Post post = postRepository.findByIdJoinAccount(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"존재 하지 않는 글"));

        List<TagDto> tagList = getTagList(post);

        return getPostResponseDto(post, tagList,PostFindCriteria.FIND_ONE);
    }


    @Transactional(readOnly = true)
    public List<PostResponseDto> findPagingPost(PageRequest pageRequest) {

        List<Post> posts = postRepository
                .pagingJoinAccount(PageRequest.of(0, 10, Sort.Direction.DESC,"id"))
                .toList();

        List<PostResponseDto> postResponseList = new ArrayList<>();
        for (Post post : posts) {
            List<TagDto> tagList = getTagList(post);
            PostResponseDto postResponseDto = getPostResponseDto(post, tagList,PostFindCriteria.FIND_ALL);
            postResponseList.add(postResponseDto);
        }
        return postResponseList;
    }



    private void postSetTag(List<Tag> tagList, Post post) {
        tagList.forEach(tag -> {
            PostTag postTag = new PostTag();
            post.addPostTag(postTag);
            postTag.setTag(tag);
        });
    }

    private List<TagDto> getTagList(Post post) {
        return post.getPostTagList()
                .stream()
                .map(postTag -> new TagDto(postTag.getTag().getItem()))
                .collect(Collectors.toList());
    }

    private PostResponseDto getPostResponseDto(Post post, List<TagDto> tagList, PostFindCriteria postFindCriteria) {
        switch (postFindCriteria) {
            case FIND_ALL:
                return PostResponseDto.builder()
                        .postId(post.getId())
                        .postTitle(post.getTitle())
                        .postContent(removeHtmlTag(post.getContent()))
                        .author(post.getAccount().getNickname())
                        .profilePath(post.getAccount().getProfileImg())
                        .createdBy(post.getCreatedAt())
                        .tagList(tagList)
                        .build();

            case FIND_ONE:
                return PostResponseDto.builder()
                        .postId(post.getId())
                        .postTitle(post.getTitle())
                        .postContent(post.getContent())
                        .author(post.getAccount().getNickname())
                        .profilePath(post.getAccount().getProfileImg())
                        .createdBy(post.getCreatedAt())
                        .tagList(tagList)
                        .isCreated(isCreatedUser(post))
                        .build();
            default:
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"잘못된 요청입니다");
        }
    }

    private String removeHtmlTag(String html) {
        return html.replaceAll("<(/)?([a-zA-Z\\d]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>","").replaceAll("&nbsp;","");
    }

    private boolean isCreatedUser(Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AccountDetail))
            return false;

        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();

        if (accountDetail == null)
            return false;

        return post.isCreatedUser(accountDetail.getAccount());

    }

}
