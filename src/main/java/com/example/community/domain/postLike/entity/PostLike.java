package com.example.community.domain.postLike.entity;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.notification.listner.NotificationTargetEntityListener;
import com.example.community.domain.post.entity.Post;
import lombok.AccessLevel;
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
@Table(name = "post_like")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",foreignKey = @ForeignKey(name = "like_post_fk"))
    private Post post;

    @ManyToOne
    @JoinColumn(name = "account_id",foreignKey = @ForeignKey(name = "like_account_fk"))
    private Account account;

    @CreatedDate
    private LocalDateTime likedAt;

    public void accountNull() {
        this.account = null;
    }

    public PostLike(Post post, Account account) {
        this.post = post;
        this.account = account;
    }
}
