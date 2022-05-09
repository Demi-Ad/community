package com.example.community.domain.notification.listner;

import com.example.community.common.util.BeanUtils;
import com.example.community.config.security.util.SecurityContextUtil;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.comment.entity.Comment;
import com.example.community.domain.guestBook.entity.GuestBook;
import com.example.community.domain.notification.entity.Notification;
import com.example.community.domain.notification.eventType.EventType;
import com.example.community.domain.postLike.entity.PostLike;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PostPersist;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class NotificationTargetEntityListener {

    private final ApplicationEventPublisher eventPublisher;

    @PostPersist
    public void notification(Object o) {
        Notification notification = createNotification(o);
        if (notification == null) {
            return;
        }

        eventPublisher.publishEvent(notification);
    }

    private Notification createNotification(Object o) {

        Account fromAccount = BeanUtils.getBean(SecurityContextUtil.class).currentAccount();

        Notification notification;

        if (o instanceof Comment) {
            Comment comment = (Comment) o;
            if (comment.getParentComment() != null) {
                notification =  Notification.builder()
                        .eventType(EventType.RE_REPLY)
                        .uri("/post/" + comment.getPost().getId() + "?" + "commentId=" + comment.getId())
                        .fromAccount(fromAccount)
                        .toAccount(comment.getParentComment().getAccount())
                        .build();
            } else {
                notification =  Notification.builder()
                        .eventType(EventType.COMMENT_EVENT)
                        .uri("/post/" + comment.getPost().getId() + "?" + "commentId=" + comment.getId())
                        .fromAccount(fromAccount)
                        .toAccount(comment.getPost().getAccount())
                        .build();
            }

        } else if (o instanceof GuestBook) {
            GuestBook guestBook = (GuestBook) o;
            notification = Notification.builder()
                    .eventType(EventType.GUEST_BOOK_EVENT)
                    .uri("/guestBook/" + guestBook.getOwner().getId())
                    .fromAccount(fromAccount)
                    .toAccount(guestBook.getOwner())
                    .build();

        } else if (o instanceof PostLike) {
            PostLike postLike = (PostLike) o;
            notification = Notification.builder()
                    .eventType(EventType.LIKE_EVENT)
                    .uri("/post/" + postLike.getPost().getId())
                    .fromAccount(fromAccount)
                    .toAccount(postLike.getPost().getAccount())
                    .build();
        } else {
            notification = null;
        }

        return notification;
    }
}
