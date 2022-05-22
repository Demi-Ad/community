package com.example.community.admin.forbiddenWord.repo;

import com.example.community.admin.forbiddenWord.entity.ForbiddenWord;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ForbiddenWordRepository extends JpaRepository<ForbiddenWord, Long>, JpaSpecificationExecutor<ForbiddenWord> {

    boolean existsByForbiddenText(String forbiddenText);



}