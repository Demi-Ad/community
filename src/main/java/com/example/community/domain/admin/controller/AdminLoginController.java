package com.example.community.domain.admin.controller;

import com.example.community.domain.admin.dto.AdminLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {

    @GetMapping("/admin/login")
    public String adminLoginForm(Model model) {
        model.addAttribute("adminLogin",new AdminLoginDto());
        return "admin/adminLogin";
    }
}
