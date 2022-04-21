package com.example.community.domain.comment.repo;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.comment.entity.Comment;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Set;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void saveTest() {
        Account account = new Account("AA@AA.AA","AAAA",null,"AAAA");
        accountRepository.save(account);
        Post post = new Post("AA","AAA");
        post.postedAccount(account);
        postRepository.save(post);

        Comment commentLV1 = new Comment("LV1",account,post);
        Comment commentLV2_1 = new Comment("LV2_1",account,post);
        Comment commentLV2_2 = new Comment("LV2_1",account,post);

        commentLV1.setChildrenComment(commentLV2_1);
        commentLV1.setChildrenComment(commentLV2_2);
        commentRepository.save(commentLV1);

        entityManager.flush();
        entityManager.clear();

        Set<Comment> comments = commentRepository.notHaveParentCommentSet(post.getId());
        Assertions.assertThat(comments.size()).isEqualTo(1);
        for (Comment comment : comments) {
            Assertions.assertThat(comment.getChildrenComment().size()).isEqualTo(2);
        }
    }
}