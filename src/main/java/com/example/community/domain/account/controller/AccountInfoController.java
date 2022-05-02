package com.example.community.domain.account.controller;

import com.example.community.domain.account.dto.AccountInfoDto;
import com.example.community.domain.account.service.AccountInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/info")
@RequiredArgsConstructor
@Slf4j
public class AccountInfoController {

    private final AccountInfoService accountInfoService;

    @GetMapping("/{id}")
    public String accountInfoForm(@PathVariable("id") Long userId, Model model) {
        AccountInfoDto accountInfo = accountInfoService.createAccountInfo(userId)
                .orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"존재 하지않는 유저");
        });

        model.addAttribute("userInfo",accountInfo);
        return "account/accountInfo";
    }
}
