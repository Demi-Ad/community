package com.example.community.admin.notice.service;

import com.example.community.admin.notice.dto.NoticeCreateDto;
import com.example.community.admin.notice.dto.NoticeResponseDto;
import com.example.community.admin.notice.entity.Notice;
import com.example.community.admin.notice.repo.NoticeRepository;
import com.example.community.admin.user.entity.Admin;
import com.example.community.common.component.Pagination;
import com.example.community.config.adminSecurity.util.AdminSecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final AdminSecurityContextUtil adminSecurityContextUtil;

    public Pagination<NoticeResponseDto> noticeList(Pageable pageable) {
        Page<NoticeResponseDto> page = noticeRepository.findNoticeList(pageable);
        return Pagination.of(page);
    }

    public NoticeResponseDto noticeSingle(Long id) {
        return noticeRepository.findById(id)
                .map(Notice::toDto)
                .orElseThrow();
    }

    @Transactional
    public Long save(NoticeCreateDto noticeCreateDto) {
        Notice notice = noticeCreateDto.toEntity();
        Admin admin = adminSecurityContextUtil.currentAdmin();
        notice.postedAdmin(admin);
        return noticeRepository.save(notice).getId();
    }

    @Transactional
    public void delete(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }
}
