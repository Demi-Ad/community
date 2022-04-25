package com.example.community.domain.post.service;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.entity.PostLike;
import com.example.community.domain.post.repo.PostLikeRepository;
import com.example.community.domain.post.repo.PostRepository;
import com.example.community.domain.post.vo.LikeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;


    public void toggleLikePost(Account account, Long id) {
        boolean isAccountLikePost = postLikeRepository.existsByPost_IdAndAccount_Id(id, account.getId());

        if (!isAccountLikePost) {
            PostLike postLike = new PostLike(postRepository.findById(id).orElseThrow(), account);
            postLikeRepository.save(postLike);
        } else {
            PostLike postLike = postLikeRepository.findByPost_IdAndAccount_Id(id, account.getId()).orElse(null);
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
