package com.example.community.domain.post.entity;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.baseentity.BaseTimeEntity;
import com.example.community.domain.comment.entity.Comment;
import com.example.community.domain.post.entitylisnter.PostEntityListener;
import com.example.community.domain.postFile.entity.PostFile;
import com.example.community.domain.postLike.entity.PostLike;
import com.example.community.domain.postTag.entity.PostTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners({PostEntityListener.class})
@Table(name = "post")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title",nullable = false)
    private String title;

    @Column(name = "post_content")
    @Lob
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",foreignKey = @ForeignKey(name = "post_account_fk"),nullable = false)
    private Account account;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private final List<PostTag> postTagList = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private final List<PostFile> postFiles = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private final List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private final List<PostLike> postLikeList = new ArrayList<>();

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void postedAccount(Account account) {
        this.account = account;
    }

    public void addPostTag(PostTag tag) {
        this.postTagList.add(tag);
        tag.setPost(this);
    }

    public void addPostFile(PostFile postFile) {
        this.postFiles.add(postFile);
        postFile.setPost(this);
    }

    public boolean isCreatedUser(Account account) {
        return this.account.equals(account);
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "title = " + title + ", " +
                "content = " + content + ")";
    }
}
