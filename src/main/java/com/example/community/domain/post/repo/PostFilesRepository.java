package com.example.community.domain.post.repo;

import com.example.community.domain.post.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostFilesRepository extends JpaRepository<PostFile, Long> {
    Optional<PostFile> findByFileConvertNameEquals(String fileConvertName);

}