package com.example.community.domain.comment.controller;

import com.example.community.admin.forbiddenWord.service.ForbiddenWordSpecification;
import com.example.community.admin.forbiddenWord.validator.ForbiddenWordCheckValidator;
import com.example.community.common.util.ValidateFailLogger;
import com.example.community.domain.comment.dto.CommentEditRequestDto;
import com.example.community.domain.comment.dto.CommentEditResponseDto;
import com.example.community.domain.comment.dto.CommentRequestDto;
import com.example.community.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ForbiddenWordCheckValidator validator;
    private final ValidateFailLogger logger;

    @PostMapping("/comment")
    @PreAuthorize("isAuthenticated()")
    public String createComment(@ModelAttribute CommentRequestDto commentRequestDto, Errors errors, RedirectAttributes redirectAttributes) {
        validator.validate(commentRequestDto.getCommentContent(), ForbiddenWordSpecification.COMMENT, errors);
        redirectAttributes.addAttribute("postId", commentRequestDto.getPostId());
        if (errors.hasErrors()) {
            String forbiddenWord = "";

            if (errors.getGlobalError() != null) {
                forbiddenWord = errors.getGlobalError().getDefaultMessage();
            }

            String encodeErr = URLEncoder.encode(Objects.requireNonNull(forbiddenWord), StandardCharsets.UTF_8);
            logger.convert(errors);
            return "redirect:/post/{postId}?err=" + encodeErr;
        }
        commentService.save(commentRequestDto);
        return "redirect:/post/{postId}";
    }

    @PreAuthorize("@authorizeCheckUtil.commentAuthorizedCheck(#deleteCommentId)")
    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam Long deleteCommentId, RedirectAttributes redirectAttributes) {
        Long postId = commentService.deleteComment(deleteCommentId);
        redirectAttributes.addAttribute("id",postId);
        return "redirect:/post/{id}";
    }

    @PostMapping("/comment/edit")
    @PreAuthorize("@authorizeCheckUtil.commentAuthorizedCheck(#commentEditRequestDto.commentId)")
    public String editComment(@ModelAttribute CommentEditRequestDto commentEditRequestDto, RedirectAttributes redirectAttributes) {
        CommentEditResponseDto dto = commentService.editComment(commentEditRequestDto);
        redirectAttributes.addAttribute("id",dto.getPostId());
        redirectAttributes.addAttribute("commentId",dto.getCommentId());
        return "redirect:/post/{id}";
    }
}
