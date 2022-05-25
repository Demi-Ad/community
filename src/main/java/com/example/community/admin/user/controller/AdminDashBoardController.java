package com.example.community.admin.user.controller;

import com.example.community.admin.user.service.DashBoardService;
import com.example.community.admin.user.service.TodayDashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminDashBoardController {

    private final DashBoardService dashBoardService;
    private final TodayDashBoardService todayDashBoardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletResponse response) {
        model.addAttribute("dashboard",dashBoardService.createDashBoard());
        model.addAttribute("count",todayDashBoardService.calcTodayCount());
        return "admin/dashBoard";
    }
}
