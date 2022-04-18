package com.example.community.domain.post.controller;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.post.dto.PostResponseDto;
import com.example.community.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/post/{postId}")
    public String postForm(@PathVariable Long postId, Model model) {
        PostResponseDto post = postService.findPost(postId);
        model.addAttribute("post",post);
        return "post/postForm";
    }
}
