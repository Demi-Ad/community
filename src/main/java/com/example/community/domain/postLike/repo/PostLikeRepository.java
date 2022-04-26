package com.example.community.domain.postLike.repo;

import com.example.community.domain.postLike.entity.PostLike;
import com.example.community.domain.postLike.vo.LikeVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query(value = "select count(p) from PostLike p where p.post.id = :postId")
    Long countPostLike(@Param("postId") Long postId);

    @Query(value = "select new com.example.community.domain.postLike.vo.LikeVo(p.post.id, count(p.id)) from PostLike p where p.post.id in :postIdList group by p.post.id")
    List<LikeVo> countPostLike(@Param("postIdList") List<Long> postId);

    boolean existsByPost_IdAndAccount_Id(Long postId, Long accountId);

    Optional<PostLike> findByPost_IdAndAccount_Id(Long postId, Long accountId);


}