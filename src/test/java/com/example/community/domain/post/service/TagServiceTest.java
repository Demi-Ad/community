package com.example.community.domain.post.service;

import com.example.community.domain.post.dto.TagRequestDto;
import com.example.community.domain.post.entity.Tag;
import com.example.community.domain.post.repo.TagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class TagServiceTest {
    @Autowired
    TagService tagService;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    EntityManager em;

    @Test
    void tagServiceTest() {
        TagRequestDto tagRequestDto1 = new TagRequestDto("A");
        TagRequestDto tagRequestDto2 = new TagRequestDto("B");
        TagRequestDto tagRequestDto3 = new TagRequestDto("A");
        List<Tag> tagList = tagService.saveElseSkip(List.of(tagRequestDto1, tagRequestDto2));
        tagRepository.saveAll(tagList);

        em.flush();
        em.clear();

        tagRepository.saveAll(tagService.saveElseSkip(List.of(tagRequestDto3)));

        em.flush();
        em.clear();

        Assertions.assertThat(tagRepository.findAll().size()).isEqualTo(2);
    }
}