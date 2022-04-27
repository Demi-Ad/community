package com.example.community.domain.post.controller;

import com.example.community.common.component.Pagination;
import com.example.community.domain.post.common.SearchParam;
import com.example.community.domain.post.dto.PostResponseDto;
import com.example.community.domain.post.dto.PostSearchParam;
import com.example.community.domain.post.resolver.Search;
import com.example.community.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostSearchController {

    private final PostService postService;

    @GetMapping("/search")
    public String postSearch(@Search PostSearchParam postSearchParam,
                             @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model) {

        Pagination<PostResponseDto> postResponseDtoPagination = postService.searchListPost(postSearchParam, pageable);
        model.addAttribute("pagingPost",postResponseDtoPagination);

        if (postSearchParam.getParam() == SearchParam.TAG) {
            // URL 상에서 '#'은 퍼센트 인코딩 되므로 #을 제거 한 후 모델에 add
            String replaceKeyword = postSearchParam.getKeyword().replace("#", "");
            postSearchParam.setKeyword(replaceKeyword);
        }

        model.addAttribute("searchedParam",postSearchParam);
        return "post/postSearchView";
    }
}
