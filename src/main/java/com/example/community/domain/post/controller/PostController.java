package com.example.community.domain.post.controller;

import com.example.community.admin.forbiddenWord.service.ForbiddenWordSpecification;
import com.example.community.admin.forbiddenWord.validator.ForbiddenWordCheckValidator;
import com.example.community.common.util.ValidateFailLogger;
import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.post.dto.PostRequestDto;
import com.example.community.domain.post.dto.PostResponseDto;
import com.example.community.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final ForbiddenWordCheckValidator forbiddenWordCheckValidator;
    private final ValidateFailLogger validateFailLogger;

    @GetMapping("/post/{postId}")
    public String postSingleView(@PathVariable Long postId, Model model, @PageableDefault Pageable pageable) {
        PostResponseDto post = postService.findPostSingleView(postId,pageable);
        model.addAttribute("post", post);
        return "post/postSingleView";
    }

    @GetMapping("/post/create")
    public String postForm(Model model) {
        model.addAttribute("post", new PostRequestDto());
        return "post/postCreateForm";
    }

    @PostMapping("/post/create")
    public String createPost(@Valid @ModelAttribute(value = "post",name = "post") PostRequestDto postRequestDto,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal AccountDetail accountDetail,
                             RedirectAttributes redirectAttributes) {
        forbiddenWordCheckValidator.validate(postRequestDto.getContent(), ForbiddenWordSpecification.POST,bindingResult);
        if (bindingResult.hasErrors()) {
            validateFailLogger.convert(bindingResult);
            return "post/postCreateForm";
        }

        Long postId = postService.save(postRequestDto, accountDetail.getAccount());
        redirectAttributes.addAttribute("id", postId);
        return "redirect:/post/{id}";
    }

    @PostMapping("/post/{id}/delete")
    @PreAuthorize("@authorizeCheckUtil.postAuthorizedCheck(#postId)")
    public String deletePost(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
        return "redirect:/";
    }

    @GetMapping("/post/{id}/edite")
    @PreAuthorize("@authorizeCheckUtil.postAuthorizedCheck(#postId)")
    public String editePostForm(@PathVariable("id") Long postId, Model model) {
        PostRequestDto postRequestDto = postService.getEditPostForm(postId);
        model.addAttribute("post", postRequestDto);
        return "post/postEditForm";
    }

    @PostMapping("/post/{id}/edite")
    @PreAuthorize("@authorizeCheckUtil.postAuthorizedCheck(#postId)")
    public String editPost(@PathVariable("id") Long postId, @Valid @ModelAttribute("post") PostRequestDto postRequestDto, BindingResult bindingResult) {
        forbiddenWordCheckValidator.validate(postRequestDto.getContent(), ForbiddenWordSpecification.POST, bindingResult);
        if (bindingResult.hasErrors()) {
            validateFailLogger.convert(bindingResult);
            return "post/postEditForm";
        }
        postService.editPost(postRequestDto, postId);
        return "redirect:/post/{id}";
    }
}
