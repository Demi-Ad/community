package com.example.community.domain.account.controller;


import com.example.community.domain.account.service.AccountForgotPasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final AccountForgotPasswordService accountForgotPasswordService;

    @GetMapping
    public String emailFindForm() {
        return "account/forgotPassword";
    }

    @PostMapping
    public String passwordChangeUrl(@ModelAttribute(name = "email") String email, Model model) {
        try {
            String changePassword = accountForgotPasswordService.changePassword(email);
            model.addAttribute("result",changePassword);
            return "account/forgotPasswordAfter";
        }
        catch (NoSuchElementException e){
            log.warn("존재하지 않는 계정 조회");
            model.addAttribute("err",true);
            return "account/forgotPassword";
        }
    }

}
