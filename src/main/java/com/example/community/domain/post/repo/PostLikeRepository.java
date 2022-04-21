package com.example.community.domain.post.repo;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query(value = "select count(p) from PostLike p where p.post.id = :postId")
    Long countPostLike(@Param("postId") Long postId);

    boolean existsByPost_IdAndAccount_Id(Long postId, Long accountId);

    Optional<PostLike> findByPost_IdAndAccount_Id(Long postId, Long accountId);


}