package com.example.community.domain.account.controller;

import com.example.community.config.security.util.SecurityContextUtil;
import com.example.community.domain.account.dto.AccountSecessionDto;
import com.example.community.domain.account.service.AccountSecessionService;
import com.example.community.domain.account.validator.EmailEqualValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/secession")
public class AccountSecessionController {

    private final EmailEqualValidator emailEqualValidator;
    private final AccountSecessionService accountSecessionService;
    private final SecurityContextUtil securityContextUtil;

    @GetMapping
    public String SecessionForm(Model model) {
        model.addAttribute("secessionDto", new AccountSecessionDto());
        return "account/secession";
    }

    @PostMapping
    public String AccountSecession(@Valid @ModelAttribute("secessionDto") AccountSecessionDto accountSecessionDto, BindingResult bindingResult) {
        emailEqualValidator.validate(accountSecessionDto,bindingResult);
        if (bindingResult.hasErrors()) {
            return "account/secession";
        }
        accountSecessionService.secession();
        securityContextUtil.anonymousAuthentication();

        return "redirect:/";
    }
}
