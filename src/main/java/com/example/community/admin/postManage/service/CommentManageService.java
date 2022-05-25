package com.example.community.admin.postManage.service;

import com.example.community.admin.postManage.dto.CommentManageResponseDto;
import com.example.community.domain.comment.repo.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentManageService {
    private final CommentRepository commentRepository;

    public List<CommentManageResponseDto> CommentResponse(Long id) {
        return commentRepository.projectionCommentManageDto(id);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.findById(id).ifPresentOrElse(comment -> {
            if (!comment.getChildrenComment().isEmpty()) {
                comment.changeComment("운영자에 의해 삭제된 댓글입니다");
                comment.adminDeleteComment();
            } else {
                commentRepository.deleteById(id);
            }
        }, () -> {
            log.error("존재하지 않는 댓글 접근");
            throw new NoSuchElementException();
        });
    }
}
