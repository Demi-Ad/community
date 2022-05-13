package com.example.community.admin.postManage.controller;

import com.example.community.admin.postManage.service.PostManageService;
import com.example.community.admin.postManage.spec.SearchParam;
import com.example.community.admin.postManage.spec.SearchPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class PostManageController {

    private final PostManageService postManageService;

    @GetMapping("/admin/postList")
    public String managePostForm(Model model,
                                 @PageableDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                                 @SearchPost SearchParam searchParam) {
        model.addAttribute("postList",postManageService.postList(pageable,searchParam));
        model.addAttribute("searchParam",searchParam);
        return "admin/postManage/postList";
    }

    @PostMapping("/admin/postDelete")
    public String managePostDelete(@RequestParam("id") Long id) {
        postManageService.deletePost(id);
        return "redirect:/admin/postList";
    }
}
