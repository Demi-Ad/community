package com.example.community.domain.comment.controller;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.comment.dto.CommentRequestDto;
import com.example.community.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/comment")
    public String createComment(@ModelAttribute CommentRequestDto commentRequestDto,
                                @AuthenticationPrincipal AccountDetail accountDetail,
                                RedirectAttributes redirectAttributes) {

        commentRequestDto.setCommentWriteAccountId(accountDetail.getAccount().getId());
        Long commentId = commentService.save(commentRequestDto);
        redirectAttributes.addAttribute("postId", commentRequestDto.getPostId());
        redirectAttributes.addAttribute("commentId", commentId);
        return "redirect:/post/{postId}";
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam Long deleteCommentId,
                                @AuthenticationPrincipal AccountDetail accountDetail,
                                RedirectAttributes redirectAttributes) {
        if (accountDetail == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인 한 유저가 아닙니다");

        Long postId = commentService.deleteComment(deleteCommentId, accountDetail.getAccount());
        redirectAttributes.addAttribute("id",postId);
        return "redirect:/post/{id}";
    }
}
