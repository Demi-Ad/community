package com.example.community.domain.post.controller;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.post.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/post/{postId}/like")
    public String likePost(@PathVariable("postId") Long id, @AuthenticationPrincipal AccountDetail accountDetail) {
        postLikeService.toggleLikePost(accountDetail.getAccount(),id);
        return "redirect:/post/{postId}";
    }
}
