package com.example.community.domain.comment.service;

import com.example.community.common.exceptionSupplier.ExceptionSupplier;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.comment.dto.CommentEditRequestDto;
import com.example.community.domain.comment.dto.CommentRequestDto;
import com.example.community.domain.comment.dto.CommentResponseDto;
import com.example.community.domain.comment.entity.Comment;
import com.example.community.domain.comment.repo.CommentRepository;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;

    public Long save(CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(ExceptionSupplier::supply400);
        Account account = accountRepository.findById(commentRequestDto.getCommentWriteAccountId()).orElseThrow(ExceptionSupplier::supply400);
        Comment comment = new Comment(commentRequestDto.getCommentContent(), account, post);

        if (commentRequestDto.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(commentRequestDto.getParentCommentId()).orElseThrow(ExceptionSupplier::supply400);
            parentComment.setChildrenComment(comment);
        }
        return commentRepository.save(comment).getId();
    }

    public List<CommentResponseDto> createCommentResponse(Long postId) {
        return commentRepository.notHaveParentCommentSet(postId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public Long deleteComment(Long commentId, Account account) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(ExceptionSupplier::supply400);
        boolean isAuthor = comment.getAccount().equals(account);
        if (isAuthor) {
            Long postId = comment.getPost().getId();
            commentRepository.delete(comment);
            return postId;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성한 유저가 아닙니다");
    }

    public Long editComment(CommentEditRequestDto commentEditRequestDto, Account account) {
        Comment comment = commentRepository.findById(commentEditRequestDto.getCommentId()).orElseThrow(ExceptionSupplier::supply400);

        boolean isAuthor = comment.getAccount().equals(account);
        if (isAuthor) {
            comment.changeComment(commentEditRequestDto.getCommentItem());
            return comment.getPost().getId();
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성한 유저가 아닙니다");
    }
}
