package com.example.community.domain.postFile.entity;

import com.example.community.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post_file")
public class PostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String fileOriginName;
    private String fileConvertName;
    private String fileExtension;


    public PostFile(String fileOriginName, String fileConvertName, String fileExtension) {
        this.fileOriginName = fileOriginName; //abc.jpg
        this.fileConvertName = fileConvertName; //171-a173a-64a7.jpg
        this.fileExtension = fileExtension; // jpg
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
