package com.example.community.admin.postManage.service;

import com.example.community.admin.postManage.dto.CommentManageResponseDto;
import com.example.community.domain.comment.repo.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentManageService {
    private final CommentRepository commentRepository;

    public List<CommentManageResponseDto> CommentResponse(Long id) {
        return commentRepository.projectionCommentManageDto(id);
    }
}
