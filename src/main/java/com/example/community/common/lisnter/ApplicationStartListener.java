package com.example.community.common.lisnter;

import com.example.community.admin.user.entity.Admin;
import com.example.community.admin.user.repo.AdminRepository;
import com.example.community.config.security.Role;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.repo.PostRepository;
import com.example.community.domain.postTag.entity.PostTag;
import com.example.community.domain.postTag.entity.Tag;
import com.example.community.domain.postTag.repo.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Transactional
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountRepository accountRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    private final PostRepository postRepository;


    private final TagRepository tagRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Account account = Account.builder().email("votm777@naver.com")
                .password(passwordEncoder.encode("aaaa"))
                .nickname("aaaa")
                .profileImg("person.png")
                .build();

        account.emailVerification();
        accountRepository.save(account);

        Tag tag = new Tag("#TEST_TAG");
        tagRepository.save(tag);

        IntStream.rangeClosed(1,100)
                .mapToObj(i -> new Post("TEST" + i, "TEST" + i))
                .collect(Collectors.toList())
                .forEach(post -> {
                    PostTag postTag = new PostTag();
                    post.addPostTag(postTag);
                    postTag.setTag(tag);
                    post.postedAccount(account);
                    tagRepository.save(tag);
                    postRepository.save(post);
                });

        Admin admin = Admin.builder()
                .adminId("admin")
                .password(passwordEncoder.encode("admin"))
                .adminName("전체관리자")
                .role(Role.ROLE_ADMIN)
                .build();

        adminRepository.save(admin);
    }
}
