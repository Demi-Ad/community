package com.example.community.domain.post.controller;

import com.example.community.common.component.Pagination;
import com.example.community.domain.post.dto.PostResponseDto;
import com.example.community.domain.post.dto.PostSearchParam;
import com.example.community.domain.post.resolver.Search;
import com.example.community.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostSearchController {

    private final PostService postService;

    @GetMapping("/search")
    @ResponseBody
    public String postSearch(@Search PostSearchParam postSearchParam,
                             @PageableDefault Pageable pageable,
                             Model model) {
        log.info("param = {}",postSearchParam);
        Pagination<PostResponseDto> postResponseDtoPagination = postService.searchListPost(postSearchParam, pageable);
        return postResponseDtoPagination.toString();
    }
}
