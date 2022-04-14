package com.example.community.domain.account.controller;

import com.example.community.domain.account.dto.RegisterDto;
import com.example.community.domain.account.service.AccountRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String register(@ModelAttribute @Valid RegisterDto registerDto, BindingResult bindingResult) {
        //TODO : profileImg 이미지 저장 서비스 구현


        validator.validate(registerDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "account/register";
        }

        accountRegisterService.accountRegister(registerDto);
        return "redirect:/";
    }
}
