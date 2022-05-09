package com.example.community.domain.notification.service;

import com.example.community.config.security.util.SecurityContextUtil;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.notification.dto.NotificationDto;
import com.example.community.domain.notification.repo.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SecurityContextUtil securityContextUtil;


    public List<NotificationDto> notificationList() {
        Account loginAccount = securityContextUtil.currentAccount();
        return notificationRepository.findByToAccount(loginAccount)
                .stream()
                .map(NotificationDto::new)
                .collect(Collectors.toList());
    }



    public Long countNotification() {
        return notificationRepository.countByToAccount(securityContextUtil.currentAccount());
    }

    @Transactional
    public void deleteNotification() {
        Account account = securityContextUtil.currentAccount();
        notificationRepository.deleteByToAccount(account);
    }
}
