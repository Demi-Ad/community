package com.example.community.domain.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminDashBoardController {

    @GetMapping("/dashboard")
    @ResponseBody
    public String dashboard() {
        return "AA";
    }
}
