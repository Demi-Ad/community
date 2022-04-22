package com.example.community.domain.comment.dto;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String author;
    private String authorProfile;
    private String content;
    private LocalDateTime createAt;
    private List<CommentResponseDto> childrenCommentList = new ArrayList<>();
    private boolean isCreateAuthor;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.author = comment.getAccount().getNickname();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.authorProfile = comment.getAccount().getProfileImg();
        this.isCreateAuthor = checkCommentAuthor(comment);
        comment.getChildrenComment().stream()
                .map(childComment -> CommentResponseDto.builder()
                        .commentId(childComment.getId())
                        .author(childComment.getAccount().getNickname())
                        .content(childComment.getContent())
                        .authorProfile(childComment.getAccount().getProfileImg())
                        .createAt(childComment.getCreatedAt())
                        .isCreateAuthor(checkCommentAuthor(childComment))
                        .build())
                .sorted(getComparator())
                .forEach(this.childrenCommentList::add);
    }

    @Builder
    public CommentResponseDto(Long commentId,String author, String content,String authorProfile, LocalDateTime createAt, boolean isCreateAuthor) {
        this.commentId = commentId;
        this.author = author;
        this.authorProfile = authorProfile;
        this.content = content;
        this.createAt = createAt;
        this.isCreateAuthor = isCreateAuthor;
    }

    private Comparator<CommentResponseDto> getComparator() {
        return (o1, o2) -> {
            if (o1.getCreateAt().isAfter(o2.getCreateAt()))
                return 1;
            if (o2.getCreateAt().isAfter(o1.getCreateAt()))
                return -1;
            return 0;
        };
    }

    private boolean checkCommentAuthor(Comment comment) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AccountDetail) {
            Account account = ((AccountDetail) principal).getAccount();
            return comment.getAccount().getId().equals(account.getId());
        }
        return false;
    }
}
