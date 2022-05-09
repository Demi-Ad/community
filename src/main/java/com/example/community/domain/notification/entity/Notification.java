package com.example.community.domain.notification.entity;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.notification.eventType.EventType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    @Enumerated(value = EnumType.STRING)
    private EventType eventType;

    private String uri;

    @CreatedDate
    private LocalDateTime createdAt;

    private String content;

    @Builder
    public Notification(Account fromAccount, Account toAccount, EventType eventType, String uri) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.eventType = eventType;
        this.uri = uri;
        this.content = createContent();
    }

    public static Notification emptyNotification() {
        return new Notification();
    }

    private String createContent() {
        StringBuilder stringBuilder = new StringBuilder();
        switch (eventType) {
            case COMMENT_EVENT:
                stringBuilder.append(fromAccount.getNickname()).append(" 님이 댓글을 작성하셨습니다");
                break;
            case LIKE_EVENT:
                stringBuilder.append(fromAccount.getNickname()).append(" 님이 좋아요");
                break;
            case GUEST_BOOK_EVENT:
                stringBuilder.append(fromAccount.getNickname()).append(" 님이 방명록을 작성하셨습니다");
                break;
            case RE_REPLY:
                stringBuilder.append(fromAccount.getNickname()).append(" 님이 대댓글을 작성하였습니다");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "eventType = " + eventType + ", " +
                "content = " + content + ")";
    }
}
