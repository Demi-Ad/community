package com.example.community.domain.postTag.repo;

import com.example.community.domain.postTag.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}