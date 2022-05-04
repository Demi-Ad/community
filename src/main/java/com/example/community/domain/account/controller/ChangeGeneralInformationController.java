package com.example.community.domain.account.controller;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.dto.ChangeInformationDto;
import com.example.community.domain.account.service.ChangeGeneralInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChangeGeneralInformationController {

    private final ChangeGeneralInformationService changeGeneralInformationService;


    @GetMapping("changeInformation/{id}")
    @PreAuthorize("@authorizeCheckUtil.check(#accountDetail, #id)")
    public String changeInformationForm(@PathVariable Long id, Model model, @AuthenticationPrincipal AccountDetail accountDetail) {
        model.addAttribute("information",new ChangeInformationDto());
        model.addAttribute("userId",accountDetail.getAccount().getId());
        return "account/changeInformation";
    }

    @PostMapping("changeInformation/{id}")
    @PreAuthorize("@authorizeCheckUtil.check(#accountDetail, #id)")
    public String changeInformation(@PathVariable Long id, @ModelAttribute ChangeInformationDto changeInformationDto, @AuthenticationPrincipal AccountDetail accountDetail) {
        changeGeneralInformationService.changeInformation(accountDetail,changeInformationDto);
        return "redirect:/";
    }
}
