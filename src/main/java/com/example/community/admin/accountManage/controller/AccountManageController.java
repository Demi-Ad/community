package com.example.community.admin.accountManage.controller;

import com.example.community.admin.accountManage.dto.AccountBlockRequestDto;
import com.example.community.admin.accountManage.dto.AccountDto;
import com.example.community.admin.accountManage.dto.AccountManageDto;
import com.example.community.admin.accountManage.resolver.BlockArg;
import com.example.community.admin.accountManage.service.AccountManageService;
import com.example.community.common.component.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class AccountManageController {

    private final AccountManageService accountManageService;

    @GetMapping("/admin/accountManage")
    public String accountManageListForm(Model model, @PageableDefault Pageable pageable) {
        Pagination<AccountDto> pageList = accountManageService.accountList(pageable);
        model.addAttribute("pageList",pageList);
        return "admin/accountManage/manageListForm";
    }

    @GetMapping("/admin/accountManage/{id}")
    public String accountManageForm(@PathVariable("id") Long accountId, Model model, @PageableDefault Pageable pageable) {
        AccountManageDto accountManageDto = accountManageService.accountDetailInformation(accountId, pageable);
        model.addAttribute("accountDetail",accountManageDto);
        model.addAttribute("block",new AccountBlockRequestDto(accountId));
        return "admin/accountManage/manageSingleForm";
    }

    @PostMapping("/admin/accountManage/block")
    public String blockAccount(@BlockArg AccountBlockRequestDto blockRequestDto, RedirectAttributes redirectAttributes) {
        accountManageService.blockAccount(blockRequestDto);
        redirectAttributes.addAttribute("id",blockRequestDto.getAccountId());
        return "redirect:/admin/accountManage/{id}";
    }

    @PostMapping("/admin/accountManage/unblock")
    public String unblockAccount(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        accountManageService.unblockAccount(id);
        redirectAttributes.addAttribute("id",id);
        return "redirect:/admin/accountManage/{id}";
    }


}
