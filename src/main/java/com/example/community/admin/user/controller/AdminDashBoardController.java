package com.example.community.admin.user.controller;

import com.example.community.admin.user.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminDashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("dashboard",dashBoardService.createDashBoard());
        return "admin/dashBoard";
    }
}
