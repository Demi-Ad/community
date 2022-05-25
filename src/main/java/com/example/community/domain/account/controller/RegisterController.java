package com.example.community.domain.account.controller;

import com.example.community.domain.account.dto.RegisterDto;
import com.example.community.domain.account.service.AccountRegisterService;
import com.example.community.domain.account.validator.DuplicateEmailValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String register(@ModelAttribute("account") @Valid RegisterDto registerDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        validator.validate(registerDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "account/register";
        }
        accountRegisterService.accountRegister(registerDto);
        redirectAttributes.addAttribute("nickname",registerDto.getNickname());
        redirectAttributes.addAttribute("email",registerDto.getEmail());
        return "redirect:/register/guide";
    }

    @GetMapping("/guide")
    public String guideForm(HttpSession session, Model model, @RequestParam("nickname") String nickname, @RequestParam("email") String email) {

        model.addAttribute("nickname",nickname);
        model.addAttribute("email",email);

        return "account/registered";
    }
}
