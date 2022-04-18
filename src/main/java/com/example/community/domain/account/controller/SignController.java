package com.example.community.domain.account.controller;


import com.example.community.domain.account.common.EmailValidateQuery;
import com.example.community.domain.account.common.RegisterState;
import com.example.community.domain.account.dto.RegisterSignDto;
import com.example.community.domain.account.service.AccountRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final AccountRegisterService accountRegisterService;

    @GetMapping
    public String sign(@ModelAttribute @Valid EmailValidateQuery emailValidateQuery, BindingResult bindingResult,  Model model) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"올바르지 않은 파라미터");
        }
        RegisterSignDto signDto = accountRegisterService.emailRegisterValidate(emailValidateQuery);
        if (signDto.getRegisterState().equals(RegisterState.FAIL)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"EmailValidate 실패");
        }

        model.addAttribute("signDto",signDto);
        return "account/emailSign";
    }
}
