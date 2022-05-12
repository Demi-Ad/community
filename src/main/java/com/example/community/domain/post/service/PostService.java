package com.example.community.domain.post.service;

import com.example.community.common.component.Pagination;
import com.example.community.common.exceptionSupplier.ExceptionSupplier;
import com.example.community.config.security.util.AuthorizeCheckUtil;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.comment.service.CommentService;
import com.example.community.domain.post.dto.PostRequestDto;
import com.example.community.domain.post.dto.PostResponseDto;
import com.example.community.domain.post.dto.PostSearchParam;
import com.example.community.domain.post.dto.SearchPreviewDto;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import com.example.community.domain.postFile.dto.PostFileDto;
import com.example.community.domain.postFile.service.PostFileService;
import com.example.community.domain.postLike.service.PostLikeService;
import com.example.community.domain.postLike.vo.LikeVo;
import com.example.community.domain.postTag.dto.TagDto;
import com.example.community.domain.postTag.entity.PostTag;
import com.example.community.domain.postTag.entity.Tag;
import com.example.community.domain.postTag.repo.PostTagRepository;
import com.example.community.domain.postTag.repo.TagRepository;
import com.example.community.domain.postTag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final TagRepository tagRepository;

    private final TagService tagService;

    private final AuthorizeCheckUtil authorizeCheckUtil;

    private final PostLikeService postLikeService;

    private final CommentService commentService;

    private final PostFileService postFileService;

    private final PostTagRepository postTagRepository;

    public Long save(PostRequestDto postRequestDto, Account account) {
        List<String> tagStrList = List.of(postRequestDto.getTagJoiningStr().split(","));
        List<Tag> tagList = tagService.saveElseFind(tagStrList);
        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContent());
        post.postedAccount(account);
        postSetTag(tagList, post);
        postFileService.save(postRequestDto.getUploadFiles(), post);
        postRepository.save(post);
        return post.getId();
    }


    public void editPost(PostRequestDto postRequestDto, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(ExceptionSupplier::supply400);
        post.getPostTagList().clear();
        List<Tag> tagList = tagService.saveElseFind(List.of(postRequestDto.getTagJoiningStr().split(",")));
        postSetTag(tagList, post);
        post.edit(postRequestDto.getTitle(), postRequestDto.getContent());
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostRequestDto getEditPostForm(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(ExceptionSupplier::supply400);
        String title = post.getTitle();
        String content = post.getContent();
        String tagListStr = post.getPostTagList()
                .stream()
                .map(postTag -> postTag.getTag().getItem()).collect(Collectors.joining(","));

        return new PostRequestDto(title, content, tagListStr);
    }


    public void deletePost(Long postId) {
        Post post = postRepository.findByIdJoinAccount(postId).orElseThrow(ExceptionSupplier::supply400);
        postRepository.delete(post);

    }

    @Transactional(readOnly = true)
    public PostResponseDto findPostSingleView(Long postId,Pageable pageable) {

        Post post = postRepository.findByIdJoinAccount(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재 하지 않는 글"));

        return createPostResponseDto(post, pageable);
    }


    @Transactional(readOnly = true)
    public Pagination<PostResponseDto> listPost(Pageable pageable) {
        Page<Post> postPage = postRepository.pagingJoinAccount(pageable);

        return Pagination.of(postPage,this::createPostResponseDto);
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
        return Pagination.of(postList, this::createPostResponseDto);
    }

    @Transactional(readOnly = true)
    public List<SearchPreviewDto> searchPreview(PostSearchParam postSearchParam) {
        PageRequest pageRequest = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        switch (postSearchParam.getParam()) {
            case TAG:
                return tagRepository.findByItemContains(postSearchParam.getKeyword(), pageRequest)
                        .map(tag -> new SearchPreviewDto("/images/hash.svg",tag.getItem(), postSearchParam.toUri(tag.getItem())))
                        .toList();
            case TITLE:
                return postRepository.findByTitleContains(postSearchParam.getKeyword(), pageRequest)
                        .map(post -> new SearchPreviewDto("/images/search.svg",post.getTitle(),"/post/" + post.getId()))
                        .toList();
            case AUTHOR:
                return accountRepository.findByNicknameContaining(postSearchParam.getKeyword(), pageRequest)
                        .map(account -> new SearchPreviewDto("/profile/" + account.getProfileImg(), account.getNickname(), postSearchParam.toUri(account.getNickname())))
                        .toList();
        }
        return List.of();
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

    private PostResponseDto createPostResponseDto(Post post,Pageable pageable) {

        return PostResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .author(post.getAccount().getNickname())
                .profilePath(post.getAccount().getProfileImg())
                .createdBy(post.getCreatedAt())
                .tagList(createTagDtoList(post))
                .isCreated(authorizeCheckUtil.postAuthorizedCheck(post.getId()))
                .likeCount(postLikeService.postLikeCount(post))
                .uploadFileLink(post.getPostFiles().stream().map(PostFileDto::new).collect(Collectors.toList()))
                .commentResponseDtoList(Pagination.of(commentService.createCommentResponse(post.getId(),pageable)))
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
                    .authorId(post.getAccount().getId())
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
