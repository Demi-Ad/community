package com.example.community.domain.post.controller;

import com.example.community.domain.post.dto.PostResponseDto;
import com.example.community.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final PostService postService;

    @GetMapping
    public String index(Model model) {
        List<PostResponseDto> pagingPost = postService.findPagingPost(null);
        model.addAttribute("postList",pagingPost);
        return "index";
    }
}
