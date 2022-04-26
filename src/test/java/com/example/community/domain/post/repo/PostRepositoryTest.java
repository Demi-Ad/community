package com.example.community.domain.post.repo;

import com.example.community.domain.post.entity.Post;
import com.example.community.domain.postTag.entity.PostTag;
import com.example.community.domain.postTag.entity.Tag;
import com.example.community.domain.postTag.repo.PostTagRepository;
import com.example.community.domain.postTag.repo.TagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostTagRepository postTagRepository;

    @Autowired
    EntityManager em;

    @Test
    void saveTest() {
        //given
        Post post = new Post("A","A");

        List<Tag> tagList = IntStream.rangeClosed(1, 5)
                .mapToObj(value -> new Tag(String.valueOf(value)))
                .collect(Collectors.toList());


        //when
        tagRepository.saveAll(tagList);


        tagList.forEach(tag -> {
            PostTag postTag = new PostTag();
            postTag.setTag(tag);
            post.addPostTag(postTag);
        });

        postRepository.save(post);


        em.flush();
        em.clear();
        //then

        Post savedPost = postRepository.findByTitle("A").orElseThrow();

        Assertions.assertThat(savedPost.getContent()).isEqualTo("A");
        Assertions.assertThat(savedPost.getPostTagList().size()).isEqualTo(5);

        List<String> savedPostTagItem = savedPost.getPostTagList()
                .stream()
                .map(PostTag::getTag)
                .map(Tag::getItem)
                .collect(Collectors.toList());

        List<String> tagItem = tagList.stream()
                .map(Tag::getItem)
                .collect(Collectors.toList());

        Assertions.assertThat(tagItem).containsAll(savedPostTagItem);

    }
}