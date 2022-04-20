package com.example.community.domain.post.service;

import com.example.community.domain.post.dto.TagDto;
import com.example.community.domain.post.entity.Tag;
import com.example.community.domain.post.repo.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> saveElseFind(List<String> tagStrList) {
        if (tagStrList == null || tagStrList.isEmpty()){
            return Collections.emptyList();
        }
        List<Tag> tagList = new ArrayList<>();

        for (String tagStr : tagStrList) {
            if (!StringUtils.hasText(tagStr))
                continue;

            Tag tag = tagRepository.findByItem(tagStr).orElseGet(() -> new Tag(tagStr));
            if (tag.getId() == null)
                tagRepository.save(tag);
            tagList.add(tag);
        }
        return tagList;
    }
}
