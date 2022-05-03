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
        if (accountForgotPasswordService.isExistsAccount(email)) {
            accountForgotPasswordService.changePassword(email);
            model.addAttribute("result",email);
            return "account/forgotPasswordAfter";
        }
        else {
            model.addAttribute("err",true);
            return "account/forgotPassword";
        }
    }

}
