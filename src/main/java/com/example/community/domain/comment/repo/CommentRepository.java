package com.example.community.domain.comment.repo;

import com.example.community.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select distinct c from Comment c where c.parentComment.id is null and c.post.id= :postId")
    @EntityGraph(attributePaths = "childrenComment")
    Set<Comment> notHaveParentCommentSet(@Param("postId") Long postId);
}