package com.example.community.admin.forbiddenWord.controller;

import com.example.community.admin.forbiddenWord.dto.ForbiddenWordDto;
import com.example.community.admin.forbiddenWord.dto.ForbiddenWordSearchDto;
import com.example.community.admin.forbiddenWord.service.ForbiddenWordService;
import com.example.community.admin.forbiddenWord.validator.DuplicateForbiddenWordValidator;
import com.example.community.common.component.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ForbiddenWordController {

    private final ForbiddenWordService forbiddenWordService;
    private final DuplicateForbiddenWordValidator validator;
    @GetMapping("/admin/forbiddenWord")
    public String forbiddenWordForm(Model model,
                                    @PageableDefault Pageable pageable,
                                    @ModelAttribute("forbiddenWordSearch") ForbiddenWordSearchDto forbiddenWordSearchDto) {
        log.info("SEARCH = {}",forbiddenWordSearchDto);
        Pagination<ForbiddenWordDto> pagination = forbiddenWordService.forbiddenWordList(pageable,forbiddenWordSearchDto.createSpecification());

        model.addAttribute("forbiddenWordList",pagination);
        model.addAttribute("forbiddenWordForm", new ForbiddenWordDto());
        model.addAttribute("forbiddenWordSearch",new ForbiddenWordSearchDto());
        return "admin/forbiddenWord/forbiddenWordForm";
    }

    @PostMapping("/admin/forbiddenWord/add")
    public String forbiddenWordAdd(@ModelAttribute("forbiddenWordForm") ForbiddenWordDto forbiddenWordDto, Errors errors, RedirectAttributes redirectAttributes) {
        validator.validate(forbiddenWordDto,errors);

        if (errors.hasErrors()) {
            redirectAttributes.addAttribute("err", URLEncoder.encode("이미 존재하는 값", StandardCharsets.UTF_8));
            return "redirect:/admin/forbiddenWord";
        }

        forbiddenWordService.save(forbiddenWordDto);
        return "redirect:/admin/forbiddenWord";
    }
}
