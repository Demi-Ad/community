package com.example.community.admin.forbiddenWord.service;

import com.example.community.admin.forbiddenWord.dto.ForbiddenWordDto;
import com.example.community.admin.forbiddenWord.entity.ForbiddenWord;
import com.example.community.admin.forbiddenWord.repo.ForbiddenWordRepository;
import com.example.community.common.component.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ForbiddenWordService {
    private final ForbiddenWordRepository forbiddenWordRepository;

    @CacheEvict(value = "forbiddenWord",allEntries = true)
    public Long save(ForbiddenWordDto forbiddenWordDto) {
        return forbiddenWordRepository.save(forbiddenWordDto.toEntity()).getId();
    }

    @CacheEvict(value = "forbiddenWord",allEntries = true)
    public void delete(Long id) {
        forbiddenWordRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Pagination<ForbiddenWordDto> forbiddenWordList(Pageable pageable, Specification<ForbiddenWord> specification) {

        Page<ForbiddenWordDto> page = forbiddenWordRepository.findAll(specification, pageable)
                .map(ForbiddenWord::toDto);
        return Pagination.of(page);
    }


    @Cacheable(value = "forbiddenWord",key = "#forbiddenWordSpecification")
    @Transactional(readOnly = true)
    public List<String> getForbiddenWord(ForbiddenWordSpecification forbiddenWordSpecification) {
        return forbiddenWordRepository.findAll(forbiddenWordSpecification.search())
                .stream()
                .map(ForbiddenWord::getForbiddenText)
                .collect(Collectors.toList());
    }

}
