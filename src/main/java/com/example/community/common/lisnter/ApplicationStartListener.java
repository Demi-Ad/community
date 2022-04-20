package com.example.community.common.lisnter;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.entity.PostTag;
import com.example.community.domain.post.entity.Tag;
import com.example.community.domain.post.repo.PostRepository;
import com.example.community.domain.post.repo.PostTagRepository;
import com.example.community.domain.post.repo.TagRepository;
import com.example.community.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private final PostRepository postRepository;

    private final PostTagRepository postTagRepository;

    private final TagRepository tagRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Account account = Account.builder().email("votm777@naver.com")
                .password(passwordEncoder.encode("aaaa"))
                .nickname("aaaa")
                .profileImg("person.png")
                .build();

        account.unLock();
        accountRepository.save(account);

        Post post = new Post("TEST1","TEST1");
        Tag tag = new Tag("#TEST_TAG");
        PostTag postTag = new PostTag();
        post.addPostTag(postTag);
        postTag.setTag(tag);
        post.postedAccount(account);
        tagRepository.save(tag);
        postRepository.save(post);

    }
}
