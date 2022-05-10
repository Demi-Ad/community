package com.example.community.domain.notification.controller;

import com.example.community.domain.notification.dto.NotificationDto;
import com.example.community.domain.notification.dto.ResponseWrapper;
import com.example.community.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/notification")
    @ResponseBody
    public ResponseWrapper<NotificationDto> notificationList() {
        List<NotificationDto> notificationList = notificationService.notificationList();
        return ResponseWrapper.of(notificationList);
    }

    @GetMapping("/notification/{id:^0{0}[1-9]+}")
    public String notificationRedirect(@PathVariable Long id) {
        String redirectNotify = notificationService.redirectNotify(id);
        return "redirect:" + redirectNotify;
    }

    @PostMapping("/notification/removeAll")
    public String removeAll(HttpServletRequest request) {
        notificationService.deleteNotification();
        String requestURI = request.getHeader("referer");
        if (requestURI == null)
            return "redirect:/";

        return "redirect:" + requestURI;
    }
}
