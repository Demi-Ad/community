package com.example.community.domain.post.entity;

import com.example.community.domain.baseentity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "post_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BaseTimeEntity baseTimeEntity;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
