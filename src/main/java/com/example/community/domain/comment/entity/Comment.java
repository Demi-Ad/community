package com.example.community.domain.comment.entity;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String content;
    @OneToMany(mappedBy = "parentComment", orphanRemoval = true, cascade = CascadeType.ALL)
    private final Set<Comment> childrenComment = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;
    @CreatedDate
    private LocalDateTime createdAt;

    public Comment(String content, Account account, Post post) {
        this.content = content;
        this.account = account;
        this.post = post;
    }

    public void setChildrenComment(Comment comment) {
        this.childrenComment.add(comment);
        comment.setParentComment(this);

    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "content = " + content + ", " +
                "account = " + account.getId() + account.getNickname() +
                " createdAt = " + createdAt + ")";
    }
}
