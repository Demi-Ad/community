package com.example.community.domain.account.controller;

import com.example.community.admin.forbiddenWord.service.ForbiddenWordSpecification;
import com.example.community.admin.forbiddenWord.validator.ForbiddenWordCheckValidator;
import com.example.community.common.component.Pagination;
import com.example.community.common.util.ValidateFailLogger;
import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.dto.AccountInfoBasicDto;
import com.example.community.domain.account.dto.AccountInfoDetailDto;
import com.example.community.domain.account.service.AccountInfoService;
import com.example.community.domain.guestBook.dto.GuestBookResponseDto;
import com.example.community.domain.guestBook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountInfoController {

    private final AccountInfoService accountInfoService;
    private final GuestBookService guestBookService;

    private final ForbiddenWordCheckValidator validator;
    private final ValidateFailLogger logger;

    @GetMapping("/info/{id:^0{0}[1-9]+}")
    public String accountInfoForm(@PathVariable("id") Long userId, Model model) {
        AccountInfoDetailDto accountInfo = accountInfoService.createAccountInfo(userId);

        model.addAttribute("userInfo", accountInfo);
        model.addAttribute("userId", userId);
        return "account/accountInfo";
    }

    @GetMapping("/guestBook/{id:^0{0}[1-9]+}")
    public String guestBook(@PathVariable("id") Long ownerId, @PageableDefault() Pageable pageable, Model model) {
        AccountInfoBasicDto accountInfoBasicDto = accountInfoService.projectionAccountInfo(ownerId);
        Pagination<GuestBookResponseDto> page = guestBookService.listGuestBook(ownerId, pageable);

        model.addAttribute("accountInfo", accountInfoBasicDto);
        model.addAttribute("userId", ownerId);
        model.addAttribute("pageResponse", page);
        return "account/guestBook";
    }

    @PostMapping("/guestBook/{id:^0{0}[1-9]+}")
    public String guestBookSave(@ModelAttribute("content") String content, Errors errors,
                                @PathVariable("id") Long ownerId,
                                @AuthenticationPrincipal AccountDetail accountDetail) {
        validator.validate(content, ForbiddenWordSpecification.GUEST_BOOK, errors);
        if (errors.hasErrors()) {
            logger.convert(errors);
            String encodeDefaultMessage = "";
            if (errors.getGlobalError() != null) {
                encodeDefaultMessage = URLEncoder.encode(errors.getGlobalError().getDefaultMessage(), StandardCharsets.UTF_8);
            }
            return "redirect:/guestBook/{id}?err=" + encodeDefaultMessage;
        }
        guestBookService.save(content, ownerId, accountDetail.getAccount());
        return "redirect:/guestBook/{id}";
    }

    @PostMapping("/guestBook/delete/{id:^0{0}[1-9]+}")
    @PreAuthorize("hasRole('ADMIN') || @guestBookService.isOwner(#guestBookId, #accountDetail)")
    public String guestBookDelete(@PathVariable("id") Long guestBookId, @AuthenticationPrincipal AccountDetail accountDetail, RedirectAttributes redirectAttributes) {
        Long ownerId = guestBookService.delete(guestBookId);
        redirectAttributes.addAttribute("ownerId",ownerId);
        return "redirect:/guestBook/{ownerId}";
    }


}
