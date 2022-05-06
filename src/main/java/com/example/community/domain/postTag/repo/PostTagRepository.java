package com.example.community.domain.postTag.repo;

import com.example.community.domain.post.entity.Post;
import com.example.community.domain.postTag.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    List<PostTag> findByPost(Post post);

}