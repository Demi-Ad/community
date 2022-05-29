package com.example.community.admin.notice.controller;

import com.example.community.admin.notice.dto.NoticeCreateDto;
import com.example.community.admin.notice.dto.NoticeResponseDto;
import com.example.community.admin.notice.service.NoticeService;
import com.example.community.common.component.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;


    @GetMapping("/notice")
    public String noticeList(Model model, @PageableDefault Pageable pageable) {
        Pagination<NoticeResponseDto> pageList = noticeService.noticeList(pageable);
        model.addAttribute("noticeList",pageList);
        return "notice/noticeList";
    }

    @GetMapping("/notice/{id}")
    public String noticeSingleView(@PathVariable Long id, Model model) {
        NoticeResponseDto noticeResponseDto = noticeService.noticeSingle(id);
        model.addAttribute("notice",noticeResponseDto);
        return "notice/noticeSingle";
    }


    @GetMapping("/admin/notice")
    public String adminNoticeList(Model model, Pageable pageable) {
        Pagination<NoticeResponseDto> pageList = noticeService.noticeList(pageable);
        model.addAttribute("noticeList",pageList);
        return "admin/notice/noticeList";
    }


    @GetMapping("admin/notice/create")
    public String noticeCreateForm(Model model) {
        model.addAttribute("notice",new NoticeCreateDto());
        return "admin/notice/noticeCreate";
    }

    @PostMapping("admin/notice/create")
    public String noticeCreate(@ModelAttribute NoticeCreateDto noticeCreateDto) {
        noticeService.save(noticeCreateDto);
        return "redirect:/admin/notice";
    }

    @PostMapping("admin/notice/delete/{id}")
    public String deleteNotice(@PathVariable Long id) {
        noticeService.delete(id);
        return "redirect:/admin/notice";
    }

}
