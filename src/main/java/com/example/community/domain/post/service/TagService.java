package com.example.community.domain.post.service;

import com.example.community.domain.post.dto.TagDto;
import com.example.community.domain.post.entity.Tag;
import com.example.community.domain.post.repo.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> saveElseFind(List<String> tagDtoList) {
        if (tagDtoList == null || tagDtoList.isEmpty()){
            return Collections.emptyList();
        }
        List<Tag> tagList = new ArrayList<>();

        for (String tagStr : tagDtoList) {
            Tag tag = tagRepository.findByItem(tagStr).orElseGet(() -> new Tag(tagStr));
            if (tag.getId() == null)
                tagList.add(tag);
        }
        return tagList;
    }
}
