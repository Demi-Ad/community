package com.example.community.domain.post.repo;

import com.example.community.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);

    @EntityGraph(attributePaths = "account")
    @Query(value = "select p from Post p where p.id=:id")
    Optional<Post> findByIdJoinAccount(@Param(value = "id") Long id);

    @EntityGraph(attributePaths = "account")
    @Query(value = "select p from Post p")
    Page<Post> pagingJoinAccount(Pageable pageable);

}