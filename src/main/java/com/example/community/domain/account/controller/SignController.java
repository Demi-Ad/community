package com.example.community.domain.account.controller;

import com.example.community.domain.account.common.EmailValidateQuery;
import com.example.community.domain.account.service.AccountRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final AccountRegisterService accountRegisterService;

    @GetMapping
    @ResponseBody
    public String sign(@ModelAttribute EmailValidateQuery emailValidateQuery)  {
        if (accountRegisterService.emailRegisterValidate(emailValidateQuery)) {
            return "OK";
        } else
            return "BAD";
    }
}