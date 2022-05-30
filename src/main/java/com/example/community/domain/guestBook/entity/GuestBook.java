package com.example.community.domain.guestBook.entity;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.notification.listner.NotificationTargetEntityListener;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners({AuditingEntityListener.class, NotificationTargetEntityListener.class})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "guest_book")
public class GuestBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",foreignKey = @ForeignKey(name = "owner_fk"),nullable = false)
    private Account owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id",foreignKey = @ForeignKey(name = "guest_fk"),nullable = false)
    private Account guest;
    @Column(nullable = false)
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public GuestBook(Account owner, Account guest, String content) {
        this.owner = owner;
        this.guest = guest;
        this.content = content;
    }
}
