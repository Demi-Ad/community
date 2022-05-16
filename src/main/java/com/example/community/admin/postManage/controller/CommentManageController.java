package com.example.community.admin.postManage.controller;

import com.example.community.admin.postManage.dto.CommentManageResponseDto;
import com.example.community.admin.postManage.service.CommentManageService;
import com.example.community.domain.notification.dto.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
public class CommentManageController {
    private final CommentManageService commentManageService;

    @GetMapping("/admin/commentList")
    @ResponseBody
    public ResponseWrapper<CommentManageResponseDto> getCommentList(@RequestParam("postId") Long id) {
        return ResponseWrapper.of(commentManageService.CommentResponse(id));
    }

    @PostMapping("/admin/commentDelete")
    @ResponseBody
    public ResponseEntity<Boolean> deleteComment(@RequestParam("id") Long id) {
        try {
            commentManageService.deleteComment(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
