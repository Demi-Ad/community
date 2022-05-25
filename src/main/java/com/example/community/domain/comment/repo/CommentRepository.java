package com.example.community.domain.comment.repo;

import com.example.community.admin.postManage.dto.CommentManageResponseDto;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.comment.entity.Comment;
import com.example.community.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    @Query("select distinct c from Comment c where c.parentComment.id is null and c.post.id= :postId order by c.createdAt asc")
    @EntityGraph(attributePaths = "childrenComment")
    Set<Comment> notHaveParentCommentSet(@Param("postId") Long postId);

    @Query("select distinct c from Comment c where c.parentComment.id is null and c.post.id= :postId order by c.createdAt asc")
    Page<Comment> notHaveParentCommentSet(@Param("postId") Long postId, Pageable pageable);

    List<Comment> findByAccount(Account account);

    @Query("select new com.example.community.admin.postManage.dto.CommentManageResponseDto(c) from Comment c where c.post.id =:id")
    List<CommentManageResponseDto> projectionCommentManageDto(@Param("id") Long id);

    @Query("delete from Comment c where c.post = :post and c.parentComment is not null ")
    @Modifying
    @Transactional
    void deleteChildComment(@Param("post") Post post);

    @Query("delete from Comment c where c.post = :post")
    @Modifying
    @Transactional
    void deleteComment(@Param("post") Post post);


}