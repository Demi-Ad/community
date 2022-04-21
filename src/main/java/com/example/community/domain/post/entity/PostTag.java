package com.example.community.domain.post.entity;

import com.example.community.domain.baseentity.BaseTimeEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "post_tag")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PostTag postTag = (PostTag) o;
        return id != null && Objects.equals(id, postTag.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
