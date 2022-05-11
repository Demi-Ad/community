package com.example.community.admin.accountManage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AccountManageController {

    @GetMapping("/admin/accountManage")
    public String accountManageForm(Model model) {
        return "admin/accountManage/manageForm";
    }
}
