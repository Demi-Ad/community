package com.example.community.domain.account.controller;

import com.example.community.domain.account.dto.RegisterDto;
import com.example.community.domain.account.service.AccountRegisterService;
import com.example.community.domain.account.validator.DuplicateEmailValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
@Slf4j
public class RegisterController {

    private final AccountRegisterService accountRegisterService;
    private final DuplicateEmailValidator validator;


    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("account",new RegisterDto());
        return "account/register";
    }

    @PostMapping
    public String register(@ModelAttribute("account") @Valid RegisterDto registerDto, BindingResult bindingResult, HttpSession session) {

        validator.validate(registerDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "account/register";
        }
        accountRegisterService.accountRegister(registerDto);
        session.setAttribute("confirm",registerDto);

        return "redirect:/register/guide";
    }

    @GetMapping("/guide")
    public String guideForm(HttpSession session, Model model) {
        RegisterDto dto = (RegisterDto) session.getAttribute("confirm");

        if (dto == null) {
            return "error/4xx";
        }

        model.addAttribute("confirm",dto);
        return "account/registered";
    }
}
