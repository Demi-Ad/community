package com.example.community.admin.user.controller;

import com.example.community.admin.user.dto.AdminLoginDto;
import com.example.community.config.adminSecurity.auth.AdminDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminLoginController {

    @GetMapping("/admin/login")
    public String adminLoginForm(Model model) {
        model.addAttribute("adminLogin",new AdminLoginDto());
        return "admin/adminLogin";
    }

    @GetMapping("/admin/logout")
    public String adminLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AdminDetail) {
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/admin/login";
    }
}
