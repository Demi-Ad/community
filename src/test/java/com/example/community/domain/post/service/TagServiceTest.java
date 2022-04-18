package com.example.community.domain.post.service;

import com.example.community.domain.post.dto.TagDto;
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
        TagDto tagDto1 = new TagDto("A");
        TagDto tagDto2 = new TagDto("B");
        TagDto tagDto3 = new TagDto("A");
        List<Tag> tagList = tagService.saveElseFind(List.of(tagDto1, tagDto2));
        tagRepository.saveAll(tagList);

        em.flush();
        em.clear();

        tagRepository.saveAll(tagService.saveElseFind(List.of(tagDto3)));

        em.flush();
        em.clear();

        Assertions.assertThat(tagRepository.findAll().size()).isEqualTo(2);
    }
}