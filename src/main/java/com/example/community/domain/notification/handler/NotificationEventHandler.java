package com.example.community.domain.notification.handler;

import com.example.community.domain.notification.entity.Notification;
import com.example.community.domain.notification.repo.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class NotificationEventHandler {

    private final NotificationRepository notificationRepository;

    @EventListener
    public void notificationSave(Notification notification) {
        if (notification.getFromAccount().getId().equals(notification.getToAccount().getId()))
            return;

        notificationRepository.save(notification);
    }
}
