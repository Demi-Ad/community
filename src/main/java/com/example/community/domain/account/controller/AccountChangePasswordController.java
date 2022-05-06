package com.example.community.domain.account.controller;

import com.example.community.domain.account.dto.ChangePasswordDto;
import com.example.community.domain.account.service.AccountChangePasswordService;
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
@RequiredArgsConstructor
@RequestMapping("/changePassword")
@Slf4j
public class AccountChangePasswordController {

    private final AccountChangePasswordService accountChangePasswordService;


    @GetMapping
    public String changePasswordForm(Model model) {
        model.addAttribute("pwdDto",new ChangePasswordDto());
        return "account/changePassword";
    }

    @PostMapping
    public String changePassword(@ModelAttribute("pwdDto") @Valid ChangePasswordDto changePasswordDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account/changePassword";
        }
        accountChangePasswordService.changePassword(changePasswordDto);
        return "redirect:/logout";
    }

}
