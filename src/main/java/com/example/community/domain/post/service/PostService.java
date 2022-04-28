package com.example.community.domain.post.service;

import com.example.community.common.component.Pagination;
import com.example.community.common.util.AuthorizeCheckUtil;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.comment.service.CommentService;
import com.example.community.domain.post.dto.PostRequestDto;
import com.example.community.domain.post.dto.PostResponseDto;
import com.example.community.domain.post.dto.PostSearchParam;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import com.example.community.domain.postFile.dto.PostFileDto;
import com.example.community.domain.postFile.service.PostFileService;
import com.example.community.domain.postLike.service.PostLikeService;
import com.example.community.domain.postLike.vo.LikeVo;
import com.example.community.domain.postTag.dto.TagDto;
import com.example.community.domain.postTag.entity.PostTag;
import com.example.community.domain.postTag.entity.Tag;
import com.example.community.domain.postTag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final AuthorizeCheckUtil authorizeCheckUtil;

    private final PostLikeService postLikeService;

    private final CommentService commentService;

    private final PostFileService postFileService;

    public Long save(PostRequestDto postRequestDto, Long accountId) {
        List<String> tagStrList = List.of(postRequestDto.getTagJoiningStr().split(","));
        List<Tag> tagList = tagService.saveElseFind(tagStrList);
        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContent());
        Account account = accountRepository.findById(accountId).orElseThrow();
        post.postedAccount(account);
        postSetTag(tagList, post);
        postFileService.save(postRequestDto.getUploadFiles(), post);
        postRepository.save(post);
        return post.getId();
    }


    public void editPost(PostRequestDto postRequestDto, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.getPostTagList().clear();
        List<Tag> tagList = tagService.saveElseFind(List.of(postRequestDto.getTagJoiningStr().split(",")));
        postSetTag(tagList, post);
        post.edit(postRequestDto.getTitle(), postRequestDto.getContent());
    }

    @Transactional(readOnly = true)
    public PostRequestDto getEditPostForm(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        String title = post.getTitle();
        String content = post.getContent();
        String tagListStr = post.getPostTagList()
                .stream()
                .map(postTag -> postTag.getTag().getItem()).collect(Collectors.joining(","));

        return new PostRequestDto(title, content, tagListStr);
    }


    public void deletePost(Long postId) {
        Post post = postRepository.findByIdJoinAccount(postId).orElseThrow();

        if (authorizeCheckUtil.check(post))
            postRepository.delete(post);
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다");
    }

    @Transactional(readOnly = true)
    public PostResponseDto findPostSingleView(Long postId) {

        Post post = postRepository.findByIdJoinAccount(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재 하지 않는 글"));

        return createPostResponseDto(post);
    }


    @Transactional(readOnly = true)
    public Pagination<PostResponseDto> listPost(Pageable pageable) {

        Page<Post> postPage = postRepository.pagingJoinAccount(pageable);

        return createPagination(postPage);
    }

    @Transactional(readOnly = true)
    public Pagination<PostResponseDto> searchListPost(PostSearchParam postSearchParam, Pageable pageable) {
        Page<Post> postList;

        switch (postSearchParam.getParam()) {
            case TAG:
                postList = postRepository.findPostListFromTag(postSearchParam.getKeyword(), pageable);
                break;
            case TITLE:
                postList = postRepository.findByTitleContains(postSearchParam.getKeyword(), pageable);
                break;
            case AUTHOR:
                postList = postRepository.findByAccount_NicknameEquals(postSearchParam.getKeyword(), pageable);
                break;
            default:
                postList = Page.empty();
                break;
        }
        return createPagination(postList);
    }

    private Pagination<PostResponseDto> createPagination( Page<Post> postPage) {
        List<Post> posts = postPage.toList();
        Pageable pageable = postPage.getPageable();
        List<PostResponseDto> postResponseList = createPostResponseDto(posts);

        Pagination<PostResponseDto> postRequestDtoPagination = new Pagination<>((int) postPage.getTotalElements(), pageable.getPageNumber() + 1);
        postRequestDtoPagination.setDataList(postResponseList);
        return postRequestDtoPagination;
    }

    private void postSetTag(List<Tag> tagList, Post post) {
        tagList.forEach(tag -> {
            PostTag postTag = new PostTag();
            post.addPostTag(postTag);
            postTag.setTag(tag);
        });
    }

    private List<TagDto> createTagDtoList(Post post) {
        return post.getPostTagList()
                .stream()
                .map(postTag -> new TagDto(postTag.getTag().getItem()))
                .collect(Collectors.toList());
    }

    private PostResponseDto createPostResponseDto(Post post) {

        return PostResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .author(post.getAccount().getNickname())
                .profilePath(post.getAccount().getProfileImg())
                .createdBy(post.getCreatedAt())
                .tagList(createTagDtoList(post))
                .isCreated(authorizeCheckUtil.check(post))
                .likeCount(postLikeService.postLikeCount(post))
                .uploadFileLink(post.getPostFiles().stream().map(PostFileDto::new).collect(Collectors.toList()))
                .commentResponseDtoList(commentService.createCommentResponse(post.getId()))
                .build();

    }
    private List<PostResponseDto> createPostResponseDto(List<Post> postList) {
        List<Long> postIdList = postList.stream()
                .map(Post::getId)
                .collect(Collectors.toList());

        List<LikeVo> likeVoList = postLikeService.postLikeCount(postIdList);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : postList) {

            Long likeCount = likeVoList.stream()
                    .filter(likeVo -> likeVo.getPostId().equals(post.getId()))
                    .findFirst()
                    .map(LikeVo::getLikeCount)
                    .orElse(0L);

            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .postId(post.getId())
                    .postTitle(post.getTitle())
                    .postContent(removeHtmlTag(post.getContent()))
                    .author(post.getAccount().getNickname())
                    .profilePath(post.getAccount().getProfileImg())
                    .createdBy(post.getCreatedAt())
                    .tagList(createTagDtoList(post))
                    .likeCount(likeCount)
                    .build();

            postResponseDtoList.add(postResponseDto);
        }
        return postResponseDtoList;
    }

    private String removeHtmlTag(String html) {
        return html
                .replaceAll("<(/)?([a-zA-Z\\d]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "")
                .replaceAll("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>", "")
                .replaceAll("<figure[^>]*class=[\"']?([^>\"']+)[\"']?[^>]*>", "")
                .replaceAll("<a[^>]*href=[\"']?([^>\"']+)[\"']?[^>]*>", "")
                .replaceAll("&nbsp;", "");
    }


}
