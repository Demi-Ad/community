package com.example.community.domain.post.controller;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.post.dto.PostRequestDto;
import com.example.community.domain.post.dto.PostResponseDto;
import com.example.community.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @GetMapping("/post/{postId}")
    @ResponseBody
    public PostResponseDto postSingleView(@PathVariable Long postId, Model model) {
        PostResponseDto post = postService.findPost(postId);
        model.addAttribute("post",post);
        return post;
    }

    @GetMapping("/post/create")
    public String postForm(Model model) {
        model.addAttribute("post",new PostRequestDto());
        return "post/postCreateForm";
    }

    @PostMapping("/post/create")
    public String createPost(@ModelAttribute PostRequestDto postRequestDto,
                             @AuthenticationPrincipal AccountDetail accountDetail,
                             RedirectAttributes redirectAttributes) {
        Long postId = postService.save(postRequestDto, accountDetail.getAccount().getId());
        redirectAttributes.addAttribute("id",postId);
        return "redirect:/post/{id}";
    }
}
