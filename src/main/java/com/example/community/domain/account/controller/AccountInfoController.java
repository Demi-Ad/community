package com.example.community.domain.account.controller;

import com.example.community.domain.account.dto.AccountInfoDto;
import com.example.community.domain.account.service.AccountInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountInfoController {

    private final AccountInfoService accountInfoService;



    @GetMapping("/info/{id}")
    public String accountInfoForm(@PathVariable("id") Long userId, Model model) {
        AccountInfoDto accountInfo = accountInfoService.createAccountInfo(userId);

        model.addAttribute("userInfo",accountInfo);
        return "account/accountInfo";
    }

    @GetMapping("/guestBook/{id}")
    public String guestBook(@PathVariable("id") Long userId, Model model) {
        return null;
    }


}
