package com.example.community.admin.postManage.service;

import com.example.community.admin.postManage.dto.PostManageResponseDto;
import com.example.community.admin.postManage.spec.SearchParam;
import com.example.community.common.component.Pagination;
import com.example.community.domain.comment.repo.CommentRepository;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostManageService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Pagination<PostManageResponseDto> postList(Pageable pageable, SearchParam searchParam) {
        Specification<Post> postSpecification = searchParam.get();
        Page<PostManageResponseDto> postList;
        if (postSpecification == null) {
            postList = postRepository.findAll(pageable).map(PostManageResponseDto::new);
        } else {
            postList = postRepository.findAll(postSpecification, pageable).map(PostManageResponseDto::new);
        }

        return Pagination.of(postList);
    }

    public void deletePost(Long id) {
        postRepository.findById(id).ifPresent(post -> {
            commentRepository.deleteChildComment(post);
            commentRepository.deleteComment(post);
            postRepository.delete(post);
        });
    }
}
