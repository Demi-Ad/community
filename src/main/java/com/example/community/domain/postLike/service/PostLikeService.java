package com.example.community.domain.postLike.service;

import com.example.community.common.exceptionSupplier.ExceptionSupplier;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import com.example.community.domain.postLike.entity.PostLike;
import com.example.community.domain.postLike.repo.PostLikeRepository;
import com.example.community.domain.postLike.vo.LikeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;


    public void toggleLikePost(Account account, Long postId) {
        boolean isAccountLikePost = postLikeRepository.existsByPost_IdAndAccount_Id(postId, account.getId());

        if (!isAccountLikePost) {
            PostLike postLike = new PostLike(postRepository.findById(postId).orElseThrow(ExceptionSupplier::supply400), account);
            postLikeRepository.save(postLike);
        } else {
            PostLike postLike = postLikeRepository.findByPost_IdAndAccount_Id(postId, account.getId()).orElse(null);
            if (postLike == null) {
                return;
            }
            postLikeRepository.delete(postLike);
        }
    }

    public Long postLikeCount(Post post) {
        return postLikeRepository.countPostLike(post.getId());
    }

    public List<LikeVo> postLikeCount(List<Long> postIdList) {
        return postLikeRepository.countPostLike(postIdList);
    }
}
