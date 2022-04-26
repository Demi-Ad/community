package com.example.community.domain.postLike.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class LikeVo {
    private Long postId;
    private Long likeCount;

    public LikeVo(Long postId, Long likeCount) {
        this.postId = postId;
        this.likeCount = likeCount;
    }
}
