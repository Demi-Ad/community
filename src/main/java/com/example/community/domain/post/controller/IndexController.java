package com.example.community.domain.post.controller;

import com.example.community.common.component.Pagination;
import com.example.community.domain.post.dto.PostResponseDto;
import com.example.community.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final PostService postService;

    @GetMapping
    public String index(Model model, @PageableDefault(sort={"createdAt"},direction = Sort.Direction.DESC) Pageable pageable) {
        Pagination<PostResponseDto> pagingPost = postService.listPost(pageable);
        log.info("pagination = {}",pagingPost);
        model.addAttribute("pagingPost",pagingPost);
        return "index";
    }
}
