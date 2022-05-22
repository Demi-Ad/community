package com.example.community.admin.forbiddenWord.repo;

import com.example.community.admin.forbiddenWord.entity.ForbiddenWord;
import com.example.community.admin.forbiddenWord.service.ForbiddenWordSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ForbiddenWordRepositoryTest {

    @Autowired
    ForbiddenWordRepository forbiddenWordRepository;

    @Autowired
    EntityManager em;

    @Test
    void test() {
        ForbiddenWord forbiddenWord = new ForbiddenWord("ABC",true,true,false);
        ForbiddenWord forbiddenWord2 = new ForbiddenWord("ABC",false,true,true);

        forbiddenWordRepository.save(forbiddenWord);
        forbiddenWordRepository.save(forbiddenWord2);

        em.flush();
        em.clear();

        List<ForbiddenWord> abc = forbiddenWordRepository.findAll(ForbiddenWordSpecification.POST.search(null));
        Assertions.assertThat(abc.size()).isEqualTo(1);
    }
}