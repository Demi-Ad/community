package com.example.community.domain.account.controller;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.dto.ChangeInformationDto;
import com.example.community.domain.account.service.ChangeGeneralInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChangeGeneralInformationController {

    private final ChangeGeneralInformationService changeGeneralInformationService;


    @GetMapping("/changeInformation")
    public String changeInformationForm(Model model, @AuthenticationPrincipal AccountDetail accountDetail) {
        model.addAttribute("information",new ChangeInformationDto());
        model.addAttribute("userId",accountDetail.getAccount().getId());
        return "account/changeInformation";
    }

    @PostMapping("/changeInformation")
    public String changeInformation(@Valid @ModelAttribute(name = "information") ChangeInformationDto changeInformationDto,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal AccountDetail accountDetail) {
        if (bindingResult.hasErrors()) {
            return "account/changeInformation";
        }

        changeGeneralInformationService.changeInformation(accountDetail,changeInformationDto);
        return "redirect:/";
    }
}
