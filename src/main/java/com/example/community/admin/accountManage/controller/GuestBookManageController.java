package com.example.community.admin.accountManage.controller;

import com.example.community.domain.guestBook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class GuestBookManageController {
    private final GuestBookService guestBookService;

    @PostMapping("/admin/guestBook/{id}")
    public String guestBookDelete(@PathVariable("id") Long guestBookId, @RequestParam Long accountId, RedirectAttributes redirectAttributes) {
        guestBookService.delete(guestBookId);
        redirectAttributes.addAttribute("id",accountId);
        return "redirect:/admin/accountManage/{id}";
    }
}
