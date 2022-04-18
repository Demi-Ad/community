package com.example.community.domain.post.repo;

import com.example.community.domain.post.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByItem(String item);

    Optional<Tag> findByItem(String item);



}